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
	private ObservableList<Lesson>[] lessonss;
	private int hour;
	
	//================================================================================
    // Contructors
    //================================================================================
	public TimePeriod(int hour){
		lessons = FXCollections.observableArrayList();
		//setLessons(new Lesson[5]);
		this.setHour(hour);
		
	}
//	public TimePeriod(ObservableList<Lesson> list, LessonTimeInformation timeInformation, int hour){
//		this(hour);
//		this.lessonTimeInformation = timeInformation;
//		this.timeInformation = new SimpleStringProperty(timeInformation.getTime());
//		setLessons(new Lesson[5]);
//		
//	}
	//================================================================================
    // Getter / Setter
    //================================================================================
//	public Lesson[] getLessons() {
//		return lessons;
//	}
//	public void setLessons(Lesson[] lessons) {
//		this.lessons = lessons;
//	}
	public SimpleObjectProperty<Lesson> getLessonForDay(int day){
		for(Lesson l: this.lessons){
			//Lesson l = lessons[i];
			if(l != null && l.getTimeInformation().getDay() == day){
				MainApplication.log("Lesson:"+l.makeMePretty());
				return new SimpleObjectProperty<Lesson>(l);
			}
		}
//		for(int i = 0 ; i<lessons.length;i++){
//			Lesson l = lessons[i];
//			if(l != null && l.getTimeInformation().getDay() == day){
//				MainApplication.log("Lesson:"+l.makeMePretty());
//				return new SimpleObjectProperty<Lesson>(l);
//			}
//		}
		Lesson l = new Lesson(new LessonTimeInformation(day,this.hour));
		return new SimpleObjectProperty<Lesson>(l);
	}
	public StringProperty getPrettyTime() {
		LessonTimeInformation lti = new LessonTimeInformation(-1, this.getHour());
		String s = ""+(lti.getHour()+1)+". Stunde\n"+lti.getTime();
		return new SimpleStringProperty(s);
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public void addLessonAtIndex(Lesson l, int index){
		this.lessons.add(index,l);
		//this.lessons[index]= l;
	}
	public void removeAllLessons(){
		this.lessons.clear();
		//this.lessons[index]= l;
	}
	@Override
	public String toString(){
		return this.getHour()+" - "+this.lessons+"";
	}
}
