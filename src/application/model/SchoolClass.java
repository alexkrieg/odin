package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SchoolClass {
    //================================================================================
    // Properties
    //================================================================================
	private int id ;
	private StringProperty name;
	private Teacher classTeacher;
	private ObservableList<SchoolClassGroup> groups;
	
    //================================================================================
    // Constructors
    //================================================================================
	public SchoolClass(String name, int id,Teacher classTeacher){
		this(name,classTeacher);
		this.id = id;
	}
	public SchoolClass(String name, Teacher classTeacher){
		this.name = new SimpleStringProperty(name);
		this.setClassTeacher(classTeacher);
		this.groups = FXCollections.observableArrayList();
	}
	
    //================================================================================
    // Getter / Setter
    //================================================================================
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public StringProperty getName() {
		return name;
	}
	public void setName(String name) {
		this.name.set(name);
	}
	public ObservableList<SchoolClassGroup> getGroups(){
		return this.groups;
	}
	public void setGroups(ObservableList<SchoolClassGroup> groups){
		this.groups = groups;
	}
	public Teacher getClassTeacher() {
		return classTeacher;
	}
	public void setClassTeacher(Teacher classTeacher) {
		this.classTeacher = classTeacher;
	}
	@Override
	public String toString(){
		return ""+this.name.get();
	}
}
