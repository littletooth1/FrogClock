package application;
	
import DatabaseConnection.DatabaseAccessor;


import application.model.Task;
import application.model.Music;

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
		
		//Test database add and update
		DatabaseAccessor db = new DatabaseAccessor("database.db");
		//Music music1 = new Music("Silent Night", false, 10 ,"/resource/music/Jon Gegelman - Half Speed Silent Night.mp3");
		//music1.addToDB(db);
		
		//Music music2 = new Music("A Vivid Frost", false, 10 ,"/resource/music/Jon Gegelman - A Vivid Frost.mp3");
		//music2.addToDB(db);
		
		//Music music3 = new Music("The Frog Walk", false, 10 ,"/resource/music/Aves - The Frog Walk.mp3");
		//music3.addToDB(db);
//		
//		Task testTask1 = new Task("Beating",true);
//		testTask1.addToDB(db);
//		String oldTaskName = testTask1.getTaskName();
//		testTask1.setTaskName("Writing");
//		testTask1.updateToDB(db,oldTaskName);
		
		
		//launch application
		launch(args);
	}
}
