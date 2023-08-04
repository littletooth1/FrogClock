package application.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DatabaseConnection.DatabaseAccessor;

public class Setting extends Data {
	private int sessionLength = 25;
	private int breakLength = 5;
	private boolean isEndOn = true;
	private boolean isBackgroundOn = true;
	private int bgmID = 0;
	
	private final static String tableName = "SETTING";
    private static final String INSERT_SETTING_QUERY = "INSERT INTO %s (ID, sessionLength,breakLength, isEndOn,isBackgroundOn, bgmID) " + "VALUES ('SETTING',%s, %s,%s , %s, %s);";
    private static final String UPDATE_SETTING_QUERY = "UPDATE %s SET sessionLength = %s , breakLength = %s,isEndOn = %s, isBackgroundOn = %s ,bgmID = %s where ID = '%s'";
    private static final String SELECT_SETTING_QUERY = "SELECT * FROM SETTING";
	
	public Setting() {}
	
	public Setting(ResultSet resultSet) throws SQLException {
		sessionLength = resultSet.getInt("sessionLength");
		breakLength = resultSet.getInt("breakLength");
		isEndOn = resultSet.getBoolean("isEndOn");
		isBackgroundOn = resultSet.getBoolean("isBackgroundOn");
		bgmID = resultSet.getInt("bgmID");
		
	}
	
	public Setting(int sessionLength, int breakLength, boolean isEndOn, boolean isBackgroundOn, int bgmID ) {
		this.sessionLength = sessionLength;
		this.breakLength = breakLength;
		this.isEndOn = isEndOn;
		this.isBackgroundOn = isBackgroundOn;
		this.bgmID = bgmID;
	}

	@Override
	public String getInsertQuery() {
		return String.format(INSERT_SETTING_QUERY, tableName, sessionLength, breakLength,isEndOn,isBackgroundOn,bgmID);

	}

	@Override
	public String getUpdateQuery(String key) {
		return String.format(UPDATE_SETTING_QUERY, tableName, sessionLength, breakLength,isEndOn,isBackgroundOn,bgmID,key);
	}
	
	
	public static Setting getSetting(DatabaseAccessor db, Statement statement) {
		ResultSet resultSet = Data.excuateQuerySql(SELECT_SETTING_QUERY, db, statement);
		Setting setting = null;
		try {
			setting = new Setting(resultSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return setting;
	}
	
	
	public void sessionAddOne() {
		sessionLength++;
	}
	
	public void sessionReduceOne() {
		sessionLength--;
	}
	
	public void breakAddOne() {
		breakLength++;
	}
	
	public void breakReduceOne() {	
		breakLength--;
	}
	
	public int getSessionLength() {
		return sessionLength;
	}

	public void setSessionLength(int sessionLength) {
		this.sessionLength = sessionLength;
	}

	public int getBreakLength() {
		return breakLength;
	}

	public void setBreakLength(int breakLength) {
		this.breakLength = breakLength;
	}

	public boolean isEndOn() {
		return isEndOn;
	}

	public void setEndOn(boolean endOn) {
		isEndOn = endOn;
	}

	public boolean isBackgroundOn() {
		return isBackgroundOn;
	}

	public void setBackgroundOn(boolean backgroundOn) {
		isBackgroundOn = backgroundOn;
	}

	public int getBgmID() {
		return bgmID;
	}

	public void setBgmId(int bgmID) {
		this.bgmID = bgmID;
	}


}