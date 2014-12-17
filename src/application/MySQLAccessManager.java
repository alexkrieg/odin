package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import application.model.LearningField;
import application.model.Room;
import application.model.SchoolClass;
import application.model.Teacher;

public class MySQLAccessManager {
	
	//cstefanherr.noip.me:42080/adminer.php für Browser
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public MySQLAccessManager(){
		this.connectToMySQL("stefanherr.noip.me:42042", "mydb", "javaprojekt", "javaprojekt*");
	}
	public boolean connectToMySQL(String host, String database, String user, String password)
	{
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectCommand = "jdbc:mysql://"+host+"/"+database+"?user="+user+"&password="+password;
			connect = DriverManager.getConnection(connectCommand);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean checkTeacherIdExisting(String strId) {
		
		boolean retVal = false;
		Statement stmt = null;
		String strSql ="SELECT * FROM mydb.teacher WHERE id_teacher = '" + strId +"'";
		
		try {
			stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(strSql);
			if (rs.next()){
				retVal = true;
			} else {
				retVal = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public boolean addNewTeacher(Teacher t) {
		
		boolean retVal = false;
		Statement stmt = null;
		String strId = t.getIdentifier().get(); 
		String strFirstName= t.firstNameProperty().get();
		String strLastName= t.lastNameProperty().get();
		
		String strSql ="INSERT INTO mydb.teacher (id_teacher, firstname, lastname) " +
							"VALUES ('"+strId+"','"+strFirstName+"','"+strLastName+"')" ;
		
		try {
			stmt = connect.createStatement();
			if (checkTeacherIdExisting(strId)==false) {
				stmt.execute(strSql);
				retVal = true;
			} else {
				retVal = false;
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public ArrayList<Teacher> selectAllTeacher() {
		
		ArrayList<Teacher> retArrayList = new ArrayList<Teacher>();
		Statement stmt = null;
		String strSql = "SELECT * FROM mydb.teacher ORDER BY lastname, firstname";
		
		try {
			stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(strSql);
			while (rs.next()) {
				Teacher tempT = new Teacher();
				tempT.setIdentifier(rs.getNString(1));
				tempT.setFirstName(rs.getNString(2));
				tempT.setLastName(rs.getNString(3));
				retArrayList.add(tempT);
				
				//System.out.println("Lehrer gefudnen");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retArrayList;
	}
	
	public boolean addNewLearningField(LearningField lf) {
		
		boolean retVal = false;
		Statement stmt = null; 
		String strDescription = lf.getDescriptionProperty().get();
		String strName = lf.getNameProperty().get();
		
		String strSql ="INSERT INTO mydb.learning_field (name, description) " +
							"VALUES ('"+strDescription+"','"+strName+"')" ;
		try {
			stmt = connect.createStatement();
			stmt.execute(strSql);
			retVal = true;	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	public ArrayList<LearningField> selectAllLearningFields() {
		ArrayList<LearningField> retArrayList = new ArrayList<LearningField>();
		Statement stmt = null;
		String strSql = "SELECT name, description, id_learning_field FROM mydb.learning_field ORDER BY name";
		try {
			stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(strSql);
			while (rs.next()) {
				LearningField tempT = new LearningField(rs.getNString(1), rs.getNString(2), rs.getInt(3));
				retArrayList.add(tempT);
				
				//System.out.println("Lehrer gefudnen");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retArrayList;
	}
	public boolean addNewRoom(Room r) {
		
		boolean retVal = false;
		Statement stmt = null; 
		String strName = r.getName().get();
		
		String strSql ="INSERT INTO mydb.room  (name)  " + 
							"VALUES ('"+strName+"')" ;
		try {
			stmt = connect.createStatement();
			stmt.execute(strSql);
			retVal = true;	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public ArrayList<Room> selectAllRooms() {
		ArrayList<Room> retArrayList = new ArrayList<Room>();
		Statement stmt = null;
		String strSql = "SELECT name, id_room FROM mydb.room ORDER BY name";
		try {
			stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(strSql);
			while (rs.next()) {
				Room tempR = new Room(rs.getNString(1),rs.getInt(2));
				retArrayList.add(tempR);
				
				//System.out.println("Lehrer gefudnen");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retArrayList;
	}
	
	public boolean addNewClass(SchoolClass sc) {
		
		boolean retVal = false;
		Statement stmt = null; 
		String strName = sc.getName().get();
		String strClassTeacherID = sc.getClassTeacher().getIdentifier().get();
		
		
		String strSql ="INSERT INTO mydb.class  (name, fk_identifier_teacher) " + 
							"VALUES ('"+strName+"', '"+strClassTeacherID+"')" ;
		try {
			stmt = connect.createStatement();
			stmt.execute(strSql);
			retVal = true;	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public ArrayList<SchoolClass> selectAllClasses() {
		ArrayList<SchoolClass> retArrayList = new ArrayList<SchoolClass>();
		Statement stmt = null;
		String strSql = "SELECT name, id_class, fk_identifier_teacher, "+
							"(SELECT firstname FROM mydb.teacher WHERE id_teacher = fk_identifier_teacher) firstname, "+
							"(SELECT lastname FROM mydb.teacher WHERE id_teacher = fk_identifier_teacher) lastname "+
							"FROM mydb.room ORDER BY name";
		System.out.println(strSql);
		try {
			stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(strSql);
			while (rs.next()) {
				Teacher tempClassTeacher = new Teacher(rs.getNString(4),rs.getNString(5));
				tempClassTeacher.setIdentifier(rs.getNString(3));
				SchoolClass tempSC = new SchoolClass(rs.getNString(1), rs.getInt(2), tempClassTeacher);
				retArrayList.add(tempSC);
				//System.out.println("Lehrer gefudnen");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retArrayList;
	}
	
}
