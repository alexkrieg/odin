package application.controller;

import application.MainApplication;
import application.model.SchoolClass;
import javafx.fxml.FXML;
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
	private Label hourLabel;
	@FXML
	private Label timeLabel;
	
	private SchoolClass currentClass;

    //================================================================================
    // Constructors
    //================================================================================
	public ConfigurationDialogController(){
		
	}
    @FXML
    private void initialize() {
    }
	public void setDialogStage(Stage stage){
		this.dialogStage = stage;
	}
	public void setDatahandler(MainApplication dataHandler){
		this.dataHandler = dataHandler;
	}
	public void setCurrentClass(SchoolClass currentClass) {
		this.currentClass = currentClass;
	}
    //================================================================================
    // Action Handler
    //================================================================================
	@FXML
	private void onSave(){
		MainApplication.log("SAVE");
	}
}
