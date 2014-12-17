package application.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.controlsfx.dialog.Dialogs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import application.MainApplication;
import application.model.SchoolClass;
import application.model.SchoolClassGroup;
import application.model.Teacher;

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
	private ComboBox<Teacher> classTeacherComboBox;
	@FXML
	private Button removeButton;
	@FXML
	private ListView<SchoolClassGroup> groupList;
	@FXML
	private HBox groupHBox;

    //================================================================================
    // Cunstructors
    //================================================================================
	public ClassDialogController(){
		this.classList.add(new SchoolClass("Neue Klasse hinzuf¸gen ...",-1,null));
		ArrayList<SchoolClass> list = MainApplication.globalMain.sharedSQLManager().selectAllClasses();
		this.setClassList(FXCollections.observableArrayList(list));
		
	}
    @FXML
    private void initialize() {
    	this.choiceBox.setItems(this.classList);
    	this.choiceBox.getSelectionModel().select(0);
    	this.choiceBoxAction();
    	ArrayList<Teacher> list = MainApplication.globalMain.sharedSQLManager().selectAllTeacher();
		ObservableList<Teacher> oList = FXCollections.observableArrayList(list);
		this.classTeacherComboBox.setItems(oList);
		this.classTeacherComboBox.getSelectionModel().select(oList.get(0));
    }
    public void setDialogStage(Stage stage){
    	this.dialogStage = stage;
    }
    private void setClass(SchoolClass classy){
    	if(classy.getId() == -1){
    		this.nameTxtField.setText("");
    		this.removeButton.setDisable(true);
    		this.groupHBox.setDisable(true);
    		this.groupList.setItems(null);
    	}else{
    		this.nameTxtField.setText(classy.getName().get());
    		this.classTeacherComboBox.getSelectionModel().select(classy.getClassTeacher());
    		this.removeButton.setDisable(false);
    		this.groupHBox.setDisable(false);
    		this.groupList.setItems(classy.getGroups());
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
    	if(this.choiceBox.getSelectionModel().getSelectedItem().getId() == -1){
    		Teacher t = this.classTeacherComboBox.getSelectionModel().getSelectedItem();
    		SchoolClass s = new SchoolClass(this.nameTxtField.getText(),t);
    		this.classList.add(s);
    		MainApplication.globalMain.sharedSQLManager().addNewClass(s);
    	}else{
    		SchoolClass s = this.choiceBox.getSelectionModel().getSelectedItem();
    		s.setName(this.nameTxtField.getText());
    		s.setClassTeacher(this.classTeacherComboBox.getSelectionModel().getSelectedItem());
    		// TODO : update class anhand von id 
    	}
//    	Dialogs.create()
//        .owner(dialogStage)
//        .title("Information")
//        .message("ƒnderungen wurden gespeichert!")
//        .showInformation();
//    	this.choiceBoxAction();
    }
    @FXML
    private void onRemove() {
    	this.classList.remove(this.choiceBox.getSelectionModel().getSelectedItem());
    	this.choiceBoxAction();
    	//TODO: remove class anhand id from db
    }
    @FXML
    private void onNewGroup(){
    	String title = "Neue Gruppe in "+this.choiceBox.getSelectionModel().getSelectedItem().getName().get()+" erstellen ...";
    	Optional<String> response = Dialogs.create()
		      .owner(dialogStage)
		      .title(title)
		      .message( "Gruppenname: ")
		      .showTextInput();
	  if(response.get().length()>0){
		  SchoolClass sc = this.choiceBox.getSelectionModel().getSelectedItem();
		  sc.getGroups().add(new SchoolClassGroup(response.get()));
	  }
    }
    @FXML
    private void onRemoveGroup(){
    	if(this.groupList.getSelectionModel().getSelectedIndex() == 0){
    		return;
    	}
    	SchoolClassGroup g = this.groupList.getSelectionModel().getSelectedItem();
    	if(g!= null){
    		SchoolClass sc = this.choiceBox.getSelectionModel().getSelectedItem();
  		  	sc.getGroups().remove(this.groupList.getSelectionModel().getSelectedItem());
    	}
    }
    @FXML
    private void onClassTeacherComboBox(){
    	
    }
}
