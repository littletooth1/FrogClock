package application.controller;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DatabaseConnection.DatabaseAccessor;
import application.model.FrogTimer;
import application.model.Task;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;


public class TimerPageController {
	DatabaseAccessor db;
	BorderPane selectedTaskCard;
	FrogTimer timer;
	String selectedTaskName;
	Font font;
	MediaPlayer mediaPlayer;

    @FXML
    private Button addNewTaskButton;

    @FXML
    private TextField newTaskTextField;

//    @FXML
//    private Text onGoingTask;

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
        this.font = Font.loadFont(getClass().getResourceAsStream("/resource/fonts/Ubuntu-Regular.ttf"), 16);
		this.db = db;
		List<Task> tasks = Task.getActiveTasks(db, statement);
		for(Task task : tasks) {
			taskPannel.getChildren().add(createTaskCard(task));
		}
		
	    timer = new FrogTimer();
	    timer.updateTimeLabel(timeLabel, timer.timeRemaining);
	    BooleanProperty isRunningProperty = new SimpleBooleanProperty(timer.isRunning);
	    MusicRelated music = new MusicRelated();
	    mediaPlayer = music.getMusicPath(db, statement);
//	    isRunningProperty.addListener((observable, oldValue, newValue) -> {
//	    	try {
//				music.playBackGround(mediaPlayer,timer.isRunning);
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	    });
	    
	    switchIcon();
	}
	
	public void switchIcon() {
	    if (timer.isRunning) {
	        startButton.setVisible(false);
	        pauseButton.setVisible(true);
	        bottomBarController.hideStatsButton();

	    } else {
	        startButton.setVisible(true);
	        pauseButton.setVisible(false);
	        bottomBarController.showStatsButton();
	    }
	    	
	}
	

	
	private BottomBarController bottomBarController;

	public void setBottomBarController(BottomBarController controller) {
	    this.bottomBarController = controller;
	}
	
	public void timerStart(ActionEvent event) throws SQLException {
		timer.startCountdown(timeLabel, this, mediaPlayer);
		switchIcon();
	};

	public void timerPause(ActionEvent event) throws SQLException {
        timer.pauseCountdown(timeLabel, mediaPlayer);
        switchIcon();
        bottomBarController.hideStatsButton();
    };

	public void timerStop(ActionEvent event) throws SQLException {
		timer.stopCountdown(timeLabel);
		switchIcon();
	};
	
	private BorderPane createTaskCard(Task task) {
		BorderPane bp = new BorderPane();
		CheckBox checkbox = new CheckBox();
		Text taskLabel = new Text(task.getTaskName());
		taskLabel.setStyle("-fx-font-family: 'Apple LiGothic Medium'; -fx-font-size: 20; -fx-fill: #805D19;" );
		checkbox.setOnAction((e)->{
			task.setIsActive(!checkbox.isSelected());
			task.updateToDB(db, task.getTaskName());
			if(checkbox.isSelected()) {
				taskLabel.setStyle("-fx-strikethrough: true;-fx-font-family: 'Apple LiGothic Medium'; -fx-font-size: 20; -fx-fill: #805D19;");
			} else {
				taskLabel.setStyle("-fx-strikethrough: false;-fx-font-family: 'Apple LiGothic Medium'; -fx-font-size: 20; -fx-fill: #805D19;");
			}
		});
		Button editButton = new Button("Edit");
		editButton.setStyle("-fx-font-family: 'Apple LiGothic Medium'; -fx-font-size: 20; -fx-text-fill: #805D19;; -fx-background-color: transparent;");
		bp.setLeft(checkbox);
		bp.setCenter(taskLabel);
		bp.setRight(editButton);
		taskLabel.setOnMouseClicked((e) -> {
			if(selectedTaskCard != null) {
				unselectCard();
			}
			selectedTaskCard = bp;
			selectedTaskName = task.getTaskName();
			selectCard();
		});
		editButton.setOnAction((e)->{
				
		});
		BorderPane.setAlignment(checkbox, Pos.CENTER_LEFT);
		BorderPane.setAlignment(taskLabel, Pos.CENTER_LEFT);
		BorderPane.setAlignment(editButton, Pos.CENTER_RIGHT);
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
		selectedTaskCard.getCenter().setStyle("-fx-font-family: 'Apple LiGothic Medium'; -fx-font-size: 20;-fx-fill: white");
		selectedTaskCard.getRight().setStyle("-fx-font-family: 'Apple LiGothic Medium'; -fx-font-size: 20; -fx-text-fill: white; -fx-background-color: transparent;");
		

	}
	
	private void unselectCard() {
		selectedTaskCard.setStyle("-fx-background-color: #F5F4FB");
		selectedTaskCard.getCenter().setStyle("-fx-font-family: 'Apple LiGothic Medium'; -fx-font-size: 20; -fx-fill: #805D19;");
		selectedTaskCard.getRight().setStyle("-fx-font-family: 'Apple LiGothic Medium'; -fx-font-size: 20; -fx-text-fill: #805D19;; -fx-background-color: transparent;");
	}

	public String getSelectedTaskName() {
		return selectedTaskName;
	}
}
