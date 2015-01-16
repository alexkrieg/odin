package application.model;

public class LessonTimeInformation {
	
    //================================================================================
    // Properties
    //================================================================================
	private int day;
	private int hour;
	private String dayString;
	private String timeFrom;
	private String timeTo;
	
    //================================================================================
    // Cunstructors
    //================================================================================
	public LessonTimeInformation(int day, int hour){
		this.setDay(day);
		this.setHour(hour);
		this.setTimeInformation();
	}
    //================================================================================
    // Inits
    //================================================================================
	private void setTimeInformation(){
		switch (this.day) {
		case 1:
			this.setDayString("Montag");
			break;
		case 2:
			this.setDayString("Dienstag");
			break;
		case 3:
			this.setDayString("Mittwoch");
			break;
		case 4:
			this.setDayString("Donnerstag");
			break;
		case 5:
			this.setDayString("Freitag");
			break;
		default:
			this.setDayString("Freitag");
			break;
		}
		switch (this.hour) {
		case 0:
			this.setTimeFrom("08:00");
			this.setTimeTo("08:45");
			break;
		case 1:
			this.setTimeFrom("08:45");
			this.setTimeTo("09:30");
			break;
		case 2:
			this.setTimeFrom("09:45");
			this.setTimeTo("10:30");
			break;
		case 3:
			this.setTimeFrom("10:30");
			this.setTimeTo("11:15");
			break;
		case 4:
			this.setTimeFrom("11:30");
			this.setTimeTo("12:15");
			break;
		case 5:
			this.setTimeFrom("12:15");
			this.setTimeTo("13:00");
			break;
		case 6:
			this.setTimeFrom("13:30");
			this.setTimeTo("14:15");
			break;
		case 7:
			this.setTimeFrom("14:15");
			this.setTimeTo("15:00");
			break;
		case 8:
			this.setTimeFrom("15:15");
			this.setTimeTo("16:00");
			break;
		case 9:
			this.setTimeFrom("16:00");
			this.setTimeTo("16:45");
			break;
		default:
			this.setTimeFrom("");
			this.setTimeTo("");
			break;
		}
	}
    //================================================================================
    // Getter / Setter
    //================================================================================
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
	public String getDayString() {
		return dayString;
	}
	public void setDayString(String dayString) {
		this.dayString = dayString;
	}
	//================================================================================
    // Methods
    //================================================================================
	public String getTime(){
		return "von "+getTimeFrom()+" bis "+getTimeTo()+" Uhr";
	}
	@Override
	public String toString(){
		String s = ""+getDay()+","+getHour()+"-"+","+getTimeFrom()+"-";
		s += getTimeTo();
		return s;
	}
}
