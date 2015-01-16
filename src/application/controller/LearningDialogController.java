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

public class LearningDialogController {
    //================================================================================
    // Properties
    //================================================================================
	public static final String DIALOG_STAGE_TITLE = "Lernfeld bearbeiten ...";
	private Stage dialogStage;
	@FXML
	private Button removeButton;
	@FXML
	private TextField nameTxtField;
	@FXML
	private TextArea descriptionTxtField;
	@FXML
	private ComboBox<LearningField> comboBox;
	
	private ObservableList<LearningField> fieldList = FXCollections.observableArrayList();
    //================================================================================
    // Cunstructors
    //================================================================================
	public LearningDialogController(){
		this.fieldList.add(new LearningField("Neues Lernfeld hinzufuegen ...","",-1));
		ArrayList<LearningField> list = MainApplication.globalMain.sharedSQLManager().selectAllLearningFields();
		this.setLearningFields(FXCollections.observableArrayList(list));
	}
    @FXML
    private void initialize() {
    	this.comboBox.setItems(this.fieldList);
		this.comboBox.getSelectionModel().select(0);
    	this.choiceBoxAction();
    }
    public void setDialogStage(Stage stage){
    	this.dialogStage = stage;
    }
    private void setField(LearningField field){
    	if(field.getID() == -1){
    		this.removeButton.setDisable(true);
    		this.nameTxtField.setText("");
        	this.descriptionTxtField.setText("");
    	}else{
    		this.nameTxtField.setText(field.getNameProperty().get());
    		this.descriptionTxtField.setText(field.getDescriptionProperty().get());
    		this.removeButton.setDisable(false);
    	}
    }
    public void setLearningFields(ObservableList<LearningField> fields){
    	this.fieldList.addAll(fields);
    }
    //================================================================================
    // Action Handlers
    //================================================================================
    @FXML
    private void choiceBoxAction(){
    	this.setField(this.comboBox.getSelectionModel().getSelectedItem());
    }
    @FXML
    private void handleOK() {
    	if(this.nameTxtField.getText().length()== 0 || this.descriptionTxtField.getText().length() == 0){
    		return;
    	}
    	if(this.comboBox.getSelectionModel().getSelectedItem().getID() == -1){
    		LearningField f = new LearningField(this.nameTxtField.getText(), this.descriptionTxtField.getText(), 0);
    		this.fieldList.add(f);
    		MainApplication.globalMain.sharedSQLManager().addNewLearningField(f);
    	}else{
    		LearningField f = this.comboBox.getSelectionModel().getSelectedItem();
    		f.setName(this.nameTxtField.getText());
    		f.setDescription(this.descriptionTxtField.getText());
    		if(MainApplication.globalMain.sharedSQLManager().updateLearningField(f) != true){
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
    private void handleCancel() {
        dialogStage.close();
    }
    @FXML
    private void onRemove() {
    	LearningField f = this.comboBox.getSelectionModel().getSelectedItem();
    	
    	if(MainApplication.globalMain.sharedSQLManager().removeLearningField(f) != true){
        	Dialogs.create()
            .owner(this.dialogStage)
            .title("Information")
            .masthead(null)
            .message("Fehler beim Speichern der Daten.")
            .showError();
    		return;
    	}
    	this.fieldList.remove(f);
    	this.choiceBoxAction();
    	Dialogs.create()
        .owner(this.dialogStage)
        .title("Information")
        .masthead(null)
        .message("Daten gespeichert.")
        .showInformation();
    }
}
