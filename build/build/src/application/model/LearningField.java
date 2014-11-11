package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.DataFormat;

public class LearningField {
    //================================================================================
    // Properties
    //================================================================================
	public static final DataFormat FORMAT = new DataFormat("application.model.LearningField");
	
	private StringProperty name;
	private StringProperty description;
	private int id;
	
    //================================================================================
    // Constructors
    //================================================================================
	public LearningField(){
		this(null,null,0);
	}
	public LearningField(String name, String description, int id){
		this(name,description);
		this.id = id;
	}
	public LearningField(String name, String description){
		this.name = new SimpleStringProperty(name);
		this.description = new SimpleStringProperty(description);
	}
    //================================================================================
    // Setters
    //================================================================================
	public void setDescription(String description){
		this.description.set(description);
	}
	public void setName(String name){
		this.name.set(name);
	}
    //================================================================================
    // Accessors
    //================================================================================
	public StringProperty getNameProperty(){
		return this.name;
	}
	public StringProperty getDescriptionProperty(){
		return this.description;
	}
	public int getID(){
		return this.id;
	}
	@Override
	public String toString(){
		return this.id+"-"+this.name.get()+"-"+this.description.get();
	}
}
