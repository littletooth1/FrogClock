package application;
	
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import DatabaseConnection.DatabaseAccessor;
import application.model.Data;
import application.model.Setting;

import application.model.Task;
import application.model.Music;
import application.model.Leaf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/ui/FrogNav.fxml"));
			Scene scene = new Scene(root);
		
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public static void main(String[] args) {
		System.out.println("Haha");
		
		//Note: Run the following code to ensure records exist in the database before testing Music Facade
//		DatabaseAccessor db = new DatabaseAccessor("database.db");
//		Music music1 = new Music( "Silent Night", false, 10 ,"/resource/music/Jon Gegelman - Half Speed Silent Night.mp3");
//		music1.addToDB(db);
//		
//		Music music2 = new Music( "A Vivid Frost", false, 10 ,"/resource/music/Jon Gegelman - A Vivid Frost.mp3");
//		music2.addToDB(db);
//		
//		Music music3 = new Music( "The Frog Walk", false, 10 ,"/resource/music/Aves - The Frog Walk.mp3");
//		music3.addToDB(db);
//		
//		Leaf leaf1 = new Leaf("123","14:28","Reading",20);
//		leaf1.addToDB(db);
		
		
		
//		Task testTask1 = new Task("Beating",true);
//		testTask1.addToDB(db);
//		String oldTaskName = testTask1.getTaskName();
//		testTask1.setTaskName("Writing");
//		testTask1.updateToDB(db,oldTaskName);
		
		
		
//		Setting setting = new Setting(25,5,false,true,0);
//		setting.addToDB(db);
//		List<Music> musicList = Music.getBoughtMusic(db);
//		System.out.println(musicList);
		
		
//		launch application
		launch(args);
	}
}
