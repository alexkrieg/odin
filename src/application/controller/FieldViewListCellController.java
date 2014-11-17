package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import application.MainApplication;
import application.model.LearningField;
import application.view.FieldViewListCell;

public class FieldViewListCellController {
    //================================================================================
    // Properties
    //================================================================================
	@FXML
	private CheckBox checkbox;
	@FXML
	private Label idCellLabel;
	@FXML
	private Label nameCellLabel;
	@FXML
	private HBox hbox;
	
	private FieldViewListCell cell;
    //================================================================================
    // Constructors
    //================================================================================
	public FieldViewListCellController(){

	}
    @FXML
    private void initialize() {
    	MainApplication.log("init");
    	this.idCellLabel.setText("allo");
    	this.nameCellLabel.setText("allo");
    }
    //================================================================================
    // Setters
    //================================================================================
    public void setLearningField(LearningField field){
    	if(field != null){
    		this.idCellLabel.setText(field.getID()+"");
        	this.nameCellLabel.setText(field.getNameProperty().get());
    		MainApplication.log("allo");
    	}
    	
    }
    public void setCell(FieldViewListCell cell){
    	this.cell = cell;
    }
    //================================================================================
    // Accessors
    //================================================================================
    public HBox getHBox(){
    	return this.hbox;
    }
    public CheckBox getCheckBox(){
    	return this.checkbox;
    }
    //================================================================================
    // Action Handler
    //================================================================================
    @FXML
    private void onCheckItem(){
    	MainApplication.log("asaklsgh");
    }
}
