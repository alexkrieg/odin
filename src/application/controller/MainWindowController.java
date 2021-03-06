package application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import application.MainApplication;
import application.PDFCreator;
import application.factory.FormattedTableCellFactory;
import application.model.Lesson;
import application.model.SchoolClass;
import application.model.Teacher;
import application.model.TimePeriod;

public class MainWindowController {
	
	public static Teacher lastSelectedTeacher;
	public static SchoolClass lastSelectedClass;
	
	private MainApplication mainApp;
	
	@FXML
	private TableView<TimePeriod> mainTableView;
	@FXML
	private TableColumn<TimePeriod, String> timeC;
	@FXML
	private TableColumn<TimePeriod, ObservableList<Lesson>> mondayC;
	@FXML
	private TableColumn<TimePeriod, ObservableList<Lesson>> tuesdayC;
	@FXML
	private TableColumn<TimePeriod, ObservableList<Lesson>> wendsdayC;
	@FXML
	private TableColumn<TimePeriod, ObservableList<Lesson>> thursdayC;
	@FXML
	private TableColumn<TimePeriod, ObservableList<Lesson>> fridayC;
	
	@FXML
	private ListView<Teacher> teacherListView;
	@FXML
	private ListView<SchoolClass> classListView;
	
	ObservableList<TimePeriod> pList = FXCollections.observableArrayList();
	
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
		createTableView();
		// Handle ListView selection changes.
		this.teacherListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue == null){
				newValue = MainWindowController.lastSelectedTeacher;
			}
		    this.setTimes(MainApplication.globalMain.sharedSQLManager().getAllLessonByTeacher(newValue));
		    MainWindowController.lastSelectedTeacher = newValue;
		    MainWindowController.lastSelectedClass = null;
		});
		this.classListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue == null){
				newValue = MainWindowController.lastSelectedClass;
			}
		    this.setTimes(MainApplication.globalMain.sharedSQLManager().getAllLessonByClass(newValue));
		    MainWindowController.lastSelectedClass = newValue;
		    MainWindowController.lastSelectedTeacher = null;
		});


		reloadData(false);
	}
	public void reloadData(boolean updateTableView){
		ObservableList<Teacher> teachers = FXCollections.observableArrayList(MainApplication.globalMain.sharedSQLManager().selectAllTeacher(null));
		ObservableList<SchoolClass> classes = FXCollections.observableArrayList(MainApplication.globalMain.sharedSQLManager().selectAllClasses(null));
		this.setClasses(classes);
		this.setTeachers(teachers);
		if(updateTableView == true){
			if(MainWindowController.lastSelectedClass != null){
				this.setTimes(MainApplication.globalMain.sharedSQLManager().getAllLessonByClass(MainWindowController.lastSelectedClass));
			}
			else if(MainWindowController.lastSelectedTeacher != null){
				this.setTimes(MainApplication.globalMain.sharedSQLManager().getAllLessonByTeacher(MainWindowController.lastSelectedTeacher));
			}
		}
	}
	private void createTableView(){
		this.mainTableView.getSelectionModel().setCellSelectionEnabled(true);
		this.mainTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		this.mainTableView.setEditable(false);
		timeC.setCellValueFactory(cellData -> cellData.getValue().getPrettyTime());
		mondayC.setCellValueFactory(cellData ->cellData.getValue().getLessonsForDay(1));
		tuesdayC.setCellValueFactory(cellData -> cellData.getValue().getLessonsForDay(2));
		wendsdayC.setCellValueFactory(cellData -> cellData.getValue().getLessonsForDay(3));
		thursdayC.setCellValueFactory(cellData -> cellData.getValue().getLessonsForDay(4));
		fridayC.setCellValueFactory(cellData -> cellData.getValue().getLessonsForDay(5));
		
		FormattedTableCellFactory<TimePeriod, ObservableList<Lesson>>  cellFactory = new FormattedTableCellFactory<TimePeriod,ObservableList<Lesson>>();
		mondayC.setCellFactory(cellFactory);
		tuesdayC.setCellFactory(cellFactory);
		wendsdayC.setCellFactory(cellFactory);
		thursdayC.setCellFactory(cellFactory);
		fridayC.setCellFactory(cellFactory);
		
		this.mainTableView.setItems(pList);
	}
    //================================================================================
    // Private Setter
    //================================================================================
	private void setTeachers(ObservableList<Teacher> list){
		this.teacherListView.setItems(list);
	}
	private void setClasses(ObservableList<SchoolClass> list){
		this.classListView.setItems(list);
	}
	private void setTimes(Lesson[][][] lList) {
		this.pList.clear();
		for(int i = 0;i<10;i++){
			TimePeriod p = new TimePeriod(i);
			for (int j = 0; j < 5 ; j++) {
				p.addLessonsAtIndex(lList[i][j], j);
			}
			pList.add(p);
		}
		createTableView();
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
	private void menuOnSchoolClassGroup(){
		this.mainApp.showSchoolClassGroupDialog();
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
	private void createPDF(){
		try{
			PDFCreator pdf = new PDFCreator();
		}catch(Exception e){
			
		}
	}
}
