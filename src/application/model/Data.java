package application.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import DatabaseConnection.DatabaseAccessor;

public abstract class Data {

	public Data() {}
	
	
	public abstract String getInsertQuery();
	public abstract String getUpdateQuery(String key);
	
	private void excuateUpdateSql(String sql, DatabaseAccessor db) {
		try {
            Statement statement = db.getConnection().createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		
	}
	
	
	private static ResultSet excuateQuerySql(String sql, DatabaseAccessor db) {
		try {
            Statement statement = db.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            statement.close();
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	
	
	
	public void addToDB(DatabaseAccessor db) {
		String sql = getInsertQuery();
		excuateUpdateSql(sql,db);
        System.out.println("Add data successfully" + this.getClass());
	}
	
	public void updateToDB(DatabaseAccessor db, String key) {
		String sql = getUpdateQuery(key);
		excuateUpdateSql(sql,db);
        System.out.println("Update data successfully" + this.getClass());
	}
	
	
	
	
}
