package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.DataFormat;

import org.apache.commons.lang3.StringUtils;


public class Teacher {
	
    //================================================================================
    // Properties
    //================================================================================
	public static final DataFormat FORMAT = new DataFormat("application.model.Teacher");
	
	private int id;
	private StringProperty firstName;
	private StringProperty lastName;
	
	private ObservableList<LearningField> learningFields;
    //================================================================================
    // Constructors
    //================================================================================
	public Teacher(){
		this(null,null);
	}
	public Teacher(int id, String firstName, String lastName){
		this(firstName,lastName);
		this.id = id;
	}
	public Teacher(String firstName, String lastName){
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.learningFields = FXCollections.observableArrayList();
	}
    //================================================================================
    // Setter
    //================================================================================
	public void addLearningField(LearningField field){
		this.learningFields.add(field);
	}
	public void removeLearningField(LearningField field){
		this.learningFields.remove(field);
	}
	public void removeLearningFieldAtIndex(int index){
		this.learningFields.remove(index);
	}
	public void setFirstName(String firstName){
		this.firstName.set(firstName);
	}
	public void setLastName(String lastName){
		this.lastName.set(lastName);
	}
	public void setLearningFields(ObservableList<LearningField> fields) {
		this.learningFields = fields;
	}
    //================================================================================
    // Accessors
    //================================================================================
	public StringProperty firstNameProperty(){
		return this.firstName;
	}
	public StringProperty lastNameProperty(){
		return this.lastName;
	}
	public ObservableList<LearningField> learningFieldProperty(){
		return this.learningFields;
	}
	public ObservableList<String> learningFieldPropertyStrings(){
		ObservableList<String> ret = FXCollections.observableArrayList();
		for(LearningField f :this.learningFields){
			ret.add(f.toString());
		}
		return ret;
	}
	public String getLearningFields(){
		String result = StringUtils.join(this.learningFields, ", ");
		return result;
	}
	public int getID(){
		return this.id;
	}
	public String getLearningFieldNames(){
		String result = "";int counter = 1;
		for (LearningField learningField : this.learningFields) {
			if(counter == this.learningFields.size()){
				result = learningField.getNameProperty().get();
			}
			else{
				result = result + learningField.getNameProperty().get() +", ";
			}
			counter ++;
		}
		return result;
	}
	@Override
	public String toString() {
		String vorname = this.firstName.get();
		String nachname = this.lastName.get();
		return vorname+" "+nachname;
	}
}
