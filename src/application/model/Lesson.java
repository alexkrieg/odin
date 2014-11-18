package application.model;

public class Lesson {
	
    //================================================================================
    // Properties
    //================================================================================
	private Teacher teacher;
	private LearningField learningField;
	private SchoolClass sClass;
	private SchoolClassGroup sClassGroup;
	private Room room;
	
	private String day;
	private String hour;
	
    //================================================================================
    // Cunstructors
    //================================================================================
	public Lesson(Teacher t, LearningField f, SchoolClass s, SchoolClassGroup g, Room r, String day, String hour){
		this.setTeacher(t);
		this.setLearningField(f);
		this.setsClass(s);
		this.setsClassGroup(g);
		this.setRoom(r);
		this.setDay(day);
		this.setHour(hour);
	}
    //================================================================================
    // Getter / Setter
    //================================================================================
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public LearningField getLearningField() {
		return learningField;
	}
	public void setLearningField(LearningField learningField) {
		this.learningField = learningField;
	}
	public SchoolClass getsClass() {
		return sClass;
	}
	public void setsClass(SchoolClass sClass) {
		this.sClass = sClass;
	}
	public SchoolClassGroup getsClassGroup() {
		return sClassGroup;
	}
	public void setsClassGroup(SchoolClassGroup sClassGroup) {
		this.sClassGroup = sClassGroup;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
    //================================================================================
    // Methods
    //================================================================================
	@Override
	public String toString(){
		String s = ""+getDay()+","+getHour()+","+getsClass()+","+getsClassGroup()+","+getTeacher()+",";
		s += getRoom()+","+getLearningField();
		return s;
	}
}
