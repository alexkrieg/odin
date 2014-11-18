package application;
	

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import application.model.*;
import application.controller.*;


public class MainApplication extends Application {
	
    //================================================================================
    // Properties
    //================================================================================
	private static final String APPLICATION_TITLE = "Stundenplaner";
	private Stage primaryStage;
	
	private MySQLAccessManager sqlManager;
	private FXMLLoader fxmlLoader;
	
	private ObservableList<Teacher> teacherData = FXCollections.observableArrayList();
	private ObservableList<LearningField> learningFieldData = FXCollections.observableArrayList();
	private ObservableList<Room> blabla = FXCollections.observableArrayList();
	private ObservableList<SchoolClass> classData = FXCollections.observableArrayList();
	
    //================================================================================
    // Cunstructors
    //================================================================================
	public MainApplication(){
		this.sqlManager = new MySQLAccessManager();
		//this.sqlManager.connectToMySQL("localhost:3306",Constants.MYSQL_DB, Constants.MYSQL_USER,Constants.MYSQL_PW);
		Teacher t1 = new Teacher("Hans", "Junke");
		learningFieldData.add(new LearningField("Lernfeld1", "Bl�dsinn",0));
		learningFieldData.add(new LearningField("Lernfeld2", "Bl�dsinn", 1));
		learningFieldData.add(new LearningField("Lernfeld3", "Bl�dsinn", 2));
		learningFieldData.add(new LearningField("Lernfeld4", "Bl�dsinn", 3));
		learningFieldData.add(new LearningField("Lernfeld5", "Bl�dsinn", 4));
		learningFieldData.add(new LearningField("Lernfeld6", "Bl�dsinn", 5));
		learningFieldData.add(new LearningField("Lernfeld7", "Bl�dsinn", 8));
		learningFieldData.add(new LearningField("Lernfeld7", "Bl�dsinn", 9));
		learningFieldData.add(new LearningField("Lernfeld7", "Bl�dsinn", 7));
		learningFieldData.add(new LearningField("Lernfeld7", "Bl�dsinn", 11));
		learningFieldData.add(new LearningField("Lernfeld7", "Bl�dsinn",22));
		learningFieldData.add(new LearningField("Lernfeld7", "Bl�dsinn", 33));
		learningFieldData.add(new LearningField("Lernfeld7", "Bl�dsinn", 88));
		learningFieldData.add(new LearningField("Lernfeld7", "Bl�dsinn", 77));
		t1.addLearningField(learningFieldData.get(0));
		t1.addLearningField(learningFieldData.get(1));
		t1.addLearningField(learningFieldData.get(4));
		t1.addLearningField(learningFieldData.get(5));
		teacherData.add(t1);
		teacherData.add(new Teacher("Lehrer", "Lehrer1"));
		teacherData.add(new Teacher("Lehrer", "Lehrer2"));
		teacherData.add(new Teacher("Lehrer", "Lehrer4"));
		teacherData.add(new Teacher("Lehrer", "Lehrer5"));
		teacherData.add(new Teacher("Lehrer", "Lehrer6"));
		teacherData.add(new Teacher("Lehrer", "Lehrer7"));
		teacherData.add(new Teacher("Lehrer", "Lehrer8"));

		blabla.add(new Room("Allo"));
		blabla.add(new Room("Raum 2"));
		blabla.add(new Room("Raum 3"));
		blabla.add(new Room("Raum 4"));
		blabla.add(new Room("Raum 5"));
		blabla.add(new Room("Raum 6"));
		blabla.add(new Room("Raum 7"));
		classData.add(new SchoolClass("Klasse 1"));
		classData.add(new SchoolClass("Klasse 2"));
		classData.add(new SchoolClass("Klasse 3"));
		classData.add(new SchoolClass("Klasse 4"));
		classData.add(new SchoolClass("Klasse 5"));
		classData.add(new SchoolClass("Klasse 6"));
	}
	
