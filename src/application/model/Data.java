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
	public String getUpdateQuery(String key) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static void excuateUpdateSql(String sql, DatabaseAccessor db) {
		try {
            Statement statement = db.getConnection().createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		
	}
	
	
	//add statement argument
	public static ResultSet excuateQuerySql(String sql, DatabaseAccessor db, Statement statement) {
		try {
            ResultSet resultSet = statement.executeQuery(sql);
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
		System.out.println(sql);
		excuateUpdateSql(sql,db);
		System.out.println("query executed" + sql);
        System.out.println("Update data successfully" + this.getClass());
	}
	

}
