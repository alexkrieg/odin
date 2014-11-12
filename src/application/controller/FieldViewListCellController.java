package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import application.model.LearningField;

public class FieldViewListCellController {
    //================================================================================
    // Properties
    //================================================================================
	@FXML
	private Label idCellLabel;
	@FXML
	private Label nameCellLabel;
	@FXML
	private HBox hbox;
	
    //================================================================================
    // Constructors
    //================================================================================
	public FieldViewListCellController(){
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../view/FieldListViewCell.fxml"));
		fxmlloader.setController(this);
		try{
			fxmlloader.load();
		} catch(Exception e) {
		}
	}
    @FXML
    private void initialize() {

    }
    //================================================================================
    // Setters
    //================================================================================
    public void setLearningField(LearningField field){
    	if(field != null){
    		//this.idCellLabel.setText(field.getID()+"");
        	//this.nameCellLabel.setText(field.getNameProperty().get());
    	}
    	
    }
    //================================================================================
    // Accessors
    //================================================================================
    public HBox getHBox(){
    	return this.hbox;
    }
}
