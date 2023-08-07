package application.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DatabaseConnection.DatabaseAccessor;
import application.model.FrogTimer;
import application.model.Task;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class TimerPageController {
	DatabaseAccessor db;
	BorderPane selectedTaskCard;
	FrogTimer timer;
	String selectedTaskName;
	Font font;
	MediaPlayer mediaPlayer;
	private static final String FONT_AND_SIZE = "-fx-font-family: 'Apple LiGothic Medium'; -fx-font-size: 20;";
	private static final String WHITE = "-fx-fill: white;";
	private static final String BROWN = "-fx-fill: #805D19;";
	private static final String LINE_THROUGH = "-fx-strikethrough: true;";
	

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
		
	    timer = new FrogTimer(db);
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
		taskLabel.setWrappingWidth(150);
		System.out.println(task.getTaskName() + " " + task.isActive());
		checkbox.setSelected(!task.isActive());
		taskLabel.setStyle(getLableStyle(checkbox.isSelected(),task.getTaskName() == selectedTaskName));
		checkbox.setOnAction((e)->{
			task.setIsActive(!checkbox.isSelected());
			task.updateToDB(db, task.getTaskName());
			taskLabel.setStyle(getLableStyle(checkbox.isSelected(),task.getTaskName() == selectedTaskName));
		});
		Button editButton = new Button("Edit");
		editButton.setStyle(FONT_AND_SIZE + "-fx-text-fill: #805D19;; -fx-background-color: transparent;");
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
			
			
			FXMLLoader loder = new FXMLLoader(getClass().getResource("/application/ui/" + "UpdateTaskDialogue" +".fxml"));
			Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initStyle(StageStyle.TRANSPARENT);
			VBox editView;
			try {
				editView = loder.load();
				dialog.setScene(new Scene(editView));
				Button saveButton = (Button) editView.getChildren().get(1);
			    Button cancelButton = (Button) editView.getChildren().get(2);
			    TextField editInput = (TextField)editView.getChildren().get(0);
			    saveButton.setOnAction((e3) -> {
			    	String newName = editInput.getText();
			    	if(!newName.equals("")) {
			    		String oldName = task.getTaskName();
			    		task.setTaskName(newName);
			    		task.updateToDB(db, oldName);
			    		taskLabel.setText(newName);
			    	}
			    	System.out.println("saveButton clicked");
			        
//			        dialogPane.setStyle("-fx-background-color: transparent;\n"
//			        		+ "    -fx-background-radius: 0;\n"
//			        		+ "    -fx-background-insets: 0;");
//			        dialogPane.getButtonTypes().add(ButtonType.CANCEL);
				    dialog.hide();
//			        dialogPane.getButtonTypes().remove(ButtonType.CANCEL);

			    });
			    cancelButton.setOnAction((e4)->{
//			    	System.out.println("cancelButton clicked");
//			        dialogPane.getButtonTypes().add(ButtonType.CANCEL);
			        dialog.hide();
//			        dialogPane.getButtonTypes().remove(ButtonType.CANCEL);
			    });
			    
			    
//			    dialog.getDialogPane().setContent(editView);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    
		    dialog.showAndWait();
			
			
				
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
		CheckBox active = (CheckBox) selectedTaskCard.getLeft();
		boolean isActive = !active.isSelected();
		
		selectedTaskCard.setStyle("-fx-background-color: #4A8229; -fx-background-radius: 10;");
		selectedTaskCard.getCenter().setStyle(isActive ? "-fx-font-family: 'Apple LiGothic Medium'; -fx-font-size: 20;-fx-fill: white" : "-fx-strikethrough: true; -fx-font-family: 'Apple LiGothic Medium'; -fx-font-size: 20;-fx-fill: white" );
		selectedTaskCard.getRight().setStyle("-fx-font-family: 'Apple LiGothic Medium'; -fx-font-size: 20; -fx-text-fill: white; -fx-background-color: transparent;");
		
	}
	
	private void unselectCard() {
		CheckBox active = (CheckBox) selectedTaskCard.getLeft();
		boolean isActive = !active.isSelected();
		selectedTaskCard.setStyle("-fx-background-color: #F5F4FB");
		selectedTaskCard.getCenter().setStyle(isActive ? "-fx-font-family: 'Apple LiGothic Medium'; -fx-font-size: 20; -fx-fill: #805D19;" : "-fx-strikethrough: true; -fx-font-family: 'Apple LiGothic Medium'; -fx-font-size: 20; -fx-fill: #805D19;");
		selectedTaskCard.getRight().setStyle("-fx-font-family: 'Apple LiGothic Medium'; -fx-font-size: 20; -fx-text-fill: #805D19; -fx-background-color: transparent;");
	}

	public String getSelectedTaskName() {
		return selectedTaskName;
	}
	
	private String getLableStyle(boolean isChecked, boolean isSelected) {
		if(isSelected) {
			if(isChecked) {
				return LINE_THROUGH + FONT_AND_SIZE + WHITE;
			} else {
				return FONT_AND_SIZE + WHITE;
			}
		} else {
			if(isChecked) {
				return LINE_THROUGH + FONT_AND_SIZE + BROWN;
			} else {
				return FONT_AND_SIZE + BROWN;
			}
		}
		
	}
}
