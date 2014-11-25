package application.model;

import application.MainApplication;
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
		this.timeInformation = new SimpleStringProperty(timeInformation.getTime());
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
	public SimpleObjectProperty<Lesson> getLessonForDay(String day){
		MainApplication.log("Lesson for day"+day+"in "+this.lessons);
		for(Lesson l : lessons){
			if(l.getTimeInformation().getDay() == day){
				return new SimpleObjectProperty<Lesson>(l);
			}
		}
		MainApplication.log("jhvkgfutdtdktu");
		return new SimpleObjectProperty<Lesson>();
	}
	public StringProperty getTimeInformation() {
		return timeInformation;
	}
	public void setTimeInformation(StringProperty timeInformation) {
		this.timeInformation = timeInformation;
	}
}
