package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class BottomBarController implements Initializable {

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
    private AnchorPane windowView;

    @FXML
    void settingPage(MouseEvent event) {
    	loadPage("SettingPage");
    }

    @FXML
    void shopPage(MouseEvent event) {
    	loadPage("ShopPage");
    }

    @FXML
    void statsPage(MouseEvent event) {
    	loadPage("StatsPage");

    }

    @FXML
    void timerPage(MouseEvent event) {
    	loadPage("TimerPage");

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
		// TODO Auto-generated method stub
		
	}

}
