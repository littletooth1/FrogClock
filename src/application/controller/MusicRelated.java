package application.controller;
import javafx.scene.media.MediaPlayer;

import java.sql.Statement;
import java.util.Objects;

import DatabaseConnection.DatabaseAccessor;
import application.model.Music;
import application.model.Setting;
import javafx.scene.media.Media;


public class MusicRelated {
	  private MediaPlayer mediaPlayer;

	public void playMusic(String url) {
		
		
		//System.out.print(MusicRelated.class.getResource(url).toString());
	    String path = Objects.requireNonNull(getClass().getClassLoader().getResource(url)).toString();
	    Media sound = new Media(path);
	    mediaPlayer = new MediaPlayer(sound);
	 // Add a listener to the status property
	    mediaPlayer.statusProperty().addListener((obs, oldStatus, newStatus) -> {
	      System.out.println("Status changed from " + oldStatus + " to " + newStatus);
	    });
	    mediaPlayer.play();
	    System.out.println("played");
	    }
	 
	 public void playBackGround(DatabaseAccessor db, Statement statement) {
		 //Get bgmID
		Setting currentSetting = Setting.getSetting(db, statement);
		int bgmID = currentSetting.getBgmID();
		 //Get musicURL
		Music music = Music.gettMusic(db, statement, bgmID);
		System.out.println(music.getMusicURL());
		playMusic(music.getMusicURL());
	 }
	

}
