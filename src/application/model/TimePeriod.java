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
	private Lesson[] lessons;
	private LessonTimeInformation lessonTimeInformation;
	private StringProperty timeInformation;
	private int hour;
	
	//================================================================================
    // Contructors
    //================================================================================
	public TimePeriod(int hour){
		setLessons(new Lesson[5]);
		this.setHour(hour);
		
	}
	public TimePeriod(ObservableList<Lesson> list, LessonTimeInformation timeInformation, int hour){
		this(hour);
		this.lessonTimeInformation = timeInformation;
		this.timeInformation = new SimpleStringProperty(timeInformation.getTime());
		setLessons(new Lesson[5]);
		
	}
	//================================================================================
    // Getter / Setter
    //================================================================================
	public Lesson[] getLessons() {
		return lessons;
	}
	public void setLessons(Lesson[] lessons) {
		this.lessons = lessons;
	}
	public SimpleObjectProperty<Lesson> getLessonForDayAndTime(int day){
		// Only if for this.lesson time onformation
		for(int i = 0 ; i<lessons.length;i++){
			Lesson l = lessons[i];
			if(l == null){
				return new SimpleObjectProperty<Lesson>(new Lesson(new LessonTimeInformation(0, 0, "sdsd", "sdds")));
			}
			if(l.getTimeInformation().getDay() == day){
				if(l.getTimeInformation().getHour() == this.getHour()){
					return new SimpleObjectProperty<Lesson>(l);
				}else{
					return new SimpleObjectProperty<Lesson>(new Lesson(new LessonTimeInformation(0, 0, "sdsd", "sdds")));
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
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public void addLessonAtIndex(Lesson l, int index){
		this.lessons[index]= l;
	}
	@Override
	public String toString(){
		return this.timeInformation+" - "+this.lessons+"";
	}
}
