package application.controller;


import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
	private ComboBox<Teacher> choiceBox;
	@FXML
	private Pane listPane;
	
	private MainApplication dataHandler;
	private Stage dialogStage;
	private CheckListView<LearningField> checkListView;
	private ObservableList<Teacher> teacherList = FXCollections.observableArrayList();
	private ObservableList<LearningField> fieldList = FXCollections.observableArrayList();
	
    //================================================================================
    // Cunstructors
    //================================================================================
	public TeacherDialogController(){
		this.checkListView = new CheckListView<>();
		teacherList.add(null);
    	// TODO: get all teachers here and save them in teacher list
    	// TODO: get all learning fields here and save them in field list
	}
	/**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
    	this.choiceBox.setItems(this.teacherList);
    	this.choiceBoxAction();
    	this.checkListView.setMaxHeight(180);
    	this.checkListView.setMaxWidth(405);
    	this.checkListView.setMinHeight(180);
    	this.checkListView.setMinWidth(405);
    	this.listPane.getChildren().add(checkListView);
    	checkListView.getCheckModel().getCheckedItems().addListener(new ListChangeListener<LearningField>() {
            public void onChanged(ListChangeListener.Change<? extends LearningField> c) {
                System.out.println(checkListView.getCheckModel().getCheckedItems());
            }
        });
    }
    public void setDialogStage(Stage stage){
    	this.dialogStage = stage;
    }
	public void setDataHandler(MainApplication handler){
		this.dataHandler=handler;
	}
    private void setTeacher(Teacher teacher){
    	if(teacher == null){
    		this.removeButton.setDisable(true);
    		this.firstNameTxtField.setText("");
        	this.lastNameTxtField.setText("");
        	this.checkListView.setDisable(true);
    	}else{
    		MainApplication.log("Alloo");
    		this.removeButton.setDisable(false);
    		this.firstNameTxtField.setText(teacher.firstNameProperty().get());
        	this.lastNameTxtField.setText(teacher.lastNameProperty().get());
        	this.checkListView.setDisable(false);
        	MainApplication.log("Fields:"+teacher.learningFieldProperty());
        	for(LearningField f : teacher.learningFieldProperty()){
        		MainApplication.log("TOCheck:"+f);
        		this.checkListView.getCheckModel().check(f);
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
    	if (this.firstNameTxtField.getText().length() == 0 || this.lastNameTxtField.getText().length() == 0 ) {
    		return;
    	}
    	if (this.choiceBox.getSelectionModel().getSelectedItem() == null) {
    		Teacher t = new Teacher(this.firstNameTxtField.getText(), this.lastNameTxtField.getText());
			this.teacherList.add(t);
			// TODO: insert new Teacher
		}
    	else {
    		Teacher t = this.choiceBox.getSelectionModel().getSelectedItem();
        	t.setFirstName(this.firstNameTxtField.getText());
        	t.setLastName(this.lastNameTxtField.getText());
        	ObservableList<LearningField> list = this.checkListView.getCheckModel().getCheckedItems();
        	t.learningFieldProperty().clear();
        	t.learningFieldProperty().addAll(list);
        	// TODO: update teacher anhand von id
    	}
    	this.choiceBoxAction();
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    @FXML
    private void handleRemove() {
    	Teacher t = this.choiceBox.getSelectionModel().getSelectedItem();
    	this.teacherList.remove(t);
    	// TODO: remove teacher anhnd von id
    	this.choiceBoxAction();
    }
}
