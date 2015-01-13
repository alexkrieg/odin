package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Room {
    //================================================================================
    // Properties
    //================================================================================
	private int id;
	private StringProperty name;
	private StringProperty characteristic;
	
    //================================================================================
    // Constructors
    //================================================================================
	public Room(int id, String name, String characteristic){
		this(name,characteristic);
		this.setId(id);
	}
	public Room(String name,String characteristic){
		this.name= new SimpleStringProperty(name);
		this.characteristic= new SimpleStringProperty(characteristic);
		this.setId(0);
	}
    //================================================================================
    // Accessors
    //================================================================================
	public void setName(String name){
		this.name.set(name);
	}
	public StringProperty getName(){
		return this.name;
	}
	public StringProperty getCharacteristic() {
		return characteristic;
	}
	public void setCharacteristic(String characteristic) {
		this.characteristic.set(characteristic);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString(){
		return ""+this.name.get();
	}

}
