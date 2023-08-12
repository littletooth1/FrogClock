package application.controller;

import java.io.IOException;
import DatabaseConnection.DatabaseAccessor;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class BottomBarController implements Initializable {
	
	DatabaseAccessor db = new DatabaseAccessor("database.db");

    @FXML
    private BorderPane mainFrame;

    @FXML
    private Button settingButton;

    @FXML
    private Button shopButton;

    @FXML
    private Button statsButton;

    @FXML
    private Button timerButton;
    
    @FXML
    private ImageView focusImage;

    @FXML
    private AnchorPane windowView;

    @FXML
    void settingPage(MouseEvent event) {
    	
    	Parent root = null;
    	
    	try {
    		
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/" + "SettingPage" +".fxml"));
            root = loader.load();
            // get the controller instance and pass parameters
            SettingPageController controller = loader.getController();
		    controller.initialize(db);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	mainFrame.setCenter(root);
    }

    @FXML
    void shopPage(MouseEvent event) {
        Parent root = null;
    	
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/" + "ShopPage" +".fxml"));
            root = loader.load();
            // get the controller instance and pass parameters
            StoreController controller = loader.getController();
		    controller.initialize(db);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	mainFrame.setCenter(root);
    	
    }

    @FXML
    void statsPage(MouseEvent event) {
        Parent root = null;
    	
    	try {
    		
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/" + "StatsPage" +".fxml"));
            root = loader.load();
            // get controller instance and pass parameters
            CalendarController controller = loader.getController();
		    controller.initialize(db);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	mainFrame.setCenter(root);

    }

    @FXML
    void timerPage(MouseEvent event) {
    	
    	Parent root = null;
    	
    	try {
    		
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/" + "TimerPage" +".fxml"));
            root = loader.load();
            // get controller instance and pass parameters
            TimerPageController controller = loader.getController();
            controller.setBottomBarController(this);
		    controller.initialize(db);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	mainFrame.setCenter(root);
    	

    }
    
    private void loadPage(String page) {
    	Parent root = null;
    	
    	try {
			root = FXMLLoader.load(getClass().getResource("/application/ui/" + page +".fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	mainFrame.setCenter(root);
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Parent root = null;
    	
    	try {
    		
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/" + "TimerPage" +".fxml"));
            root = loader.load();
            // get controller instance and pass parameters
            TimerPageController controller = loader.getController();
            controller.setBottomBarController(this);
		    controller.initialize(db);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	mainFrame.setCenter(root);
		
	}
	
	public void toggleStatsButtonVisibility(boolean showStats) {
	    statsButton.setManaged(showStats);
	    statsButton.setVisible(showStats);
	    settingButton.setManaged(showStats);
	    settingButton.setVisible(showStats);
	    shopButton.setManaged(showStats);
	    shopButton.setVisible(showStats);
	    timerButton.setManaged(showStats);
	    timerButton.setVisible(showStats);
	    focusImage.setManaged(!showStats);
	    focusImage.setVisible(!showStats);
	}
	
	public void hideStatsButton() {
	    toggleStatsButtonVisibility(false);
	}

	public void showStatsButton() {
	    toggleStatsButtonVisibility(true);
	}
}
