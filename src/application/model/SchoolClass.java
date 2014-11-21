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
	private StringProperty description;
	private ObservableList<SchoolClassGroup> groups;
	
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
		this.groups = FXCollections.observableArrayList();
		this.groups.add(new SchoolClassGroup("Alle Sch¸ler"));
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
	public ObservableList<SchoolClassGroup>getGroups(){
		return this.groups;
	}
	@Override
	public String toString(){
		return ""+this.name.get();
	}
}
