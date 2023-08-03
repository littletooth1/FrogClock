package application.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import DatabaseConnection.DatabaseAccessor;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

public class Timer extends Data{
	long timeRemaining;
	public Timer() {
    	try {
    		DatabaseAccessor db = new DatabaseAccessor("database.db");
			Statement statement = db.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT sessionLength FROM SETTING WHERE ID = 'SETTING'");
			if (resultSet.next()) {
			    int timeFromDb = resultSet.getInt("sessionLength");
			    timeRemaining = timeFromDb * 60 * 1_000_000_000L;
			    System.out.println("!!!Session length from DB: " + timeFromDb + "..." + timeRemaining/60/1_000_000_000L);
			}
			db.getConnection().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}

	public void startCountdown(Label timeLabel) throws SQLException{
        new AnimationTimer() {
		    long lastUpdate = 0;

		    @Override
		    public void handle(long now) {
		    	
		    	// default to 25 minutes
		    	
		        if (lastUpdate > 0) {
		            long elapsed = now - lastUpdate;
		            timeRemaining -= elapsed;
		        }

		        if (timeRemaining <= 0) {
		            timeLabel.setText("Time's Up!");
		            stop();
		        } else {
		            int seconds = (int) (timeRemaining / 1_000_000_000L);
		            LocalTime displayTime = LocalTime.ofSecondOfDay(seconds);
		            timeLabel.setText(displayTime.format(DateTimeFormatter.ofPattern("mm:ss")));
		        }

		        lastUpdate = now;
		    }
		}.start();
	}
	
	@Override
	public String getInsertQuery() {
		// TODO Auto-generated method stub
		return null;
	}

}
