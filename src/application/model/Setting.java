package application.model;

public class Setting extends Data {
	private int sessionLength = 25;
	private int breakLength = 5;
	private boolean isEndOn = true;
	private boolean isBackgroundOn = true;
	private String backgroundName = "Default";
	
	private final static String tableName = "SETTING";
    private static final String INSERT_SETTING_QUERY = "INSERT INTO %s (ID, sessionLength,breakLength, isEndOn,isBackgroundOn, backgroundName) " + "VALUES ('SETTING',%s, %s,%s , %s, '%s');";
    private static final String UPDATE_SETTING_QUERY = "UPDATE %s SET sessionLength = %s , breakLength = %s,isEndOn = %s, isBackgroundOn = %s ,backgroundName = '%s' where ID = '%s'";

	
	public Setting() {}
	
	public Setting(int sessionLength, int breakLength, boolean isEndOn, boolean isBackgroundOn, String backgroundName ) {
		this.sessionLength = sessionLength;
		this.breakLength = breakLength;
		this.isEndOn = isEndOn;
		this.isBackgroundOn = isBackgroundOn;
		this.backgroundName = backgroundName;
	}

	@Override
	public String getInsertQuery() {
		return String.format(INSERT_SETTING_QUERY, tableName, sessionLength, breakLength,isEndOn,isBackgroundOn,backgroundName);

	}

	@Override
	public String getUpdateQuery(String key) {
		return String.format(UPDATE_SETTING_QUERY, tableName, sessionLength, breakLength,isEndOn,isBackgroundOn,backgroundName,key);
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

	public java.lang.String getBackgroundName() {
		return backgroundName;
	}

	public void setBackgroundName(java.lang.String backgroundName) {
		this.backgroundName = backgroundName;
	}


}