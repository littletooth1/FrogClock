package application.controller;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DatabaseConnection.DatabaseAccessor;
import application.model.FrogTimer;
import application.model.Task;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;



public class TimerPageController {
	DatabaseAccessor db;
	BorderPane selectedTaskCard;
	FrogTimer timer;

    @FXML
    private Button addNewTaskButton;

    @FXML
    private TextField newTaskTextField;

    @FXML
    private Text onGoingTask;

    @FXML
    private VBox taskPannel;
    
    @FXML
	private Label timeLabel;

    @FXML
    public Button startButton;

    @FXML
    public Button pauseButton;

	public TimerPageController() {
		// TODO Auto-generated constructor stub
	}
	
	public void initialize(DatabaseAccessor db) throws SQLException {
        Statement statement = db.getConnection().createStatement(); 
		this.db = db;
		List<Task> tasks = Task.getActiveTasks(db, statement);
		for(Task task : tasks) {
			taskPannel.getChildren().add(createTaskCard(task));
		}
		
	    timer = new FrogTimer();
	    timer.updateTimeLabel(timeLabel, timer.timeRemaining);
	    switchIcon();
	}
	
	public void switchIcon() {
	    if (timer.isRunning) {
	        startButton.setVisible(false);
	        pauseButton.setVisible(true);
	    } else {
	        startButton.setVisible(true);
	        pauseButton.setVisible(false);
	    }
	}
	
	public void timerStart(ActionEvent event) throws SQLException {
		timer.startCountdown(timeLabel, this);
		switchIcon();
	};

	public void timerPause(ActionEvent event) throws SQLException {
        timer.pauseCountdown(timeLabel);
        switchIcon();
    };

	public void timerStop(ActionEvent event) throws SQLException {
		timer.stopCountdown(timeLabel);
		switchIcon();
	};
	
	private BorderPane createTaskCard(Task task) {
		BorderPane bp = new BorderPane();
		Text taskLabel = new Text(task.getTaskName());
		Button selectButton = new Button("select");
		bp.setLeft(taskLabel);
		bp.setRight(selectButton);
		selectButton.setOnAction((e)->{
			onGoingTask.setText(task.getTaskName());
			if(selectedTaskCard != null) {
				unselectCard();
			}
			selectedTaskCard = bp;
			selectCard();	
			
		});
		bp.setStyle("-fx-background-color: white");
		bp.getStyleClass().add("taskCard");
		
		return bp;
	}
	
	@FXML
	private void addNewTask() {
		String newTaskName = newTaskTextField.getText();
		if(!newTaskName.equals("")) {
			Task task = new Task(newTaskName,true);
			task.addToDB(db);
			newTaskTextField.setText("");
			taskPannel.getChildren().add(createTaskCard(task));
		}
	}
	
	private void selectCard() {
		selectedTaskCard.setStyle("-fx-background-color: green");
		selectedTaskCard.getLeft().setStyle("-fx-fill: white");
	}
	
	private void unselectCard() {
		selectedTaskCard.setStyle("-fx-background-color: white");
		selectedTaskCard.getLeft().setStyle("-fx-fill: black");
	}

}
