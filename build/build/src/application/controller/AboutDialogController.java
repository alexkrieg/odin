package application.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AboutDialogController {
	
    //================================================================================
    // Properties
    //================================================================================
	public static final String DIALOG_STAGE_TITLE = "Über ...";
	private Stage dialogStage;
	
    //================================================================================
    // Cinstructors
    //================================================================================
	public AboutDialogController(){
		
	}
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }
	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}
    //================================================================================
    // Action Hanlder
    //================================================================================
	@FXML
	private void onClose(){
		this.dialogStage.close();
	}
}
