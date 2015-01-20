package application.controller;

import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import org.controlsfx.control.CheckListView;
import org.controlsfx.dialog.Dialogs;

import application.MainApplication;
import application.model.LearningField;
import application.model.SchoolClass;
import application.model.SchoolClassGroup;
import application.model.Teacher;

public class ClassDialogController {
    //================================================================================
    // Properties
    //================================================================================
	public static final String DIALOG_STAGE_TITLE = "Klasse bearbeiten ...";
	private Stage dialogStage;
	private ObservableList<SchoolClass> classList;
	private ObservableList<SchoolClassGroup> groupList;
	@FXML 
	private ComboBox<SchoolClass> choiceBox;
	@FXML
	private TextField nameTxtField;
	@FXML
	private ComboBox<Teacher> classTeacherComboBox;
	@FXML
	private Button removeButton;
	@FXML
	private Pane listPane;
	@FXML
	private HBox groupHBox;
	
	private CheckListView<SchoolClassGroup> checkListView;
    //================================================================================
    // Cunstructors
    //================================================================================
	public ClassDialogController(){
		this.checkListView = new CheckListView<>();
		this.classList = FXCollections.observableArrayList();
	}
    @FXML
    private void initialize() {
    	this.choiceBox.setItems(this.classList);
    	this.choiceBox.getSelectionModel().select(0);
    	this.choiceBoxAction();
    	this.checkListView.setMaxHeight(135);
    	this.checkListView.setMaxWidth(421);
    	this.checkListView.setMinHeight(135);
    	this.checkListView.setMinWidth(421);
    	this.listPane.getChildren().add(checkListView);
    	
    	ArrayList<Teacher> list = MainApplication.globalMain.sharedSQLManager().selectAllTeacher(null);
		this.classTeacherComboBox.setItems(FXCollections.observableArrayList(list));
		this.classTeacherComboBox.getSelectionModel().select(0);
		
		ArrayList<SchoolClassGroup> glist = MainApplication.globalMain.sharedSQLManager().selectAllSchoolClassGroup();
		this.groupList = FXCollections.observableArrayList(glist);
		this.groupList.remove(0);
		this.checkListView.setItems(this.groupList);
		
		updateClassList();
    }
    public void setDialogStage(Stage stage){
    	this.dialogStage = stage;
    }
    private void setClass(SchoolClass classy){
    	if(classy.getId() == -1){
    		this.nameTxtField.setText("");
    		this.removeButton.setDisable(true);
    		//this.groupHBox.setDisable(true);
    	}else{
    		this.nameTxtField.setText(classy.getName().get());
    		this.classTeacherComboBox.getSelectionModel().select(classy.getClassTeacher());
    		this.removeButton.setDisable(false);
    		//this.groupHBox.setDisable(false);
        	for(SchoolClassGroup g: this.checkListView.getItems()){
        		for(SchoolClassGroup d : classy.getGroups()){
        			if(g.getId() == d.getId()){
        				this.checkListView.getCheckModel().check(g);
        			}
            	}
        	}
    	}
    }
    public void updateClassList(){
    	this.classList.clear();
    	this.classList.add(new SchoolClass("Neue Klasse hinzufuegen ...",-1,null));
    	ArrayList<SchoolClass> list = MainApplication.globalMain.sharedSQLManager().selectAllClasses(null);
    	this.classList.addAll(list);
    	MainApplication.globalMain.updateData(false);
    	this.choiceBox.getSelectionModel().select(0);
    }
    //================================================================================
    // Action Handler
    //================================================================================
    @FXML
    private void choiceBoxAction(){
    	this.checkListView.getCheckModel().clearChecks();
    	if(this.choiceBox.getSelectionModel().getSelectedItem()!= null){
    		this.setClass(this.choiceBox.getSelectionModel().getSelectedItem());
    	}	
    }
    @FXML
    private void handleOK() {
    	boolean checker = false;
    	if(this.choiceBox.getSelectionModel().getSelectedItem().getId() == -1){
    		Teacher t = this.classTeacherComboBox.getSelectionModel().getSelectedItem();
    		SchoolClass s = new SchoolClass(this.nameTxtField.getText(),t);
    		ObservableList<SchoolClassGroup> list = this.checkListView.getCheckModel().getCheckedItems();
        	s.getGroups().addAll(list);
    		if(MainApplication.globalMain.sharedSQLManager().addNewClass(s)){
    			checker = true;
    		}
    	}else{
    		SchoolClass s = this.choiceBox.getSelectionModel().getSelectedItem();
    		s.setName(this.nameTxtField.getText());
    		s.setClassTeacher(this.classTeacherComboBox.getSelectionModel().getSelectedItem());
    		ObservableList<SchoolClassGroup> list = this.checkListView.getCheckModel().getCheckedItems();
        	s.removeAllGroups();
        	s.getGroups().addAll(list);
    		if(MainApplication.globalMain.sharedSQLManager().updateClass(s)){
    			checker = true;
    		}
    	}
    	if(checker == true){
    		this.updateClassList();
    		Dialogs.create()
            .owner(this.dialogStage)
            .title("Information")
            .masthead(null)
            .message("Daten gespeichert!")
            .showInformation();
    	}else{
    		Dialogs.create()
            .owner(this.dialogStage)
            .title("Information")
            .masthead(null)
            .message("Fehler beim Speichern der Daten.")
            .showError();
    	}
    }
    @FXML
    private void onRemove() {
    	SchoolClass sc = this.choiceBox.getSelectionModel().getSelectedItem();
    	if(MainApplication.globalMain.sharedSQLManager().removeClass(sc)){
    		this.updateClassList();
    		
    	}else{
    		
    	}
    	this.choiceBoxAction();
    }
    @FXML
    private void onClassTeacherComboBox(){
    	
    }
}
