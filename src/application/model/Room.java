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
	public Room(String name, int id){
		this(name);
		this.id=id;
	}
	public Room(String name){
		this.name= new SimpleStringProperty(name);
		this.characteristic= new SimpleStringProperty("");
		this.id = 0;
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
	@Override
	public String toString(){
		return this.id+"-"+this.name.get();
	}

}
