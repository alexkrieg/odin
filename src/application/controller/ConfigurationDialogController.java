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
import application.factory.FormattedClassComboCellFactory;
import application.factory.FormattedRoomComboCellFactory;
import application.factory.FormattedTeacherComboCellFactory;
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
	private ObservableList<Lesson> lessons;
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
	private ComboBox<Lesson> classSplitComboBox;
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
		this.lessons = FXCollections.observableArrayList();
	}
    @FXML
    private void initialize() {
    	this.classGroupComboBox.setDisable(true);
    	this.fieldComboBox.setDisable(true);
    	FormattedClassComboCellFactory<SchoolClassGroup> classFactory= new FormattedClassComboCellFactory<SchoolClassGroup>();
    	this.classGroupComboBox.setCellFactory(classFactory);
    	this.classSplitComboBox.setItems(this.lessons);
    	

    	this.classComboBox.setItems(classList);
    }
	public void setLessons(ObservableList<Lesson> list){
		this.setTimeInformation(list.get(0).getTimeInformation());
		this.roomList = FXCollections.observableArrayList(MainApplication.globalMain.sharedSQLManager().selectAllRooms(list.get(0).getTimeInformation()));
    	FormattedRoomComboCellFactory<Room> roomFactory = new FormattedRoomComboCellFactory<Room>();
    	this.roomComboBox.setCellFactory(roomFactory);
    	this.roomComboBox.setItems(roomList);
		
    	this.teacherList = FXCollections.observableArrayList(MainApplication.globalMain.sharedSQLManager().selectAllTeacher(list.get(0).getTimeInformation()));
    	FormattedTeacherComboCellFactory<Teacher> teacherFactory= new FormattedTeacherComboCellFactory<Teacher>();
    	this.teacherComboBox.setCellFactory(teacherFactory);
    	this.teacherComboBox.setItems(this.teacherList);
    	
    	this.classList = FXCollections.observableArrayList(MainApplication.globalMain.sharedSQLManager().selectAllClasses(list.get(0).getTimeInformation()));
    	this.classComboBox.setItems(this.classList);
		this.lessons.addAll(list);
		if(lessons.get(0).isEmpty()){
			this.lessons.get(0).setId(-1);
			this.classSplitComboBox.getSelectionModel().select(0);
		}else{
			this.lessons.add(0, new Lesson(-1,list.get(0).getTimeInformation()));
			this.classSplitComboBox.getSelectionModel().select(1);
		}
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
		this.dayLabel.setText(i.getDayString());
	}
	public void setLesson(Lesson l){
		this.lesson = l;
		if(l.isEmpty()){
			this.buttonRemove.setDisable(true);
			this.roomComboBox.getSelectionModel().clearSelection();
			this.classComboBox.getSelectionModel().clearSelection();
			this.classGroupComboBox.getSelectionModel().clearSelection();
			this.fieldComboBox.getSelectionModel().clearSelection();
			this.teacherComboBox.getSelectionModel().clearSelection();
			if(MainWindowController.lastSelectedClass != null){
				this.classComboBox.getSelectionModel().select(MainWindowController.lastSelectedClass);
			}
			else if(MainWindowController.lastSelectedTeacher != null){
				this.teacherComboBox.getSelectionModel().select(MainWindowController.lastSelectedTeacher);
			}
		}
		else{
			this.buttonRemove.setDisable(false);
			this.teacherComboBox.getSelectionModel().select(this.lesson.getTeacher());
			this.roomComboBox.getSelectionModel().select(this.lesson.getRoom());
			this.classComboBox.getSelectionModel().select(this.lesson.getsClass());
			this.classGroupComboBox.getSelectionModel().select(this.lesson.getsClassGroup());
			this.fieldComboBox.getSelectionModel().select(this.lesson.getLearningField());
		}
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
			if(MainApplication.globalMain.sharedSQLManager().updateLesson(this.lesson)){
				
			}else{
				Dialogs.create()
		        .owner(dialogStage)
		        .title("Fehler")
		        .message("Fehler beim Update der Daten.")
		        .showError();
			}
		}
		MainApplication.globalMain.updateData(true);
		this.dialogStage.close();
	}
	@FXML
	private void onClassComboBox(){
		SchoolClass sc = this.classComboBox.getSelectionModel().getSelectedItem();
		if(sc == null){
			return;
		}
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
		Lesson l = this.classSplitComboBox.getSelectionModel().getSelectedItem();
		this.setLesson(l);
	}
	@FXML
	private void onButtonRemove(){
		if(MainApplication.globalMain.sharedSQLManager().removeLesson(this.lesson.getId())){
			this.lessons.remove(this.classSplitComboBox.getSelectionModel().getSelectedItem());
		}else{
			Dialogs.create()
	        .owner(dialogStage)
	        .title("Fehler")
	        .message("Fehler beim Löschen der Daten.")
	        .showError();
		}
		MainApplication.globalMain.updateData(true);
		this.dialogStage.close();
	}
}
