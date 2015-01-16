package application;
	

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import application.controller.AboutDialogController;
import application.controller.ClassDialogController;
import application.controller.ConfigurationDialogController;
import application.controller.LearningDialogController;
import application.controller.MainWindowController;
import application.controller.RoomDialogController;
import application.controller.TeacherDialogController;
import application.model.Lesson;


public class MainApplication extends Application {
	
    //================================================================================
    // Properties
    //================================================================================
	public static MainApplication globalMain;
	
	
	private static final String APPLICATION_TITLE = "Stundenplaner";
	private Stage primaryStage;
	private MainWindowController mainController;
	private MySQLAccessManager sqlManager;
	private FXMLLoader fxmlLoader;
	
    //================================================================================
    // Cunstructors
    //================================================================================
	public MainApplication(){
		this.sqlManager = new MySQLAccessManager();
		MainApplication.globalMain = this;
	}
	
    //================================================================================
    // Accessors
    //================================================================================
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
			Scene scene = new Scene(root,1080,600);
			scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());
			this.primaryStage.setScene(scene);
			this.primaryStage.setTitle(MainApplication.APPLICATION_TITLE);
			this.mainController  = loader.getController();
			this.mainController.setMainApp(this);
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
		dialogStage.showAndWait();
	}
	public void showTeacherDialog(){
        Stage dialogStage = this.generateStage("TeacherDialog",TeacherDialogController.DIALOG_STAGE_TITLE,false);
        TeacherDialogController controller = this.fxmlLoader.getController();
        controller.setDialogStage(dialogStage);
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
        controller.setDialogStage(dialogStage);
		dialogStage.showAndWait();
	}
	public void showRoomDialog(){
        Stage dialogStage = this.generateStage("RoomDialog",RoomDialogController.DIALOG_STAGE_TITLE,false);
        RoomDialogController controller = this.fxmlLoader.getController();
        controller.setDialogStage(dialogStage);
		dialogStage.showAndWait();
	}
	public void showConfigurationDialog(Lesson l){
		Stage dialogStage = this.generateStage("ConfigurationDialog", ConfigurationDialogController.DIALOG_STAGE_TITLE, false);
		ConfigurationDialogController controller = this.fxmlLoader.getController();
		controller.setDialogStage(dialogStage);
		controller.setLessons(l);
		dialogStage.showAndWait();
	}    
	//================================================================================
    // Updater
    //================================================================================
	public void updateData(boolean updateTableView){
		this.mainController.reloadData(updateTableView);
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
