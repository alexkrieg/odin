package application.controller;

import javafx.fxml.FXML;
import application.MainApplication;

public class MainWindowController {
	
	private MainApplication mainApp;
	
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
		this.mainApp.showConfigurationDialog("1","2","14:00","23:00","Dienstag");
	}
}
