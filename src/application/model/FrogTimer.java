package application.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import DatabaseConnection.DatabaseAccessor;
import application.controller.MusicRelated;
import application.controller.TimerPageController;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Alert;

public class FrogTimer extends Data{

	public long timeRemaining;
	long lastSession;
	long lastBreak;
	long breakTimeRemaining;
    long lastUpdate = 0;
    boolean isEndOn; 

	private boolean isBreak;
	public boolean isRunning;

	private AnimationTimer currentTimer;
	DatabaseAccessor db;

	public FrogTimer(DatabaseAccessor db) {
        isBreak = false;
        isRunning = false;
        this.db = db;
    	try {
    		
			Statement statement = db.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT sessionLength, breakLength, isEndOn FROM SETTING WHERE ID = 'SETTING'");
			if (resultSet.next()) {
			    int timeFromDb = resultSet.getInt("sessionLength");
			    int breakTimeFromDb = resultSet.getInt("breakLength");
			    isEndOn = resultSet.getBoolean("isEndOn");
			    timeRemaining = timeFromDb * 60 * 1_000_000_000L;
			    breakTimeRemaining = breakTimeFromDb * 60 * 1_000_000_000L;
			    lastSession = timeRemaining;
			    lastBreak = breakTimeRemaining;
			    
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}

	public void startCountdown(Label timeLabel, TimerPageController controller, MediaPlayer mediaPlayer) throws SQLException{
		isRunning = true;
		lastUpdate = 0;
		currentTimer = new AnimationTimer() {
		    @Override
		    public void handle(long now) {
		        if (lastUpdate > 0) {
		            long elapsed = now - lastUpdate;
		            timeRemaining -= elapsed;
		        }
		        
				try {
					MusicRelated.playBackGround(mediaPlayer,isRunning, isBreak);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
	            if (isBreak) {
	                handleBreak(now, timeLabel, controller, mediaPlayer);
	            } else {
	                handleSession(now, timeLabel,controller.getSelectedTaskName(),mediaPlayer);
	            }

		        lastUpdate = now;
		    }
		};
		currentTimer.start();

	}
	

	public void handleSession(long now, Label timeLabel, String taskName, MediaPlayer mediaPlayer) {	

	    if (taskName == null) {
	        taskName = "Free Frog Clock"; // default clock name if no task is selected
	    }
	    
        if (timeRemaining <= lastSession - 5 * 1_000_000_000L) {
    		currentTimer.stop();
    		
    		//play end music
    		MusicRelated music = new MusicRelated();
    		if (isEndOn == true) {
        	    music.playMusic(db);
    		}
    	    if (mediaPlayer != null) mediaPlayer.stop();

    	    
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String taskFinishTime = LocalDateTime.now().format(dateFormatter)+" "+LocalDateTime.now().format(timeFormatter);
            String taskFinishDate = LocalDateTime.now().format(dateFormatter);

    		Leaf leaf = new Leaf(taskFinishTime, taskFinishDate, taskName, 5);
    		leaf.addToDB(db);

    		ButtonType alertButton = new ButtonType("Ok");
            Alert alert = new Alert(AlertType.NONE, "Time's up, let's have a break!", alertButton);
            alert.setOnHidden(event -> {
                timeRemaining = breakTimeRemaining;
                lastUpdate = 0;
                isBreak = true;
                currentTimer.start();
            });
            alert.show();
            

        } else {
    		updateTimeLabel(timeLabel, timeRemaining);
        } 
	}

	public void handleBreak(long now, Label timeLabel, TimerPageController controller, MediaPlayer mediaPlayer) {
        if (timeRemaining <= lastBreak - 5 * 1_000_000_000L) {
        	currentTimer.stop();
    		isRunning = false;    		

        	ButtonType alerButtonForBreak = new ButtonType("Ok");
            Alert alert = new Alert(AlertType.NONE, "Time's up, let's get back to work!", alerButtonForBreak);
            alert.setOnHidden(event -> {
                try {
					stopCountdown(timeLabel,mediaPlayer);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                controller.switchIcon();
            });
            alert.show();

        } else {
    		updateTimeLabel(timeLabel, timeRemaining);
        } 
	}

	public void pauseCountdown(Label timeLabel, MediaPlayer mediaPlayer) throws SQLException{
		currentTimer.stop();
		lastUpdate = 0;
		isRunning = false;
		updateTimeLabel(timeLabel, timeRemaining);
		MusicRelated.playBackGround(mediaPlayer,isRunning, isBreak);

	}

	public void stopCountdown(Label timeLabel, MediaPlayer mediaPlayer) throws SQLException{
		currentTimer.stop();
		timeRemaining = lastSession;

		isBreak = false;
		isRunning = false;
		updateTimeLabel(timeLabel, timeRemaining);
		MusicRelated.playBackGround(mediaPlayer,isRunning, isBreak);
	}

	public void updateTimeLabel(Label timeLabel, long time) {
	    int seconds = (int) (time / 1_000_000_000L);
	    LocalTime displayTime = LocalTime.ofSecondOfDay(seconds);
	    timeLabel.setText(displayTime.format(DateTimeFormatter.ofPattern("mm:ss")));
	}


	@Override
	public String getInsertQuery() {
		// TODO Auto-generated method stub
		return null;
	}

}