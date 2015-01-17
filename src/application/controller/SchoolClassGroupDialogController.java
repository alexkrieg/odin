package application.controller;

import java.util.ArrayList;

import org.controlsfx.dialog.Dialogs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import application.MainApplication;
import application.model.LearningField;
import application.model.SchoolClassGroup;

public class SchoolClassGroupDialogController {
    //================================================================================
    // Properties
    //================================================================================
	public static final String DIALOG_STAGE_TITLE = "Gruppen bearbeiten ...";
	private Stage dialogStage;
	@FXML
	private Button removeButton;
	@FXML
	private TextField nameTxtField;
	@FXML
	private ComboBox<SchoolClassGroup> comboBox;
	
	private ObservableList<SchoolClassGroup> groupList = FXCollections.observableArrayList();
    //================================================================================
    // Cunstructors
    //================================================================================
	public SchoolClassGroupDialogController(){
		this.groupList.add(new SchoolClassGroup("Neue Gruppe hinzufuegen",-3));
		ArrayList<SchoolClassGroup> list = MainApplication.globalMain.sharedSQLManager().selectAllSchoolClassGroup();
		this.setSchoolClassGroup(FXCollections.observableArrayList(list));
	}
    @FXML
    private void initialize() {
    	this.comboBox.setItems(this.groupList);
		this.comboBox.getSelectionModel().select(0);
    	this.choiceBoxAction();
    }
    public void setDialogStage(Stage stage){
    	this.dialogStage = stage;
    }
    private void setGroup(SchoolClassGroup group){
    	if(group.getId() == -3){
    		this.removeButton.setDisable(true);
    		this.nameTxtField.setText("");
    	}
    	else{
    		this.nameTxtField.setText(group.getName().get());
    		if(group.getId() == -1){
    			this.removeButton.setDisable(true);
    			this.nameTxtField.setDisable(true);
    		}else{
    			this.removeButton.setDisable(false);
    			this.nameTxtField.setDisable(false);
    		}
    		
    	}
    }
    public void setSchoolClassGroup(ObservableList<SchoolClassGroup> groups){
    	this.groupList.addAll(groups);
    }
    //================================================================================
    // Action Handlers
    //================================================================================
    @FXML
    private void choiceBoxAction(){
    	this.setGroup(this.comboBox.getSelectionModel().getSelectedItem());
    }
    @FXML
    private void handleOK() {
    	if(this.nameTxtField.getText().length()== 0){
    		return;
    	}
    	if(this.comboBox.getSelectionModel().getSelectedItem().getId() == -3){
    		SchoolClassGroup g = new SchoolClassGroup(this.nameTxtField.getText());
    		if(MainApplication.globalMain.sharedSQLManager().addNewSchoolClassGroup(g)){
    			this.groupList.add(g);
    		}
    		
    	}else{
    		SchoolClassGroup g = this.comboBox.getSelectionModel().getSelectedItem();
    		g.setName(this.nameTxtField.getText());
    		if(MainApplication.globalMain.sharedSQLManager().updateSchoolClassGroup(g) != true){
            	Dialogs.create()
                .owner(this.dialogStage)
                .title("Information")
                .masthead(null)
                .message("Fehler beim Speichern der Daten.")
                .showError();
    			return;
    		}
    	}
    	Dialogs.create()
        .owner(this.dialogStage)
        .title("Information")
        .masthead(null)
        .message("Daten gespeichert.")
        .showInformation();
    	this.choiceBoxAction();
    }
    @FXML
    private void onRemove() {
    	SchoolClassGroup f = this.comboBox.getSelectionModel().getSelectedItem();
    	if(MainApplication.globalMain.sharedSQLManager().removeSchoolClassGroup(f) != true){
        	Dialogs.create()
            .owner(this.dialogStage)
            .title("Information")
            .masthead(null)
            .message("Fehler beim Löschen der Gruppe.")
            .showError();
    		return;
    	}
    	this.groupList.remove(f);
    	this.choiceBoxAction();
    	Dialogs.create()
        .owner(this.dialogStage)
        .title("Information")
        .masthead(null)
        .message("Gruppe gelöscht.")
        .showInformation();
    }
}