    //================================================================================
    // Accessors
    //================================================================================
	public ObservableList<Teacher> getTeacherData(){
		return this.teacherData;
	}
	public ObservableList<LearningField> getLearningFieldData(){
		return this.learningFieldData;
	}
	public MySQLAccessManager sharedSQLManager(){
		return this.sqlManager;
	}
    //================================================================================
    // Starter
    //================================================================================
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage=primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("view/MainWindow.fxml"));
			BorderPane root = loader.load();
			Scene scene = new Scene(root,1280,800);
			scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());
			this.primaryStage.setScene(scene);
			this.primaryStage.setTitle(MainApplication.APPLICATION_TITLE);
			MainWindowController contr  = loader.getController();
			contr.setMainApp(this);
			this.primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
    //================================================================================
    // Show Dialogs
    //================================================================================
	private Stage generateStage(String view, String title, boolean transparent){
		try {
			this.fxmlLoader = new FXMLLoader();
			String url = "view/"+view+".fxml";
			this.fxmlLoader.setLocation(MainApplication.class.getResource(url));
			AnchorPane page = (AnchorPane) this.fxmlLoader.load();
	        Stage dialogStage = new Stage();
	        if(transparent){
	        	dialogStage.initStyle(StageStyle.TRANSPARENT);
	        }else{
	        	dialogStage.initStyle(StageStyle.UTILITY);
	        }	        
	        dialogStage.setTitle(title);
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        dialogStage.setResizable(false);
			return dialogStage;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void showClassDialog(){
        Stage dialogStage = this.generateStage("ClassDialog",ClassDialogController.DIALOG_STAGE_TITLE,false);
        ClassDialogController controller = this.fxmlLoader.getController();
        controller.setDialogStage(dialogStage);
        controller.setClassList(this.classData);
		dialogStage.showAndWait();
	}
	public void showTeacherDialog(){
        Stage dialogStage = this.generateStage("TeacherDialog",TeacherDialogController.DIALOG_STAGE_TITLE,false);
        TeacherDialogController controller = this.fxmlLoader.getController();
        controller.setDialogStage(dialogStage);
        controller.setTeachers(this.teacherData);
        controller.setFields(this.learningFieldData);
        controller.setDataHandler(this);
		dialogStage.showAndWait();
	}
	public void showAboutDialog(){
        Stage dialogStage = this.generateStage("AboutDialog",AboutDialogController.DIALOG_STAGE_TITLE,false);
        AboutDialogController controller = this.fxmlLoader.getController();
        controller.setDialogStage(dialogStage);
		dialogStage.showAndWait();
	}
	public void showLearningFieldDialog(){
        Stage dialogStage = this.generateStage("LearningFieldDialog",LearningDialogController.DIALOG_STAGE_TITLE,false);
        LearningDialogController controller = this.fxmlLoader.getController();
        MainApplication.log(controller+"");
        controller.setDialogStage(dialogStage);
        controller.setLearningFields(this.learningFieldData);
		dialogStage.showAndWait();
	}
	public void showRoomDialog(){
        Stage dialogStage = this.generateStage("RoomDialog",RoomDialogController.DIALOG_STAGE_TITLE,false);
        RoomDialogController controller = this.fxmlLoader.getController();
        controller.setDialogStage(dialogStage);
        controller.setRoomList(this.blabla);
		dialogStage.showAndWait();
	}
	public void showConfigurationDialog(String hFrom, String hTo,String tFrom,String tTo, String day){
		Stage dialogStage = this.generateStage("ConfigurationDialog", ConfigurationDialogController.DIALOG_STAGE_TITLE, false);
		ConfigurationDialogController controller = this.fxmlLoader.getController();
		controller.setDialogStage(dialogStage);
		controller.setTimeInformation(hFrom,hTo,tFrom,tTo,day);
		dialogStage.showAndWait();
	}
    //================================================================================
    // Launch / Log / Close
    //================================================================================
	public static void main(String[] args) {
		launch(args);
	}
	public static void log(String toLog){
		System.out.println(toLog);
	}
	public void close(){
		this.primaryStage.close();
	}
}
