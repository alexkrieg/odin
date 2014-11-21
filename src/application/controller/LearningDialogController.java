package application.controller;

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
	private MainApplication mainApplication;
	
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
		this.fieldList.add(new LearningField("Neues Lernfeld hinzuf¸gen ...","",-1));
		//TODO: get all learning fields and save the in fieldList
		
	}
    @FXML
    private void initialize() {
    	this.comboBox.setItems(this.fieldList);
		this.comboBox.getSelectionModel().select(0);
    	this.choiceBoxAction();
    }
    public void setMainApplication(MainApplication app){
    	this.mainApplication = app;
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
    		// TODO: insert new Field in database
    	}else{
    		LearningField f = this.comboBox.getSelectionModel().getSelectedItem();
    		f.setName(this.nameTxtField.getText());
    		f.setDescription(this.descriptionTxtField.getText());
    		// TODO : update db anhand von field id
    	}
    	Dialogs.create()
        .owner(dialogStage)
        .title("Information")
        .message("ƒnderungen wurden gespeichert!")
        .showInformation();
    	this.choiceBoxAction();
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    @FXML
    private void onRemove() {
    	this.fieldList.remove(this.comboBox.getSelectionModel().getSelectedItem());
    	this.choiceBoxAction();
    	//TODO: remove fieldlist anhand id from db
    }
}
