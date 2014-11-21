package application.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TimePeriod {
	
	//================================================================================
    // Properties
    //================================================================================
	private ObservableList<Lesson> lessons;
	private StringProperty timeInformation;
	
	//================================================================================
    // Contructors
    //================================================================================
	public TimePeriod(){
		setLessons(FXCollections.observableArrayList());
	}
	public TimePeriod(ObservableList<Lesson> list, LessonTimeInformation timeInformation){
		this();
		setLessons(list);
		this.timeInformation = new SimpleStringProperty();
	}
	//================================================================================
    // Getter / Setter
    //================================================================================
	public ObservableList<Lesson> getLessons() {
		return lessons;
	}
	public void setLessons(ObservableList<Lesson> lessons) {
		this.lessons = lessons;
	}
	public Lesson getLessonForDay(String day){
		for(Lesson l : lessons){
			if(l.getTimeInformation().getDay() == day){
				return l;
			}
		}
		return null;
	}
	public StringProperty getTimeInformation() {
		return timeInformation;
	}
	public void setTimeInformation(StringProperty timeInformation) {
		this.timeInformation = timeInformation;
	}
}
