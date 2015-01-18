package application.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TimePeriod {
	
	//================================================================================
    // Properties
    //================================================================================
	private ObservableList<ObservableList<Lesson>> lessonss;
	private int hour;
	
	//================================================================================
    // Contructors
    //================================================================================
	public TimePeriod(int hour){
		lessonss = FXCollections.observableArrayList();
//		for(ObservableList<Lesson> list :lessonss){
//			list = FXCollections.observableArrayList();
//		}
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
	public SimpleObjectProperty<ObservableList<Lesson>> getLessonsForDay(int day){
		for(ObservableList<Lesson> list :lessonss){
			if(list.get(0) != null && list.get(0).getTimeInformation().getDay() == day){
				return new SimpleObjectProperty<ObservableList<Lesson>>(list);
			}
		}
		Lesson l = new Lesson(new LessonTimeInformation(day,this.hour));
		ObservableList<Lesson> newList = FXCollections.observableArrayList();
		newList.add(l);
		return new SimpleObjectProperty<ObservableList<Lesson>>(newList);
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
	
	public void addLessonsAtIndex(Lesson[] list, int index){
		ObservableList<Lesson> l = FXCollections.observableArrayList(list);
		this.lessonss.add(index,l);
	}
	public void removeAllLessons(){
		this.lessonss.clear();
	}
	@Override
	public String toString(){
		return this.getHour()+" - "+this.lessonss+"";
	}
}
