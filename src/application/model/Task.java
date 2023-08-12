package application.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DatabaseConnection.DatabaseAccessor;


public class Task extends Data {
	private String taskName;
	private boolean isActive;
	private final static String tableName = "TASK";
    private static final String INSERT_TASK_QUERY = "INSERT INTO %s (taskName,isActive) " + "VALUES ('%s', %s);";
    private static final String UPDATE_TASK_QUERY = "UPDATE %s SET taskName = '%s', isActive = %s where taskName = '%s'";
    private static final String DELETE_TASK_QUERY = "DELETE FROM %s where taskName = '%s'";


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
	
	public boolean isActive() {
		return isActive;
	}
	

	@Override
	public String getInsertQuery() {
		return String.format(INSERT_TASK_QUERY, tableName, taskName, isActive);
	}

	@Override
	public String getUpdateQuery(String oldTaskName) {		
		String s =  String.format(UPDATE_TASK_QUERY, tableName, taskName, isActive, oldTaskName);
		return s;
	}
	
	@Override
	public String getDeleteQuery(String oldTaskName) {		
		String s =  String.format(DELETE_TASK_QUERY, tableName, taskName);
		System.out.println(s);
		return s;
	}
	
	

	public static List<Task> getActiveTasks(DatabaseAccessor db, Statement statement) {
		String sql = "SELECT * From TASK";
		List<Task> activeTasks = new ArrayList<>(); 

		try {
            ResultSet resultSet = Data.excuateQuerySql(sql, db, statement);
            while (resultSet.next()) {
            	activeTasks.add(new Task(resultSet));
            }
            return activeTasks;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getErrorCode());
        }
        return null;
	}

}
