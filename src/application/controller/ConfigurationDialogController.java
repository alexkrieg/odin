package application.controller;

import org.controlsfx.dialog.Dialogs;

import application.MainApplication;
import application.model.LearningField;
import application.model.Lesson;
import application.model.LessonTimeInformation;
import application.model.Room;
import application.model.SchoolClass;
import application.model.SchoolClassGroup;
import application.model.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConfigurationDialogController {
    //================================================================================
    // Properties
    //================================================================================
	public static final String DIALOG_STAGE_TITLE = "Stunde bearbeiten ...";
	private Stage dialogStage;
	private MainApplication mainApplication;
	private Lesson lesson;
	private LessonTimeInformation timeInformation;
	private ObservableList<String> lessons = FXCollections.observableArrayList();
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
		this.lessons.add(null);
	}
    @FXML
    private void initialize() {
    	this.classGroupComboBox.setDisable(true);
    	this.fieldComboBox.setDisable(true);
    	this.classSplitComboBox.setItems(this.lessons);
    	this.classSplitComboBox.getSelectionModel().select(0);
    	this.onClassSplitComboBox();
    }
    //================================================================================
    // Setters
    //================================================================================
	public void setDialogStage(Stage stage){
		this.dialogStage = stage;
	}
	public void setMainApplication(MainApplication app){
		this.mainApplication = app;
	}
	public void setTimeInformation(LessonTimeInformation i){
		this.timeInformation = i;
		this.timeLabelFrom.setText(this.timeInformation.getTimeFrom());
		this.timeLabelTo.setText(this.timeInformation.getTimeTo());
		this.hourLabelFrom.setText(this.timeInformation.getHourFrom());
		this.hourLabelTo.setText(this.timeInformation.getHourTo());
		this.dayLabel.setText(this.timeInformation.getDay());
	}
	public void setLesson(Lesson l){
		MainApplication.log("Set lesson: "+l);
		if(l!= null){
			this.buttonRemove.setDisable(false);
			this.lesson = l;
			this.teacherComboBox.getSelectionModel().select(this.lesson.getTeacher());
			this.roomComboBox.getSelectionModel().select(this.lesson.getRoom());
			this.classComboBox.getSelectionModel().select(this.lesson.getsClass());
			this.classGroupComboBox.getSelectionModel().select(this.lesson.getsClassGroup());
			this.fieldComboBox.getSelectionModel().select(this.lesson.getLearningField());
		}
		else{
			this.buttonRemove.setDisable(true);
			this.lesson = null;
		}
	}
	public void setLessons(ObservableList<String> list){
		this.lessons.addAll(list);
	}
	public void setTeachers(ObservableList<Teacher> list){
		this.teacherComboBox.setItems(list);
	}
	public void setRooms(ObservableList<Room> list){
		this.roomComboBox.setItems(list);
	}
	public void setClasses(ObservableList<SchoolClass> list){
		this.classComboBox.setItems(list);
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
		String day = this.dayLabel.getText();
		String hour = ""+this.hourLabelFrom.getText()+"-"+this.hourLabelTo.getText();
		if(t == null || f == null || s == null || g == null || r == null){
	    	Dialogs.create()
	        .owner(dialogStage)
	        .title("Fehler")
	        .message("Es sind nicht alle Felder ausgef¸llt! Bitte w‰hlen sie in jeder Kategorie ein dings!")
	        .showError();
			return;
		}
		if(this.lesson != null){
			this.lesson.setLearningField(f);
			this.lesson.setRoom(r);
			this.lesson.setsClass(s);
			this.lesson.setsClassGroup(g);
			//TODO: update lesson anhand von id
		}else{
			Lesson l = new Lesson(t, f, s, g, r, timeInformation);
			//TODO: insert lesson in mapping table
		}
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
		this.setLesson(null);
	}
	@FXML
	private void onButtonRemove(){
		this.lessons.remove(this.classSplitComboBox.getSelectionModel().getSelectedItem());
	}
}
