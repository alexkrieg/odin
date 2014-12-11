package application.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.security.auth.callback.Callback;

import application.FormattedTableCellFactory;
import application.MainApplication;
import application.model.Lesson;
import application.model.LessonTimeInformation;
import application.model.SchoolClass;
import application.model.Teacher;
import application.model.TimePeriod;

public class MainWindowController {
	
	private MainApplication mainApp;
	
	@FXML
	private TableView<TimePeriod> mainTableView;
	@FXML
	private TableColumn<TimePeriod, String> timeC;
	@FXML
	private TableColumn<TimePeriod, Lesson> mondayC;
	@FXML
	private TableColumn<TimePeriod, Lesson> tuesdayC;
	@FXML
	private TableColumn<TimePeriod, Lesson> wendsdayC;
	@FXML
	private TableColumn<TimePeriod, Lesson> thursdayC;
	@FXML
	private TableColumn<TimePeriod, Lesson> fridayC;
	
	@FXML
	private ListView<Teacher> teacherListView;
	@FXML
	private ListView<SchoolClass> classListView;
	
    //================================================================================
    // Cunstructors
    //================================================================================
	public MainWindowController(){
	}
	public void setMainApp(MainApplication mainApp){
		this.mainApp=mainApp;
	}
	@FXML
    private void initialize() {
		this.mainTableView.getSelectionModel().setCellSelectionEnabled(true);
		this.mainTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		this.mainTableView.setEditable(false);
		// Handle ListView selection changes.
		this.teacherListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		    System.out.println("TeacherListView Selection Changed (selected: " + newValue.toString() + ")");
		});
		this.classListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		    System.out.println("ClassListView Selection Changed (selected: " + newValue.toString() + ")");
		});

		timeC.setCellValueFactory(cellData -> cellData.getValue().getTimeInformation());
		mondayC.setCellValueFactory(cellData ->cellData.getValue().getLessonForDayAndTime("Montag"));
		tuesdayC.setCellValueFactory(cellData -> cellData.getValue().getLessonForDayAndTime("Dienstag"));
		wendsdayC.setCellValueFactory(cellData -> cellData.getValue().getLessonForDayAndTime("Mittwoch"));
		thursdayC.setCellValueFactory(cellData -> cellData.getValue().getLessonForDayAndTime("Donnerstag"));
		fridayC.setCellValueFactory(cellData -> cellData.getValue().getLessonForDayAndTime("Freitag"));
		
		FormattedTableCellFactory<TimePeriod, Lesson>  cellFactory = new FormattedTableCellFactory<TimePeriod,Lesson>();
		mondayC.setCellFactory(cellFactory);
	}
	public void reloadData(){
    	// TODO: get all teachers here 
    	// TODO: get all classes here
		// TODO: this.mainApplication.sharedSQLManager().getData;
	}
    //================================================================================
    // Setter
    //================================================================================
	public void setTeachers(ObservableList<Teacher> list){
		this.teacherListView.setItems(list);
	}
	public void setClasses(ObservableList<SchoolClass> list){
		this.classListView.setItems(list);
	}
	public void setTimes(ObservableList<TimePeriod> list) {
		this.mainTableView.setItems(list);
	}
    //================================================================================
    // Action Handlers
    //================================================================================
	@FXML
	private void menuOnNewTeacher(){
		this.mainApp.showTeacherDialog();
	}
	@FXML
	private void menuOnLearningField(){
		this.mainApp.showLearningFieldDialog();
	}
	@FXML
	private void menuOnAbout(){
		this.mainApp.showAboutDialog();
	}
	@FXML
	private void menuOnClose(){
		this.mainApp.close();
	}
	@FXML
	private void menuOnClass(){
		this.mainApp.showClassDialog();
	}
	@FXML
	private void menuOnRoom(){
		this.mainApp.showRoomDialog();
	}
	@FXML
	private void open(){
		// time information from cell
		LessonTimeInformation i = new LessonTimeInformation("Dienstag", "1", "2", "14:00", "23:00");
		// if null 
		Lesson l = null;
		this.mainApp.showConfigurationDialog(l,i);	
		// !null 
		//Lesson l = null;// replace with lesson from cell 
		//this.mainApp.showConfigurationDialog(l,i);
	}
}
