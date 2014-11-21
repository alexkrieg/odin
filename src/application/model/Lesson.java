package application.model;

public class Lesson {
	
    //================================================================================
    // Properties
    //================================================================================
	private int id;
	private Teacher teacher;
	private LearningField learningField;
	private SchoolClass sClass;
	private SchoolClassGroup sClassGroup;
	private Room room;
	private LessonTimeInformation timeInformation;
	
    //================================================================================
    // Cunstructors
    //================================================================================
	public Lesson(int id,Teacher t, LearningField f, SchoolClass s, SchoolClassGroup g, Room r, LessonTimeInformation i){
		this(t,f,s,g,r,i);
		this.setId(id);
	}
	public Lesson(Teacher t, LearningField f, SchoolClass s, SchoolClassGroup g, Room r, LessonTimeInformation i){
		this.setTeacher(t);
		this.setLearningField(f);
		this.setsClass(s);
		this.setsClassGroup(g);
		this.setRoom(r);
		this.setTimeInformation(i);
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
    public LessonTimeInformation getTimeInformation() {
		return timeInformation;
	}
	public void setTimeInformation(LessonTimeInformation timeInformation) {
		this.timeInformation = timeInformation;
	}
	//================================================================================
    // Methods
    //================================================================================
	@Override
	public String toString(){
		String s = ""+getTimeInformation()+","+getsClass()+","+getsClassGroup()+","+getTeacher()+",";
		s += getRoom()+","+getLearningField();
		return s;
	}
}
