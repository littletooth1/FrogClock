package application.controller;

import application.model.Music;
import application.model.Setting;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import DatabaseConnection.DatabaseAccessor;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class SettingPageController {
	
	Setting setting;
	DatabaseAccessor db;
	List<Music> musics;
	
    @FXML
    private CheckBox backgroundOnCheckbox;

    @FXML
    private Button breakAddButton;

    @FXML
    private Text breakLengthLabel;

    @FXML
    private Button breakReduceButton;

    @FXML
    private CheckBox endOnCheckbox;

    @FXML
    private Polygon musicLeftButton;

    @FXML
    private Text musicNameLabel;

    @FXML
    private Polygon musicRightButton;

    @FXML
    private HBox musicSelectionBox;

    @FXML
    private Button sessionAddButton;

    @FXML
    private Text sessionLengthLabel;

    @FXML
    private Button sessionReduceButton;

    

    @FXML
    void breakReduceOne(ActionEvent event) {
    	setting.breakReduceOne();
    	int len = setting.getBreakLength();
    	breakLengthLabel.setText("" + len);
    	if(len < 2) {
    		breakReduceButton.setDisable(true);
    	} else {
    		breakReduceButton.setDisable(false);
    	}
    	setting.updateToDB(db, "SETTING");
    	
    }

    
    @FXML
    void breakAddOne(ActionEvent event) {
    	setting.breakAddOne();
    	int len = setting.getBreakLength();
    	breakLengthLabel.setText("" + len);
    	setting.updateToDB(db, "SETTING");
    }
    
    @FXML
    void sessionAddOne(ActionEvent event) {
    	setting.sessionAddOne();
    	sessionLengthLabel.setText("" + setting.getSessionLength());
    	setting.updateToDB(db, "SETTING");
    }

    @FXML
    void sessionReduceOne(ActionEvent event) {
    	setting.sessionReduceOne();
    	int len = setting.getSessionLength();
    	sessionLengthLabel.setText("" + len);
    	if(len < 6) {
    		sessionReduceButton.setDisable(true);
    	} else {
    		sessionReduceButton.setDisable(false);
    	}
    	setting.updateToDB(db, "SETTING");
    	

    }

    @FXML
    void toggleEndOn(ActionEvent event) {
    	
    	setting.setEndOn(endOnCheckbox.isSelected());
    	setting.updateToDB(db, "SETTING");

    }

    @FXML
    void toggleBackgroundOn(ActionEvent event) {
    	
    	setting.setBackgroundOn(backgroundOnCheckbox.isSelected());
    	setting.updateToDB(db, "SETTING");
    	

    }
    
	
	public void initialize(DatabaseAccessor db) throws SQLException {
        Statement statement = db.getConnection().createStatement(); 
		this.db = db;
		setting = Setting.getSetting(db, statement);
		musics = Music.getBoughtMusic(db, statement);
		sessionLengthLabel.setText("" + setting.getSessionLength());
		breakLengthLabel.setText("" + setting.getBreakLength());
		endOnCheckbox.setSelected(setting.isEndOn());
		backgroundOnCheckbox.setSelected(setting.isBackgroundOn());
		int bgmId = setting.getBgmID();
		musicNameLabel.setText(musics.get(bgmId).getMusicName());
	}
	
	
	
    @FXML
    void musicLeft(ActionEvent event) {
    	int size = musics.size();
    	int curId = setting.getBgmID();
    	setting.setBgmId((curId - 1 + size) % size);
    	setting.updateToDB(db, "SETTING");
    	musicNameLabel.setText(musics.get(setting.getBgmID()).getMusicName());

    }

    @FXML
    void musicRight(ActionEvent event) {
    	int size = musics.size();
    	int curId = setting.getBgmID();
    	setting.setBgmId((curId + 1 ) % size);
    	setting.updateToDB(db, "SETTING");
    	musicNameLabel.setText(musics.get(setting.getBgmID()).getMusicName());
    }

}

