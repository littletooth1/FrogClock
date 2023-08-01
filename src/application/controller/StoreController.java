package application.controller;

import application.model.Data;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.model.Leaf;
import application.model.Music;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.StageStyle;
import javafx.scene.layout.BorderPane;
import DatabaseConnection.DatabaseAccessor;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;




public class StoreController implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;
	private int leafNumber;
	Font ubuntuFont;
	private Music music;

	
	
	
	@FXML
	private Label leafLabel;
	
	@FXML
	private Label shopTitle;
	
	@FXML
	private AnchorPane mainPane;
	
	@FXML
	private Button buyBotton1;
	
	@FXML
	private Button buyBotton2;
	
	@FXML
	private Button buyBotton3;

	@FXML
	private Button buyConfirm;
	
	@FXML
	private Button notBuy;

	
	
	DatabaseAccessor db = new DatabaseAccessor("database.db");
	

	int getLeafNumber(DatabaseAccessor db) throws SQLException {
		
		//Tomato TABLE to be created
		int leavesEarned = 200;
		ResultSet leavesSpent = Data.excuateQuerySql("SELECT SUM( CASE WHEN available == true Then price ELSE 0 END) FROM MUSIC AS [totalCost]", db);
		int result = 0;
		if (leavesSpent.next()) {
			result = leavesSpent.getInt("totalCost");
		}
		return leavesEarned - result;	
	
	}
	
	
	void setLabelStyle() throws SQLException{
		leafNumber = getLeafNumber(db);
	
		leafLabel.setText("        Leaf Collected:  " + leafNumber);
		leafLabel.setStyle(" -fx-font-size: 12; "
				+"-fx-font-weight: bold"
				+ " -fx-text-fill: #7A3732;"
				+"-fx-background-color: #DAEED7;"
				+"-fx-background-radius:  30 0 0 30;"
				+"-fx-font-family: '" + ubuntuFont.getName() + "'"
				);
		
		shopTitle.setText("Froggie Shop");
		shopTitle.setStyle(" -fx-font-size: 24; "
				+"-fx-font-weight: bold"
				+ " -fx-text-fill: #7A3732;"
				+"-fx-font-family: '" + ubuntuFont.getName() + "'"
				);
		
	}

	
	@FXML
	private void buyMusic1(ActionEvent event) throws IOException, SQLException  {
		if (leafNumber >= 10) {
			//change music available status
			ResultSet resultSet = Data.excuateQuerySql("SELECT * FROM MUSIC", db);
			
			while(resultSet.next()) {	
				Music music1 = new Music(resultSet);
				System.out.print(music1);
				music1.updateToDB(db, music1.getMusickName());
			}
			
			
			
			//change button
			buyBotton1.setText("Sold");
			buyBotton1.setStyle("-fx-background-color: #8D8D8D; -fx-background-radius: 30 30 30 30; ");
				
			//update leafNumber
			leafNumber = getLeafNumber(db);
			leafLabel.setText("        Leaf Collected:  " + leafNumber);
		}
		else {
			NotBuyAlert();
		}	
		
	}
	
	
	

	@FXML
	private void NotBuyAlert() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/BuyAlert.fxml"));
		BorderPane borderPane = loader.load();
		
		
        // Create a new dialog using the custom content
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.getDialogPane().setContent(borderPane);

        // Add a button to the dialog's button pane (for demonstration purposes)
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.setStyle("-fx-background-color: #DAEED7; "
		+ "-fx-background-radius: 30 30 30 30; "
		+ "-fx-font-size: 16; "
		+"-fx-font-weight: bold");
        
       // VBox vbox = new VBox();
       // borderPane.setCenter(vbox);
        
      //  Label comments = new Label("Sorry, you don't have enough leaves. ");
       // comments.setStyle("-fx-font-weight: bold; -fx-font-family: '\" + ubuntuFont.getName() + \"'");
        
       /* Button close = new Button("close");
        close.setStyle("-fx-background-color: #DAEED7; "
        		+ "-fx-background-radius: 30 30 30 30; "
        		+ "-fx-font-size: 16; "
				+"-fx-font-weight: bold");
        
        
        vbox.getChildren().addAll(comments,close);
        
        close.setOnAction(e -> {
        	dialog.hide();
        });
        */
       
       // System.out.print("I am here");
		//vbox.getChildren().add(closeButton);
        //System.out.print("Vbox add button");


       // borderPane.setCenter(closeButton);
        //BorderPane.setAlignment(closeButton, Pos.CENTER);
        // Show the dialog and wait for the user response (button click)
        dialog.showAndWait();
	}
	


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		
		this.ubuntuFont = Font.loadFont(getClass().getResourceAsStream("/resource/fonts/Ubuntu-Regular.ttf"), 16);
		try {
			setLabelStyle();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	/* buyBotton1.setOnAction(event ->  {
			
			//pop up a message: Are you sure? Yes / No
			
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/BuyAlert.fxml"));
			BorderPane borderPane;
			try {
				borderPane = loader.load();
			
			
			 // Get the controller for the dialog
	        DialogController dialogController = loader.getController();
	        dialogController.setUserData(this);


	        // Create a new dialog using the custom content
	        Dialog<ButtonType> dialog = new Dialog<>();
	        dialog.initStyle(StageStyle.UNDECORATED);
	        dialog.getDialogPane().setContent(borderPane);

	        // Add a button to the dialog's button pane (for demonstration purposes)
	        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

	        // Show the dialog and wait for the user response (button click)
	        dialog.showAndWait();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} );
		
		*/
	}



}
