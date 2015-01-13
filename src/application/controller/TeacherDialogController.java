package application.controller;


import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import org.controlsfx.control.CheckListView;

import application.MainApplication;
import application.model.LearningField;
import application.model.Teacher;

public class TeacherDialogController {
	
    //================================================================================
    // Properties
    //================================================================================
	public static final String DIALOG_STAGE_TITLE = "Lehrer bearbeiten ...";
	@FXML
	private Button removeButton;
	@FXML
	private TextField firstNameTxtField;
	@FXML
	private TextField lastNameTxtField;
	@FXML
	private TextField tokenTxtField;
	@FXML
	private ComboBox<Teacher> choiceBox;
	@FXML
	private Pane listPane;
	
	private Stage dialogStage;
	private CheckListView<LearningField> checkListView;
	private ObservableList<Teacher> teacherList = FXCollections.observableArrayList();
	private ObservableList<LearningField> fieldList = FXCollections.observableArrayList();
	
    //================================================================================
    // Cunstructors
    //================================================================================
	public TeacherDialogController(){
		this.checkListView = new CheckListView<>();
		teacherList.add(new Teacher("Neuen Lehrer anlegen ...", ""));
		ArrayList<LearningField> list = MainApplication.globalMain.sharedSQLManager().selectAllLearningFields();
		this.setFields(FXCollections.observableArrayList(list));
		ArrayList<Teacher> listlist = MainApplication.globalMain.sharedSQLManager().selectAllTeacher();
		this.setTeachers(FXCollections.observableArrayList(listlist));
	}
	/**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
    	this.choiceBox.setItems(this.teacherList);
    	this.choiceBox.getSelectionModel().select(0);
    	this.choiceBoxAction();
    	this.checkListView.setMaxHeight(233);
    	this.checkListView.setMaxWidth(405);
    	this.checkListView.setMinHeight(233);
    	this.checkListView.setMinWidth(405);
    	this.listPane.getChildren().add(checkListView);
    	this.checkListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    		checkListView.getCheckModel().check(newValue);
		});
    }
    public void setDialogStage(Stage stage){
    	this.dialogStage = stage;
    }
    private void setTeacher(Teacher teacher){
    	if(teacher.getIdentifier().get().equals("empty")){
    		this.removeButton.setDisable(true);
    		this.firstNameTxtField.setText("");
        	this.lastNameTxtField.setText("");
        	this.checkListView.setDisable(true);
        	this.tokenTxtField.setText("");
        	this.tokenTxtField.setDisable(false);
    	}else{
    		this.removeButton.setDisable(false);
    		this.firstNameTxtField.setText(teacher.firstNameProperty().get());
        	this.lastNameTxtField.setText(teacher.lastNameProperty().get());
        	this.tokenTxtField.setText(teacher.getIdentifier().get());
        	this.tokenTxtField.setDisable(true);
        	this.checkListView.setDisable(false);
        	for(LearningField f: this.checkListView.getItems()){
        		for(LearningField g : teacher.learningFieldProperty()){
        			if(f.getID() == g.getID()){
        				this.checkListView.getCheckModel().check(f);
        			}
            	}
        	}
    	}
    }
    public void setTeachers(ObservableList<Teacher> list){
    	this.teacherList.addAll(list);
    }
    public void setFields(ObservableList<LearningField> list){
    	this.fieldList = list;
    	checkListView.setItems(this.fieldList);
    }
    //================================================================================
    // Action Handlers
    //================================================================================
    @FXML
    private void choiceBoxAction(){
    	this.checkListView.getCheckModel().clearChecks();
    	this.setTeacher(this.choiceBox.getSelectionModel().getSelectedItem());
    }
    @FXML
    private void handleOk() {
    	if (this.firstNameTxtField.getText().length() == 0 || this.lastNameTxtField.getText().length() == 0 || this.tokenTxtField.getText().length() == 0) {
    		return;
    	}
    	if (this.choiceBox.getSelectionModel().getSelectedItem().getIdentifier().get().equals("empty") == true) {
    		Teacher t = new Teacher(this.tokenTxtField.getText(),this.firstNameTxtField.getText(), this.lastNameTxtField.getText());
    		t.setLearningFields(FXCollections.observableArrayList(this.checkListView.getCheckModel().getCheckedItems()));
    		if(MainApplication.globalMain.sharedSQLManager().addNewTeacher(t) != true){
    			//TODO: INFO: fehler beim speichern
    			return;
    		}
    		this.teacherList.add(t);
		}
    	else {
    		Teacher t = this.choiceBox.getSelectionModel().getSelectedItem();
        	t.setFirstName(this.firstNameTxtField.getText());
        	t.setLastName(this.lastNameTxtField.getText());
        	ObservableList<LearningField> list = this.checkListView.getCheckModel().getCheckedItems();
        	t.learningFieldProperty().clear();
        	t.learningFieldProperty().addAll(list);
        	if(MainApplication.globalMain.sharedSQLManager().updateTeacher(t) != true){
    			//TODO: INFO: fehler beim speichern
    			return;
    		}
    	}
    	//TODO: INFO änderungen gespeichert
    	this.choiceBoxAction();
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    @FXML
    private void handleRemove() {
    	Teacher t = this.choiceBox.getSelectionModel().getSelectedItem();
    	if(MainApplication.globalMain.sharedSQLManager().removeTeacher(t)!= true){
        	//TODO: INfo: fehlgeschlagen
    		return;
    	}
    	this.teacherList.remove(t);
		//TODO: INFO: lehrer GELÖSCHT
    	this.choiceBoxAction();
    }
}
