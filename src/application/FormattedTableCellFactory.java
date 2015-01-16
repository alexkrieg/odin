package application;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import application.model.Lesson;
import application.model.TimePeriod;

public class FormattedTableCellFactory<S,T> implements Callback<TableColumn<TimePeriod, Lesson>, TableCell<TimePeriod, Lesson>> {

	public FormattedTableCellFactory() {
	}
	@Override
	public TableCell<TimePeriod, Lesson> call(TableColumn<TimePeriod, Lesson> param) {
		// TODO Auto-generated method stub
		return new TableCell<TimePeriod, Lesson>() {
	        @Override
	        protected void updateItem(Lesson item, boolean empty) {
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
	            if(item != null && item.isEmpty() == false){
	            	this.textProperty().set(item.makeMePretty());
	            }else{
	            	this.textProperty().set("");
	            }
	        }
	    };
	}

}
