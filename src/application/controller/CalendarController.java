package application.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.Region;

public class CalendarController {
	ZonedDateTime today;
	HashMap<LocalDate, List<String>> taskMap;
	Font architectFont;
	Font ubuntuFont;
	
	@FXML
	private Text year;
	
	@FXML
	private Text month;
	
	@FXML
	private Text stickerText;
	
	@FXML
	private GridPane gridPane;
	
	@FXML
	private void nextMonth() {
        today = today.plusMonths(1);
        switchDate();
	}
	
	@FXML
	private void previousMonth() {
        today = today.minusMonths(1);
        switchDate();
	}
	
	private void switchDate() {
        gridPane.getChildren().clear();
        createCalendar();
        createSticker();
	}
	
	public void initialize() {
		this.architectFont = Font.loadFont(getClass().getResourceAsStream("/resource/fonts/ArchitectsDaughter-Regular.ttf"), 16);
		this.ubuntuFont = Font.loadFont(getClass().getResourceAsStream("/resource/fonts/Ubuntu-Regular.ttf"), 16);
		today = ZonedDateTime.now();
	    taskMap = loadTasks();
		createCalendar();
	    createSticker();
	}
	
	private HashMap<LocalDate, List<String>> loadTasks() {
	    HashMap taskMap = new HashMap<>();
	    
	    LocalDate today = LocalDate.now();
	    taskMap.put(today, Arrays.asList("Task 1", "Task 2", "Task 1", "Task 2",
	    		"Task 1", "Task 2", "Task 1", "Task 2"));
	    
	    LocalDate tomorrow = today.plusDays(1);
	    taskMap.put(tomorrow, Arrays.asList("Task 3"));

	    LocalDate twoDaysLater = today.plusDays(2);
	    taskMap.put(twoDaysLater, Arrays.asList("Task 4", "Task 5"));
	    
	    LocalDate test = today.plusDays(15);
	    taskMap.put(test, Arrays.asList("Task 4", "Task 5"));
	    return taskMap;
	}
	
	private void createCalendar() {
		year.setText(String.valueOf(today.getYear()));
		month.setText(String.valueOf(today.getMonth()).substring(0, 3));
		year.setStyle("-fx-font-family: '" + ubuntuFont.getName() + "'; -fx-font-size: 36;");
		month.setStyle("-fx-font-family: '" + ubuntuFont.getName() + "'; -fx-font-size: 36;");

		int currentYear = today.getYear();
	    int currentMonth = today.getMonthValue();

        int monthStartFrom = ZonedDateTime.of(today.getYear(), today.getMonthValue(), 1,0,0,0,0,today.getZone()).getDayOfWeek().getValue();
        LocalDate firstCellDate = LocalDate.of(currentYear, currentMonth, 1).minusDays(monthStartFrom - 1);

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
            	int cellIndex = row * 7 + col;
            	LocalDate cellDate =  firstCellDate.plusDays(cellIndex);
            	
            	StackPane cell = new StackPane();
                
                Text cellDateText = new Text(String.valueOf(cellDate.getDayOfMonth()));
                Text cellTaskText = new Text();

                if (cellDate.getMonthValue() != currentMonth) {
                    cell.setStyle("-fx-background-color: lightgray;");
                }
                
                if (taskMap.containsKey(cellDate)) {
                    List<String> tasks = taskMap.get(cellDate);
                	cellTaskText.setText("leaf x " + tasks.size());
                    cellTaskText.setStyle("-fx-font-family: '" + architectFont.getName() + "'; -fx-font-size: 11;");
                    StackPane.setAlignment(cellTaskText, Pos.BOTTOM_CENTER);
                }
                
                if (cellDate.isEqual(today.toLocalDate())) {
                    cell.setStyle("-fx-background-color: #45539D;");
                    cellDateText.setFill(Color.WHITE);
                    cellTaskText.setFill(Color.WHITE);
                }
                
                cell.setOnMouseClicked(event -> {
                	today = cellDate.atStartOfDay(ZoneId.systemDefault());
                    switchDate();
                });

                StackPane.setAlignment(cellDateText, Pos.CENTER);
                cell.getChildren().addAll(cellDateText, cellTaskText);
                gridPane.add(cell, col, row);
            }
        }
	}
	
	private void createSticker() {
        List<String> tasks = taskMap.get(today.toLocalDate());
        
        if(tasks != null) {
        	StringBuilder taskList = new StringBuilder();
        	int displayLimit = 5;
        	
        	for (int i = 0; i < Math.min(tasks.size(), displayLimit); i++) {
                taskList.append("- ").append(tasks.get(i)).append("\n");
            }
        	
            if (tasks.size() > displayLimit) {
                taskList.append("* and ").append(tasks.size() - displayLimit).append(" more tasks *");
            }
        	
            stickerText.setText(taskList.toString());
        } else {
            stickerText.setText("- None");
        }
        stickerText.setStyle("-fx-font-family: 'Architects Daughter Regular'; -fx-font-size: 12;");

	}

}
