package application.model;

public class LessonTimeInformation {
	
    //================================================================================
    // Properties
    //================================================================================
	private int id;
	private int day;
	private int hour;
	private String timeFrom;
	private String timeTo;
	
    //================================================================================
    // Cunstructors
    //================================================================================
	public LessonTimeInformation(int id,int day, int hour, String timeFrom, String timeTo){
		this(day,hour,timeFrom,timeTo);
		this.setId(id);
	}
	public LessonTimeInformation(int day, int hour, String timeFrom, String timeTo){
		this.setDay(day);
		this.setHour(hour);
		this.setTimeFrom(timeFrom);
		this.setTimeTo(timeTo);
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
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public String getTimeFrom() {
		return timeFrom;
	}
	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}


	public String getTimeTo() {
		return timeTo;
	}
	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}
	//================================================================================
    // Methods
    //================================================================================
	public String getTime(){
		String s = ""+getHour()+" "+"\n"+getTimeFrom()+" - "+getTimeTo()+" Uhr";
		return s;
	}
	@Override
	public String toString(){
		String s = ""+getDay()+","+getHour()+"-"+","+getTimeFrom()+"-";
		s += getTimeTo()+","+getId();
		return s;
	}
}
