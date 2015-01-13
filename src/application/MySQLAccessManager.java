package application;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import application.model.LearningField;
import application.model.Lesson;
import application.model.LessonTimeInformation;
import application.model.Room;
import application.model.SchoolClass;
import application.model.SchoolClassGroup;
import application.model.Teacher;

public class MySQLAccessManager {
	
	//cstefanherr.noip.me:42080/adminer.php für Browser
    //================================================================================
    // Properties
    //================================================================================
	private Connection connect = null;
	
    //================================================================================
    // Constructor
    //================================================================================
	public MySQLAccessManager(){
		this.connectToMySQL("stefanherr.noip.me:42042", "mydb", "javaprojekt", "javaprojekt*");
	}
    //================================================================================
    // Connector
    //================================================================================
	private boolean connectToMySQL(String host, String database, String user, String password)
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
    //================================================================================
    // Teacher
    //================================================================================
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
		
		ObservableList<LearningField> list = t.learningFieldProperty(); 
		for (LearningField lf : list) {
			strSql = "INSERT INTO mydb.teacher_has_learning_fields (teacher_id, learning_field_id ) " +
						"VALUES ('" + strId + "', " + lf.getID() + ")";
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
				
				strSql = "SELECT teacher_id, learning_field_id, (SELECT name FROM mydb.learning_field where id_learning_field = learning_field_id) name, " +
				          "(SELECT description FROM mydb.learning_field where id_learning_field = learning_field_id) descr " +
						" FROM mydb.teacher_has_learning_fields WHERE teacher_id = '" + rs.getNString(1) + "'";
				try {
					stmt = connect.createStatement();
					ResultSet rsLf = stmt.executeQuery(strSql);
					while (rsLf.next()) {
						LearningField lf = new LearningField(rsLf.getNString("name"), rsLf.getNString("descr"), rsLf.getInt("learning_field_id"));
						tempT.addLearningField(lf);
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
								
				retArrayList.add(tempT);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retArrayList;
	}
	public boolean removeTeacher(Teacher t){
		boolean retVal = false;
		
		try {
			Statement stmt = null; 
		
			String strSql = "UPDATE mydb.class "+
								"SET fk_identifier_teacher = null " +
								"WHERE fk_identifier_teacher = '" + t.getIdentifier().get() + "'";
			stmt = connect.createStatement();
			stmt.execute(strSql);
			stmt = null;
			
			strSql = "DELETE FROM teacher_has_learning_fields " +
					"WHERE teacher_id = '"+t.getIdentifier().get()+"'"; 
			stmt = connect.createStatement();
			stmt.execute(strSql);
			stmt = null;
			
			strSql = "UPDATE mydb.main_mapping " +
					"SET fk_teacher = null " +
					"WHERE fk_teacher = '" + t.getIdentifier().get() + "'";
			stmt = connect.createStatement();
			stmt.execute(strSql);
			stmt = null;
			
			strSql = "DELETE FROM mydb.teacher " +
						"WHERE id_teacher = '" +t.getIdentifier().get()+ "'";
			stmt = connect.createStatement();
			stmt.execute(strSql);
			stmt = null;
			
			retVal = true;	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public boolean updateTeacher(Teacher t){
		boolean retVal = false;
		Statement stmt = null; 
		
		try {
			String strSql = "UPDATE mydb.teacher "+
							"SET firstname = '" + t.firstNameProperty().get() + "', " +
							"lastname = '"+ t.lastNameProperty().get() +"' " +
							"WHERE id_teacher = '" + t.getIdentifier().get() + "'";
		
			stmt = connect.createStatement();
			stmt.execute(strSql);
			
			strSql = "DELETE FROM mydb.teacher_has_learning_fields " +
						"WHERE teacher_id = '" + t.getIdentifier().get() + "'";
			
			stmt = connect.createStatement();
			stmt.execute(strSql);
			
			for(LearningField lf : t.learningFieldProperty()) {
				strSql = "INSERT INTO mydb.teacher_has_learning_fields " +
							"(teacher_id, learning_field_id) values " +
							"('" + t.getIdentifier().get() + "', " + lf.getID() + ")";
				stmt = connect.createStatement();
				stmt.execute(strSql);
				
			}
			
			retVal = true;	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
    //================================================================================
    // LearningFields
    //================================================================================
	public boolean addNewLearningField(LearningField lf) {
		
		boolean retVal = false;
		Statement stmt = null; 
		String strDescription = lf.getDescriptionProperty().get();
		String strName = lf.getNameProperty().get();
		
		String strSql ="INSERT INTO mydb.learning_field (name, description) " +
							"VALUES ('"+strName+"','"+strDescription+"')" ;
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
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retArrayList;
	}
	
	public boolean removeLearningField(LearningField f){
		boolean retVal = false;
		
		try {
			Statement stmt = null; 
			
			String strSql = "UPDATE mydb.main_mapping " +
					"SET fk_learning_field = null " +
					"WHERE fk_learning_field = " + f.getID() + "";
			stmt = connect.createStatement();
			stmt.execute(strSql);
			stmt = null;
			
			strSql = "DELETE FROM mydb.teacher_has_learning_fields " +
					"WHERE learning_field_id = '" +f.getID()+ "'";
			stmt = connect.createStatement();
			stmt.execute(strSql);
			stmt = null;
		
			strSql = "DELETE FROM mydb.learning_field " +
					"WHERE id_learning_field = '" +f.getID()+ "'";
			stmt = connect.createStatement();
			stmt.execute(strSql);
		stmt = null;
			
			retVal = true;	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	public boolean updateLearningField(LearningField f){
		boolean retVal = false;
		Statement stmt = null; 
		
		try {
			String strSql = "UPDATE mydb.learning_field "+
							"SET name = '" + f.getNameProperty().get() + "', " +
							"description = '"+ f.getDescriptionProperty().get() +"' " +
							"WHERE id_learning_field = '" + f.getID() + "'";
			
			stmt = connect.createStatement();
			stmt.execute(strSql);
			
			retVal = true;	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
    //================================================================================
    // Room
    //================================================================================
	public boolean addNewRoom(Room r) {
		
		boolean retVal = false;
		Statement stmt = null; 
		String strName = r.getName().get();
		
		String strSql ="INSERT INTO mydb.room  (name, room_description)  " + 
							"VALUES ('"+strName+"', '"+r.getCharacteristic().get()+"')" ;
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
		String strSql = "SELECT name, id_room, room_description FROM mydb.room ORDER BY name";
		try {
			stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(strSql);
			while (rs.next()) {
				Room tempR = new Room(rs.getInt(2), rs.getNString(1), rs.getNString(3));
				retArrayList.add(tempR);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retArrayList;
	}
	public boolean removeRoom(Room r){
		boolean retVal = false;
		
		Statement stmt = null; 
		try {
			String strSql ="UPDATE mydb.main_mapping " +
							"set fk_room = null WHERE fk_room = "+ r.getId();
			
			stmt = connect.createStatement();
			stmt.execute(strSql);
			
			strSql ="DELETE FROM mydb.room WHERE id_room = " + r.getId();
			stmt = connect.createStatement();
			stmt.execute(strSql);
			retVal = true;	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public boolean updateRoom(Room r) {
		
		boolean retVal = false;
		Statement stmt = null; 
				
		String strSql = "UPDATE mydb.room "+
						"SET name = '" + r.getName().get() + "', " +
						    "room_description = '"+ r.getCharacteristic().get() +"' " +
						"WHERE id_room = " + r.getId();
		
		try {
			stmt = connect.createStatement();
			stmt.execute(strSql);
			retVal = true;	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
    //================================================================================
    // SchoolClass
    //================================================================================
	public boolean addNewClass(SchoolClass sc) {
		
		boolean retVal = false;
		Statement stmt = null; 
		String strName = sc.getName().get();
		String strClassTeacherID = sc.getClassTeacher().getIdentifier().get();
		
		try {
			String strSql ="INSERT INTO mydb.class  (name, fk_identifier_teacher) " + 
							"VALUES ('"+strName+"', '"+strClassTeacherID+"')" ;
			stmt = connect.createStatement();
			stmt.execute(strSql);
			
			strSql = "INSERT INTO mydb.class_has_class_types (class_id, class_types_id) VALUES " +
						"((SELECT max(id_class) from mydb.class WHERE name = '" + strName + "'), -1)";
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
		Statement stmtGroup = null;
		String strSql = "SELECT name, id_class, fk_identifier_teacher, "+
							"(SELECT firstname FROM mydb.teacher WHERE id_teacher = fk_identifier_teacher) firstname, "+
							"(SELECT lastname FROM mydb.teacher WHERE id_teacher = fk_identifier_teacher) lastname "+
							"FROM mydb.class ORDER BY id_class";
		
		try {
			stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(strSql);
			ResultSet rsGroups = null;
			while (rs.next()) {
				Teacher tempClassTeacher = new Teacher(rs.getNString(4),rs.getNString(5));
				tempClassTeacher.setIdentifier(rs.getNString(3));
				SchoolClass tempSC = new SchoolClass(rs.getNString(1), rs.getInt(2), tempClassTeacher);
				ObservableList<SchoolClassGroup> tmpListAllGroups = FXCollections.observableArrayList();
				strSql = "select " +
								"class_types_id, " +
								"(select name from mydb.class_types where id = class_types_id) name " +
								"from mydb.class_has_class_types where class_id = "+rs.getInt(2);
				stmtGroup = connect.createStatement();
				rsGroups = stmtGroup.executeQuery(strSql);
				
				while (rsGroups.next()) {
					SchoolClassGroup tmpSchoolClassGroup = new SchoolClassGroup(rsGroups.getNString("name"), rsGroups.getInt("class_types_id"));
					tmpListAllGroups.add(tmpSchoolClassGroup);
				}
				
				tempSC.setGroups(tmpListAllGroups);
				retArrayList.add(tempSC);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retArrayList;
	}	
	
	public boolean updateClass(SchoolClass sc){
		boolean retVal = false;
		Statement stmt = null; 
		
		String strSql = "UPDATE mydb.class "+
						"SET fk_identifier_teacher = '" + sc.getClassTeacher().getIdentifier().get() + "', " +
						    "name = '"+ sc.getName().get() +"' " +
						"WHERE id_class = " + sc.getId();
		
		try {
			stmt = connect.createStatement();
			stmt.execute(strSql);
			retVal = true;	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	public boolean removeClass(SchoolClass sc) {
		boolean retVal = false;
        Statement stmt = null; 
	
		try {
			
			String  strSql = "UPDATE mydb.main_mapping SET fk_class = null " +
					"WHERE fk_class = " + sc.getId();
			stmt = connect.createStatement();
			stmt.execute(strSql);
			
			strSql = "DELETE FROM class_has_class_types WHERE class_id = " + sc.getId();
			stmt = connect.createStatement();
			stmt.execute(strSql);
			
			strSql = "DELETE FROM mydb.class "+
					"WHERE id_class = " + sc.getId();

			stmt = connect.createStatement();
			stmt.execute(strSql);
			
			retVal = true;	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
    //================================================================================
    // Lesson
    //================================================================================
	public ArrayList<Lesson> getAllLessonByTeacher(Teacher argT)
	{
		ArrayList<Lesson> retArrayList = new ArrayList<Lesson>();
		Statement stmt = null;
		String strSql = "select " +
								"hour, " +
						       "fk_teacher, " +
						       "(select firstname from mydb.teacher te where te.id_teacher = main.fk_teacher) teachFirstName, " +
						       "(select lastname from mydb.teacher te where te.id_teacher = main.fk_teacher) teachLastName, " +
						       " fk_room, " +
						       "(select name from mydb.room ro where id_room = fk_room) room_name, " +
						       "(select room_description from mydb.room where id_room = fk_room) room_description, " +
						       "fk_learning_field, " +
						       " (select name from mydb.learning_field lf where id_learning_field = fk_learning_field) lf_name, " +
						       " (select description from mydb.learning_field lf where id_learning_field = fk_learning_field) lf_description, " +
						       "fk_class, " +
						       "(select name from mydb.class cl where id_class = fk_class) class_name, " +
						       "(select fk_identifier_teacher from mydb.class cl where id_class = fk_class) class_name, " +
						       "fk_class_type, " +
						       "(select name from mydb.class_types where id = fk_class_type) class_type_name, " +
						       "day " +
						  "from " +
						       "mydb.main_mapping main " +
						  "where fk_teacher = '" + argT.getIdentifier().get()+"'";
		
		try {
			stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(strSql);
			while (rs.next()) {
				Teacher t = new Teacher(rs.getNString("fk_teacher"), rs.getNString("teachFirstName"), rs.getNString("teachLastName"));
				LearningField f = new LearningField(rs.getNString("lf_name"), rs.getNString("lf_description") , rs.getInt("fk_learning_field"));
				Room r =  new Room(rs.getInt("fk_room"), rs.getNString("room_name"), rs.getNString("room_description"));
				SchoolClass s = new SchoolClass(rs.getNString("class_name"), rs.getInt("fk_class"), t);
				SchoolClassGroup g = new SchoolClassGroup(rs.getNString("class_type_name"), rs.getInt("fk_class_type"));
				String string = rs.getNString("hour");
				String[] a = string.split(",");
				LessonTimeInformation i = new LessonTimeInformation(rs.getNString("day"), a[0], a[1], "00:00", "00:00");
				Lesson tempLesson = new Lesson(t, f, s, g, r, i);
				retArrayList.add(tempLesson);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retArrayList;
		
	}
	
	
	public boolean addNewLesson(Lesson l, String hour, String day)
	{
		boolean retVal = false;
		Statement stmt = null; 
		String stringIdTeacher = l.getTeacher().getIdentifier().get();
		int intRoom = l.getRoom().getId();
		int intLearningFieldId = l.getLearningField().getID();
		int intSchoolClassId = l.getsClass().getId();
		int intSchoolClassGroupId = l.getsClassGroup().getId();
		
		
		String strSql ="INSERT INTO mydb.main_mapping  (hour, fk_teacher, fk_room, fk_learning_field, fk_class, fk_class_type, day) " + 
							"VALUES ('"+hour+"', "+stringIdTeacher+", "+intRoom+", "+intLearningFieldId+", "+intSchoolClassId+", "+intSchoolClassGroupId+", '"+day+"' )" ;
		
		try {
			stmt = connect.createStatement();
			stmt.execute(strSql);
			retVal = true;	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
		
	}
	
}
