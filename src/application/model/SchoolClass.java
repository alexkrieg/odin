package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SchoolClass {
    //================================================================================
    // Properties
    //================================================================================
	private int id ;
	private StringProperty name;
	private StringProperty description;
	
    //================================================================================
    // Constructors
    //================================================================================
	public SchoolClass(String name, int id){
		this(name);
		this.id = id;
	}
	public SchoolClass(String name){
		this.name = new SimpleStringProperty(name);
		this.description = new SimpleStringProperty("");
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
	public StringProperty getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description.set(description);
	}
	@Override
	public String toString(){
		return this.id +"-" +this.name.get();
	}
}
