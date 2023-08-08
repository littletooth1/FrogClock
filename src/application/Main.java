package application;
	
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import DatabaseConnection.DatabaseAccessor;
import application.controller.MusicRelated;
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
	
	
	public static void main(String[] args) throws SQLException {
		System.out.println("Haha");
				
//		launch application
		launch(args);
	}
}
