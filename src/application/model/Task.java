package application.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Task extends Data {
	private String taskName;
	private boolean isActive;
	private final static String tableName = "TASK";
    private static final String INSERT_TASK_QUERY = "INSERT INTO %s (taskName,isActive) " + "VALUES ('%s', '%s');";
    private static final String UPDATE_TASK_QUERY = "UPDATE %s SET taskName = '%s', isActive = %s where taskName = '%s'";


	public Task(String name, boolean isActive) {
		this.taskName = name;
		this.isActive = isActive;
	}

	public Task(ResultSet resultSet) throws SQLException {
		taskName = resultSet.getString("taskName");
		isActive = resultSet.getBoolean("isActive");
	}
	
	public void setTaskName(String newTaskName) {
		taskName = newTaskName;
	}
	
	public void setIsActive(boolean newIsActive) {
		isActive = newIsActive;
	}
	
	public String getTaskName() {
		return taskName;
	}
	

	@Override
	public String getInsertQuery() {
		return String.format(INSERT_TASK_QUERY, tableName, taskName, isActive);
	}

	@Override
	public String getUpdateQuery(String oldTaskName) {		
		String s =  String.format(UPDATE_TASK_QUERY, tableName, taskName, isActive, oldTaskName);
		System.out.println(s);
		return s;
	}


	

}
