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
	private LessonTimeInformation lessonTimeInformation;
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
		this.lessonTimeInformation = timeInformation;
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
	public SimpleObjectProperty<Lesson> getLessonForDayAndTime(String day){
		// Only if for this.lesson time onformation
		for(Lesson l : lessons){
			if(l.getTimeInformation().getDay() == day){
				if(l.getTimeInformation().getTime().equals(this.getLessonTimeInformation().getTime())){
					return new SimpleObjectProperty<Lesson>(l);
				}else{
					return new SimpleObjectProperty<Lesson>(new Lesson(this.getLessonTimeInformation()));
				}
			}
		}
		return new SimpleObjectProperty<Lesson>();
	}
	public StringProperty getTimeInformation() {
		return timeInformation;
	}
	public void setTimeInformation(StringProperty timeInformation) {
		this.timeInformation = timeInformation;
	}
	public LessonTimeInformation getLessonTimeInformation(){
		return this.lessonTimeInformation;
	}
	@Override
	public String toString(){
		return this.timeInformation+" - "+this.lessons+"";
	}
}
