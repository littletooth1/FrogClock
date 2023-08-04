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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;


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
		CheckBox checkbox = new CheckBox();
		Text taskLabel = new Text(task.getTaskName());
		
		checkbox.setOnAction((e)->{
			task.setIsActive(!checkbox.isSelected());
			task.updateToDB(db, task.getTaskName());
			if(checkbox.isSelected()) {
				taskLabel.setStyle("-fx-strikethrough: true;");
			} else {
				taskLabel.setStyle("-fx-strikethrough: false;");
			}
		});
		Button selectButton = new Button("Select");
		selectButton.setStyle("-fx-background-color: transparent");
		bp.setLeft(checkbox);
		bp.setCenter(taskLabel);
		bp.setRight(selectButton);
		selectButton.setOnAction((e)->{
			onGoingTask.setText(task.getTaskName());
			if(selectedTaskCard != null) {
				unselectCard();
			}
			selectedTaskCard = bp;
			selectCard();	
		});
		BorderPane.setAlignment(checkbox, Pos.CENTER_LEFT);
		BorderPane.setAlignment(taskLabel, Pos.CENTER_LEFT);
		BorderPane.setAlignment(selectButton, Pos.CENTER_RIGHT);
		BorderPane.setMargin(checkbox, new Insets(0,25,0,15));
		bp.setStyle("-fx-background-color: #F5F4FB; -fx-background-radius: 10;");
		bp.setPadding(new Insets(2,8,2,8));
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
		selectedTaskCard.setStyle("-fx-background-color: #4A8229; -fx-background-radius: 10;");
		selectedTaskCard.getCenter().setStyle("-fx-fill: white");
		selectedTaskCard.getRight().setStyle("-fx-text-fill: white; -fx-background-color: transparent;");

	}
	
	private void unselectCard() {
		selectedTaskCard.setStyle("-fx-background-color: #F5F4FB");
		selectedTaskCard.getCenter().setStyle("-fx-fill: black");
		selectedTaskCard.getRight().setStyle("-fx-fill: black; -fx-background-color: transparent;");
	}

}
