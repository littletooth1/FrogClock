package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class DialogController {
	private StoreController storeController;
	
	public void setUserData(StoreController storeController) {
	    this.storeController = storeController;
	}

	@FXML
	void handleButtonClick(ActionEvent event) {
	    // Change the color of the button in the other controller
		storeController.getMyButton().setStyle("-fx-background-color: #00ff00;");
	}
	

}
