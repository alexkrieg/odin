package application.controller;

import application.MainApplication;
import application.model.LearningField;
import application.model.Room;
import application.model.SchoolClass;
import application.model.SchoolClassGroup;
import application.model.Teacher;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
	private MainApplication dataHandler;
	
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
		
	}
    @FXML
    private void initialize() {
    	this.classGroupComboBox.setDisable(true);
    	this.fieldComboBox.setDisable(true);
    }
    //================================================================================
    // Setters
    //================================================================================
	public void setDialogStage(Stage stage){
		this.dialogStage = stage;
	}
	public void setDatahandler(MainApplication dataHandler){
		this.dataHandler = dataHandler;
	}
	public void setTimeInformation(String hFrom, String hTo,String tFrom, String tTo, String day){
		this.timeLabelFrom.setText(tFrom);
		this.timeLabelTo.setText(tTo);
		this.hourLabelFrom.setText(hFrom);
		this.hourLabelTo.setText(hTo);
		this.dayLabel.setText(day);
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
		MainApplication.log("SAVE");
	}
	@FXML
	private void onClassComboBox(){
		MainApplication.log("onClass");
		SchoolClass sc = this.classComboBox.getSelectionModel().getSelectedItem();
		this.classGroupComboBox.setItems(sc.getGroups());
		this.classGroupComboBox.setDisable(false);
	}
	@FXML
	private void onClassGroupComboBox(){
		MainApplication.log("onGroup");
	}
	@FXML
	private void onTeacherComboBox(){
		MainApplication.log("onTeacher");
		Teacher t = this.teacherComboBox.getSelectionModel().getSelectedItem();
		this.fieldComboBox.setItems(t.learningFieldProperty());
		this.fieldComboBox.setDisable(false);
	}
	@FXML
	private void onFieldComboBox(){
		MainApplication.log("onField");
	}
	@FXML
	private void onRoomComboBox(){
		MainApplication.log("onRoom");
	}
}
