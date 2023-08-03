package application.controller;

import java.sql.SQLException;

import application.model.Timer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TimerController {
	@FXML
	private Label timeLabel;
	
	Timer timer;

	public void initialize() {
	    timer = new Timer();
	}
	
	public void timerStart(ActionEvent event) throws SQLException {
		timer.startCountdown(timeLabel);
	};
}
