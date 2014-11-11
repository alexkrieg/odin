package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class MySQLAccessManager {
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public MySQLAccessManager(){
		
	}
	public boolean connectToMySQL(String host, String database, String user, String password)
	{
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectCommand = "jdbc:mysql://"+host+"/"+database+"?user="+user+"&password="+password;
			connect = DriverManager.getConnection(connectCommand);
			Statement stmt = connect.createStatement();
			String sqrtl = "SELECT * FROM teacher";
			ResultSet rs = stmt.executeQuery(sqrtl);
			while(rs.next()){
				MainApplication.log(rs.toString());
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public String executeQuery(String query){
		
		return "";
	}
}
