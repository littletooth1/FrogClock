package application.controller;
import javafx.scene.media.MediaPlayer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import DatabaseConnection.DatabaseAccessor;
import application.model.Music;
import application.model.Setting;
import javafx.scene.media.Media;


public class MusicRelated {
	private MediaPlayer mediaPlayer;

	public void playMusic() {		
	    String path = Objects.requireNonNull(getClass().getClassLoader().getResource("resource/music/timerSound.mp3")).toString();
	    Media sound = new Media(path);
	    mediaPlayer = new MediaPlayer(sound);
	 // Add a listener to the status property
	    mediaPlayer.statusProperty().addListener((obs, oldStatus, newStatus) -> {
	    System.out.println("Status changed from " + oldStatus + " to " + newStatus);
	    });
	    mediaPlayer.play();
	    }
	 
	public MediaPlayer getMusicPath(DatabaseAccessor db, Statement statement) throws SQLException {
		//Get bgmID
				Setting currentSetting = Setting.getSetting(db, statement);
				boolean isBackgroundOn = currentSetting.isBackgroundOn();
				if (isBackgroundOn == false) {
					return null;
				}
				System.out.println(isBackgroundOn);
				
				int bgmID = currentSetting.getBgmID();
				System.out.println(bgmID);

				 //Get musicURL
				Music music = Music.gettMusic(db, statement, bgmID);
				System.out.println(music.getMusicURL());
				String path = Objects.requireNonNull(getClass().getClassLoader().getResource(music.getMusicURL())).toString();
				statement.close();
				Media sound = new Media(path);
			    mediaPlayer = new MediaPlayer(sound);
			    mediaPlayer.statusProperty().addListener((obs, oldStatus, newStatus) -> {
				    System.out.println("Status changed from " + oldStatus + " to " + newStatus);
				    });
				return mediaPlayer;
	}
	
	
	
	 static public void playBackGround(MediaPlayer mediaPlayer, boolean isRunning, boolean isBreak) throws SQLException {
		 
		 if (mediaPlayer == null) {
			  return;// do nothing 
			} else {
				  // If the isRunning is true, play the media
		    	  if (isRunning == true && isBreak == false) {
		    	    mediaPlayer.play();
		    	    
		    	  } 
		    	  else if(isRunning == false && isBreak == false){
		    		mediaPlayer.stop();
		    		  
		    	  }
		    	  
		    	  else {
		    	    // If the isRunning is false, pause the media
		    	    mediaPlayer.pause();
		    	  }
			}
		 return;
		 
	    	
	 }
	 
	

}
