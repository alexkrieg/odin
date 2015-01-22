package application.factory;

import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import application.MainApplication;
import application.model.Lesson;
import application.model.LessonTimeInformation;
import application.model.TimePeriod;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;

public class FormattedTableCellFactory<S,T> implements Callback<TableColumn<TimePeriod, ObservableList<Lesson>>, TableCell<TimePeriod, ObservableList<Lesson>>> {

	public FormattedTableCellFactory() {
	}
	@Override
	public TableCell<TimePeriod, ObservableList<Lesson>> call(TableColumn<TimePeriod, ObservableList<Lesson>> param) {
		// TODO Auto-generated method stub
		return new TableCell<TimePeriod, ObservableList<Lesson>>() {
			String tempStyle = "";
	        @Override
	        protected void updateItem(ObservableList<Lesson> item, boolean empty) {
	        	
	            super.updateItem(item, empty);    
	            this.setOnMouseClicked((event)->{
	            	if(event.getClickCount() == 2){
	            		if(event.getButton() == MouseButton.SECONDARY){
	            			for(Lesson l:item){
	            				if(l!= null && !l.isEmpty()){
	            					MainApplication.globalMain.sharedSQLManager().removeLesson(l.getId());
	            				}
	            			}
	            			MainApplication.globalMain.updateData(true);
	            		}
	            		else{
	            			MainApplication.globalMain.showConfigurationDialog(item);	
	            		}
	            	}
	            });
	            this.setOnMouseEntered((event)->{
	            });
	            this.setOnMouseExited((event)->{
	            });
	            this.setOnDragDetected((event)->{
	            	if(item != null && item.get(0).isEmpty() == false){
	            		String s = "";
	            		for(Lesson l: item){
	            			if(l!= null){
	            				s += l.getId()+",";
	            			}
	            		}
	            		Dragboard dragboard = startDragAndDrop(TransferMode.COPY);
		                ClipboardContent content = new ClipboardContent();
		                content.putString(s);
		                dragboard.setContent(content);
		                event.consume();
	            	}
	            	
	            });
	            setOnDragOver(event -> {
	                if (event.getGestureSource() != this && event.getDragboard().hasString()) {
	                	if(item.get(0)!= null){
	                		event.acceptTransferModes(TransferMode.COPY);
	                	}
	                    
	                }
	                event.consume();
	            });
	            setOnDragEntered(event -> {
	                if (event.getGestureSource() != this && event.getDragboard().hasString()) {
	                    setOpacity(0.3);
	                    tempStyle = getStyle();
	                    setStyle("-fx-border-color: black");
	                }
	            });
	 
	            setOnDragExited(event -> {
	                if (event.getGestureSource() != this && event.getDragboard().hasString()) {
	                	setStyle(tempStyle);
	                    setOpacity(1);
	                }
	            });
	            setOnDragDropped(event ->{
	            	Dragboard db = event.getDragboard();
	            	boolean success = false;
	            	if(db.hasString()){
	            		String[] ids = db.getString().split(",");
	            		LessonTimeInformation newTime = item.get(0).getTimeInformation();
	            		//TODO: select lesson anhand id save in new time information
	            		success = true;
	            	}
	            	event.setDropCompleted(success);
	            	event.consume();
	            });
	            String text = "";
	            if(item != null && item.get(0).isEmpty() == false){
	            	for(Lesson l : item){
	            		if(l!=null){
	            			text += l.makeMePretty()+"\n";
	            			if(l.isClassAvailable() != true || l.isRoomAvailable() != true || l.isTeacherAvailable() != true){
	            				setStyle("-fx-background-color: orange");
	            			}
	            		}
	            	}
	            }
	            textProperty().set(text);
	        }
	    };
	}

}
