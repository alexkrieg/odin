package application.controller;

import java.util.ArrayList;

import org.controlsfx.dialog.Dialogs;

import application.MainApplication;
import application.model.LearningField;
import application.model.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RoomDialogController {
    //================================================================================
    // Properties
    //================================================================================
	public static final String DIALOG_STAGE_TITLE = "Raum bearbeiten ...";
	private Stage dialogStage;
	private MainApplication dataHandler;
	private ObservableList<Room> roomList = FXCollections.observableArrayList();
	
	@FXML
	private ComboBox<Room> choiceBox;
	@FXML
	private Button removeButton;
	@FXML
	private TextField characteristicTxtField;
	@FXML
	private TextField nameTxtField;
    //================================================================================
    // Cunstructors
    //================================================================================
	public RoomDialogController(){
		this.roomList.add(new Room("Neuen Raum hinzuf¸gen ...", -1));
		ArrayList<Room> list = MainApplication.globalMain.sharedSQLManager().selectAllRooms();
		this.setRoomList(FXCollections.observableArrayList(list));
	}
    @FXML
    private void initialize() {
    	this.choiceBox.setItems(this.roomList);
    	this.choiceBox.getSelectionModel().select(0);
    	this.choiceBoxAction();
    }
    private void setRoom(Room room){
    	if(room.getId() == -1){
    		this.nameTxtField.setText("");
    		this.characteristicTxtField.setText("");
    		this.removeButton.setDisable(true);
    	}else{
    		this.nameTxtField.setText(room.getName().get());
    		this.characteristicTxtField.setText(room.getCharacteristic().get());
    		this.removeButton.setDisable(false);
    	}
    }
    public void setRoomList(ObservableList<Room> list){
    	this.roomList.addAll(list);
    }
    public void setDataHandler(MainApplication dataHandler){
    	this.dataHandler = dataHandler;
    }
    public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
    //================================================================================
    // Action Handler
    //================================================================================
    @FXML
    private void choiceBoxAction(){
    	this.setRoom(this.choiceBox.getSelectionModel().getSelectedItem());
    }
    @FXML
    private void handleOK() {
    	if(this.choiceBox.getSelectionModel().getSelectedItem().getId() == -1){
    		Room r = new Room(this.nameTxtField.getText());
    		r.setCharacteristic(this.characteristicTxtField.getText());
    		this.roomList.add(r);
    		MainApplication.globalMain.sharedSQLManager().addNewRoom(r);
    	}else{
    		Room r = this.choiceBox.getSelectionModel().getSelectedItem();
    		r.setCharacteristic(this.characteristicTxtField.getText());
    		r.setName(this.nameTxtField.getText());
    		// TODO : update room anhand von id 
    	}
    	Dialogs.create()
        .owner(null)
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
    	this.roomList.remove(this.choiceBox.getSelectionModel().getSelectedItem());
    	this.choiceBoxAction();
    	//TODO: remove fieldlist anhand id from db
    }
}
