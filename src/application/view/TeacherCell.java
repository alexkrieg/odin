package application.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import application.MainApplication;
import application.model.Teacher;

public class TeacherCell extends ListCell<Teacher> {
    //================================================================================
    // Properties
    //================================================================================
	private VBox vb;
	private HBox hb;
	private VBox ib;
	private Label firstName;
	private Label lastName;
	private Label learningFields;
	private Button optionBtn;
	private Teacher teacher;
	
	
    //================================================================================
    // Constructors
    //================================================================================
	public TeacherCell(){
		String style= "-fx-font-family: 'Segoe UI', Helvetica, Arial, sans-serif;-fx-font-size: 12pt;-fx-text-fill: #000000;";
		hb = new HBox();vb = new VBox();ib = new VBox();
		hb.setAlignment(Pos.CENTER_LEFT);
		ib.setAlignment(Pos.CENTER_LEFT);ib.setPrefWidth(200);
		vb.setAlignment(Pos.CENTER_LEFT);
		firstName = new Label();
		lastName = new Label();
		firstName.setStyle(style);
		lastName.setStyle(style+"-fx-font-weight : bold;-fx-font-size: 14pt;");
		learningFields = new Label();
		learningFields.setStyle(style + "-fx-font-size: 10pt;");
		optionBtn = new Button();
		optionBtn.setAlignment(Pos.CENTER_RIGHT);
		optionBtn.setVisible(false);
		Image imageDecline = new Image(getClass().getResourceAsStream("setting.png"));
		optionBtn.setGraphic(new ImageView(imageDecline));
		ib.getChildren().addAll(firstName,lastName,learningFields);
		hb.getChildren().addAll(ib,optionBtn);
		setGraphic(hb);
		
	}
	@Override
	public void updateItem(Teacher newTeacher, boolean empty){
		if(newTeacher != null){
			this.teacher = newTeacher;
			firstName.setText(newTeacher.firstNameProperty().get());
			lastName.setText(newTeacher.lastNameProperty().get());
			learningFields.setText(newTeacher.getLearningFieldNames());
			this.setOnMouseEntered((event)-> {
				this.onMouseOver();
			});
			this.setOnMouseExited((event)-> {
				this.onMouseOut();
			});
			this.setOnDragDetected((event)->{
				MainApplication.log("Drag");
				this.onDragDetected();
			});
			this.setOnDragDropped((event)->{
				MainApplication.log("Dropped");
			});
		}
	}
	
    //================================================================================
    // Action Handlers
    //================================================================================
	private void onMouseOver(){
		this.optionBtn.setVisible(true);
	}
	private void onMouseOut(){
		this.optionBtn.setVisible(false);
	}
	private void onDragDetected(){
		Dragboard db = this.startDragAndDrop(TransferMode.ANY);
		ClipboardContent content = new ClipboardContent();
		content.put(Teacher.FORMAT, this.teacher.toString());
		//content.putString(this.teacher.lastNameProperty().get());
		db.setContent(content);
	}
    //================================================================================
    // Accesors
    //================================================================================
	public Button getOptionButton(){
		return this.optionBtn;
	}
}
