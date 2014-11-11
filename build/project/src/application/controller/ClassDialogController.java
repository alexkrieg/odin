package application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import application.MainApplication;
import application.model.SchoolClass;

public class ClassDialogController {
    //================================================================================
    // Properties
    //================================================================================
	public static final String DIALOG_STAGE_TITLE = "Klasse bearbeiten ...";
	private Stage dialogStage;
	private ObservableList<SchoolClass> classList = FXCollections.observableArrayList();
	private MainApplication dataHandler;
	@FXML 
	private ComboBox<SchoolClass> choiceBox;
	@FXML
	private TextField nameTxtField;
	@FXML
	private TextField descriptionTxtField;
	@FXML
	private Button removeButton;
	
    //================================================================================
    // Cunstructors
    //================================================================================
	public ClassDialogController(){
		this.classList.add(null);
	}
    @FXML
    private void initialize() {
    	this.choiceBox.setItems(this.classList);
    }
    public void setDialogStage(Stage stage){
    	this.dialogStage = stage;
    }
    private void setClass(SchoolClass classy){
    	if(classy == null){
    		this.nameTxtField.setText("");
    		this.descriptionTxtField.setText("");
    		this.removeButton.setDisable(true);
    	}else{
    		this.nameTxtField.setText(classy.getName().get());
    		this.descriptionTxtField.setText(classy.getDescription().get());
    		this.removeButton.setDisable(false);
    	}
    }
    public void setClassList(ObservableList<SchoolClass> list){
    	this.classList.addAll(list);
    }
    public void setDataHandler(MainApplication dataHandler){
    	this.dataHandler = dataHandler;
    }
    //================================================================================
    // Action Handler
    //================================================================================
    @FXML
    private void choiceBoxAction(){
    	this.setClass(this.choiceBox.getSelectionModel().getSelectedItem());
    }
    @FXML
    private void handleOK() {
    	if(this.choiceBox.getSelectionModel().getSelectedItem() == null){
    		SchoolClass s = new SchoolClass(this.nameTxtField.getText());
    		s.setDescription(this.descriptionTxtField.getText());
    		this.classList.add(s);
    		// TODO : insert new class in database
    	}else{
    		SchoolClass s = this.choiceBox.getSelectionModel().getSelectedItem();
    		s.setDescription(this.descriptionTxtField.getText());
    		s.setName(this.nameTxtField.getText());
    		// TODO : update class anhand von id 
    	}
    	this.choiceBoxAction();
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    @FXML
    private void onRemove() {
    	this.classList.remove(this.choiceBox.getSelectionModel().getSelectedItem());
    	this.choiceBoxAction();
    	//TODO: remove class anhand id from db
    }
}
