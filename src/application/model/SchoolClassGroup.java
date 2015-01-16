package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SchoolClassGroup {
	
	//================================================================================
	// Properties
	//================================================================================
	private int id ;
	private StringProperty name;
		
	//================================================================================
	// Constructors
	//================================================================================
	public SchoolClassGroup(String name, int id){
			this(name);
			this.id = id;
	}
	public SchoolClassGroup(String name){
		this.name = new SimpleStringProperty(name);
		this.id = -2;
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
	@Override
	public String toString(){
		return "" +this.name.get();
	}


}
