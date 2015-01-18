package application.factory;

import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import application.MainApplication;
import application.model.Lesson;
import application.model.TimePeriod;

public class FormattedTableCellFactory<S,T> implements Callback<TableColumn<TimePeriod, ObservableList<Lesson>>, TableCell<TimePeriod, ObservableList<Lesson>>> {

	public FormattedTableCellFactory() {
	}
	@Override
	public TableCell<TimePeriod, ObservableList<Lesson>> call(TableColumn<TimePeriod, ObservableList<Lesson>> param) {
		// TODO Auto-generated method stub
		return new TableCell<TimePeriod, ObservableList<Lesson>>() {
	        @Override
	        protected void updateItem(ObservableList<Lesson> item, boolean empty) {
	            super.updateItem(item, empty);    
	            this.setOnMouseClicked((event)->{
	            	if(event.getClickCount() == 2){
	            		MainApplication.globalMain.showConfigurationDialog(item);	
	            	}
	            });
	            this.setOnMouseEntered((event)->{
	            });
	            this.setOnMouseExited((event)->{
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
