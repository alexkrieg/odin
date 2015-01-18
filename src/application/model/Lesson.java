package application.model;

public class Lesson {
	
    //================================================================================
    // Properties
    //================================================================================
	private int id;
	private String identifier;
	private Teacher teacher;
	private LearningField learningField;
	private SchoolClass sClass;
	private SchoolClassGroup sClassGroup;
	private Room room;
	private LessonTimeInformation timeInformation;
	private boolean empty;
	private boolean teacherAvailable;
	private boolean roomAvailable;
	private boolean classAvailable;
	
    //================================================================================
    // Cunstructors
    //================================================================================
	public Lesson(int id, LessonTimeInformation i){
		this(i);
		this.setId(id);
	}
	public Lesson(LessonTimeInformation i){
		this.setTimeInformation(i);
		this.setEmpty(true);
		this.setTeacherAvailable(true);
		this.setRoomAvailable(true);
		this.setClassAvailable(true);
	}
	public Lesson(int id,Teacher t, LearningField f, SchoolClass s, SchoolClassGroup g, Room r, LessonTimeInformation i){
		this(t,f,s,g,r,i);
		this.setId(id);
	}
	public Lesson(Teacher t, LearningField f, SchoolClass s, SchoolClassGroup g, Room r, LessonTimeInformation i){
		this(i);
		this.setTeacher(t);
		this.setLearningField(f);
		this.setsClass(s);
		this.setsClassGroup(g);
		this.setRoom(r);
		this.setEmpty(false);
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
    public boolean isEmpty() {
		return empty;
	}
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
	public LessonTimeInformation getTimeInformation() {
		return timeInformation;
	}
	public void setTimeInformation(LessonTimeInformation timeInformation) {
		this.timeInformation = timeInformation;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public boolean isTeacherAvailable() {
		return teacherAvailable;
	}
	public void setTeacherAvailable(boolean teacherAvailable) {
		this.teacherAvailable = teacherAvailable;
	}
	public boolean isRoomAvailable() {
		return roomAvailable;
	}
	public void setRoomAvailable(boolean roomAvailable) {
		this.roomAvailable = roomAvailable;
	}
	public boolean isClassAvailable() {
		return classAvailable;
	}
	public void setClassAvailable(boolean classAvailable) {
		this.classAvailable = classAvailable;
	}
	//================================================================================
    // Methods
    //================================================================================
	@Override
	public String toString(){
		if(id == -1){
			return "Neue Stunde hinzufuegen ...";
		}else{
			return "Klasse: "+getsClass()+"\nGruppe: "+getsClassGroup();
		}
	}
	public String makeMePretty(){
		return "Klasse: "+getsClass()+"\nGruppe: "+getsClassGroup()+"\nLehrer: "+getTeacher()+"\nRaum: "+getRoom()+"\nLernfeld: "+getLearningField();
	}
}
