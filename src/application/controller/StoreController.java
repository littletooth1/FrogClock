package application.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.model.Leaf;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.StageStyle;
import javafx.scene.layout.BorderPane;
import DatabaseConnection.DatabaseAccessor;









public class StoreController implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	
	
	@FXML
	private Label leafLabel;
	
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
	
	

	@FXML
	void buyMusic(ActionEvent event) throws IOException  {
		
		//pop up a message: Are you sure? Yes / No
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/BuyAlert.fxml"));
		BorderPane borderPane = loader.load();
		 // Get the controller for the dialog
        //DialogController dialogController = loader.getController();
       // dialogController.setUserData(this);


        // Create a new dialog using the custom content
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.getDialogPane().setContent(borderPane);

        // Add a button to the dialog's button pane (for demonstration purposes)
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        // Show the dialog and wait for the user response (button click)
        dialog.showAndWait();
        
        
        
		//MouseButton buttonCliked = event.getButton();
		
		
		
		//Press No.


		
	}
	
	
	@FXML
	void clickYes(ActionEvent event) {
		//press Yes. 1. deduct leafs  2. change status of Music in DB  3.change color of button
		
		/*Leaf leaf = new Leaf(null);
		
		DatabaseAccessor db = new DatabaseAccessor();
		leaf.updateToDB(db, null);
		*/
		
		
		buyBotton1.setStyle("-fx-background-color: #ff0000; ");
		buyBotton1.setStyle("-fx-background-radius: 30 30 30 30; ");
		
		
	}
	
	
	


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
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


	public Object getMyButton() {
		// TODO Auto-generated method stub
		return null;
	}

}
