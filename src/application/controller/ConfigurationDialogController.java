package application.controller;

import org.controlsfx.dialog.Dialogs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import application.MainApplication;
import application.model.LearningField;
import application.model.Lesson;
import application.model.LessonTimeInformation;
import application.model.Room;
import application.model.SchoolClass;
import application.model.SchoolClassGroup;
import application.model.Teacher;

public class ConfigurationDialogController {
    //================================================================================
    // Properties
    //================================================================================
	public static final String DIALOG_STAGE_TITLE = "Stunde bearbeiten ...";
	private Stage dialogStage;
	private Lesson lesson;
	private ObservableList<String> lessons;
	private ObservableList<Teacher> teacherList;
	private ObservableList<SchoolClass> classList;
	private ObservableList<Room> roomList;
	@FXML
	private Button buttonRemove;
	@FXML
	private Label dayLabel;
	@FXML
	private Label hourLabelFrom;
	@FXML
	private Label hourLabelTo;
	@FXML
	private Label timeLabelFrom;
	@FXML
	private Label timeLabelTo;
	@FXML
	private ComboBox<String> classSplitComboBox;
	@FXML
	private ComboBox<SchoolClass> classComboBox;
	@FXML
	private ComboBox<SchoolClassGroup> classGroupComboBox;
	@FXML
	private ComboBox<Teacher> teacherComboBox;
	@FXML
	private ComboBox<LearningField> fieldComboBox;
	@FXML
	private ComboBox<Room> roomComboBox;

    //================================================================================
    // Constructors
    //================================================================================
	public ConfigurationDialogController(){
		this.lesson = null;
		this.teacherList = FXCollections.observableArrayList(MainApplication.globalMain.sharedSQLManager().selectAllTeacher());
		this.classList = FXCollections.observableArrayList(MainApplication.globalMain.sharedSQLManager().selectAllClasses());
		this.roomList = FXCollections.observableArrayList(MainApplication.globalMain.sharedSQLManager().selectAllRooms());
		//this.lessons.add(null);
	}
    @FXML
    private void initialize() {
    	this.classGroupComboBox.setDisable(true);
    	this.fieldComboBox.setDisable(true);
    	
    	this.classSplitComboBox.setItems(this.lessons);
    	this.classSplitComboBox.getSelectionModel().select(0);
    	this.onClassSplitComboBox();
    	
    	this.teacherComboBox.setItems(this.teacherList);
    	this.roomComboBox.setItems(roomList);
    	this.classComboBox.setItems(classList);
    }
    //================================================================================
    // Setters
    //================================================================================
	public void setDialogStage(Stage stage){
		this.dialogStage = stage;
	}
	private void setTimeInformation(LessonTimeInformation i){
		this.timeLabelFrom.setText(i.getTimeFrom());
		this.timeLabelTo.setText(i.getTimeTo());
		this.hourLabelFrom.setText(i.getHour()+1+"");
		this.hourLabelTo.setText(i.getHour()+1+"");
		this.dayLabel.setText(i.getDayString());
	}
	public void setLesson(Lesson l){
		this.lesson = l;
		if(!l.isEmpty()){
			this.buttonRemove.setDisable(false);
			this.teacherComboBox.getSelectionModel().select(this.lesson.getTeacher());
			this.roomComboBox.getSelectionModel().select(this.lesson.getRoom());
			this.classComboBox.getSelectionModel().select(this.lesson.getsClass());
			this.classGroupComboBox.getSelectionModel().select(this.lesson.getsClassGroup());
			this.fieldComboBox.getSelectionModel().select(this.lesson.getLearningField());
		}
		else{
			this.buttonRemove.setDisable(true);
			if(MainWindowController.lastSelectedClass != null){
				this.classComboBox.getSelectionModel().select(MainWindowController.lastSelectedClass);
			}
			else if(MainWindowController.lastSelectedTeacher != null){
				this.teacherComboBox.getSelectionModel().select(MainWindowController.lastSelectedTeacher);
			}
		}
	}
	public void setLessons(Lesson l){
		this.setTimeInformation(l.getTimeInformation());
		this.setLesson(l);
	}
    //================================================================================
    // Action Handler
    //================================================================================
	@FXML
	private void onSave(){
		Teacher t = this.teacherComboBox.getSelectionModel().getSelectedItem();
		LearningField f = this.fieldComboBox.getSelectionModel().getSelectedItem();
		SchoolClass s = this.classComboBox.getSelectionModel().getSelectedItem();
		SchoolClassGroup g = this.classGroupComboBox.getSelectionModel().getSelectedItem();
		Room r = this.roomComboBox.getSelectionModel().getSelectedItem();
		if(t == null || f == null || s == null || g == null || r == null){
	    	Dialogs.create()
	        .owner(dialogStage)
	        .title("Fehler")
	        .message("Es sind nicht alle Felder ausgefuellt!")
	        .showError();
			return;
		}
		if(this.lesson.isEmpty()){
			Lesson l = new Lesson(t, f, s, g, r, this.lesson.getTimeInformation());
			MainApplication.globalMain.sharedSQLManager().addNewLesson(l);
		}else{
			this.lesson.setLearningField(f);
			this.lesson.setRoom(r);
			this.lesson.setsClass(s);
			this.lesson.setsClassGroup(g);
			this.lesson.setTeacher(t);
			//TODO: update lesson anhand von id
		}
		MainApplication.globalMain.updateData(true);
		this.dialogStage.close();
	}
	@FXML
	private void onClassComboBox(){
		SchoolClass sc = this.classComboBox.getSelectionModel().getSelectedItem();
		this.classGroupComboBox.setItems(sc.getGroups());
		this.classGroupComboBox.setDisable(false);
		this.classGroupComboBox.getSelectionModel().select(0);
	}
	@FXML
	private void onClassGroupComboBox(){
	}
	@FXML
	private void onTeacherComboBox(){
		Teacher t = this.teacherComboBox.getSelectionModel().getSelectedItem();
		this.fieldComboBox.setItems(t.learningFieldProperty());
		this.fieldComboBox.setDisable(false);
		this.fieldComboBox.getSelectionModel().select(0);
	}
	@FXML
	private void onFieldComboBox(){
	}
	@FXML
	private void onRoomComboBox(){
	}
	@FXML
	private void onClassSplitComboBox(){
		//Lesson l = this.classSplitComboBox.getSelectionModel().getSelectedItem();
		//this.setLesson(null);
	}
	@FXML
	private void onButtonRemove(){
		this.lessons.remove(this.classSplitComboBox.getSelectionModel().getSelectedItem());
	}
}
