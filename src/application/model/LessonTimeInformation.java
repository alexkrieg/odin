package application.model;

public class LessonTimeInformation {
	
    //================================================================================
    // Properties
    //================================================================================
	private int id;
	private String day;
	private String hourFrom;
	private String hourTo;
	private String timeFrom;
	private String timeTo;
	
    //================================================================================
    // Cunstructors
    //================================================================================
	public LessonTimeInformation(int id,String day, String hourFrom, String hourTo, String timeFrom, String timeTo){
		this(day,hourFrom,hourTo,timeFrom,timeTo);
		this.setId(id);
	}
	public LessonTimeInformation(String day, String hourFrom, String hourTo, String timeFrom, String timeTo){
		this.setDay(day);
		this.setHourFrom(hourFrom);
		this.setHourTo(hourTo);
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
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getHourFrom() {
		return hourFrom;
	}
	public void setHourFrom(String hourFrom) {
		this.hourFrom = hourFrom;
	}
	public String getTimeFrom() {
		return timeFrom;
	}
	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}
	public String getHourTo() {
		return hourTo;
	}
	public void setHourTo(String hourTo) {
		this.hourTo = hourTo;
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
	@Override
	public String toString(){
		String s = ""+getDay()+","+getHourFrom()+"-"+getHourTo()+","+getTimeFrom()+"-";
		s += getTimeTo()+","+getId();
		return s;
	}
}
