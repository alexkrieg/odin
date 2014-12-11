package application;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import application.model.Lesson;
import application.model.LessonTimeInformation;
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
	            //setGraphic(null);
	            this.setOnMouseClicked((event)->{
	            	if(event.getClickCount() == 2){
	            		Lesson l = item;
	            		MainApplication.globalMain.showConfigurationDialog(l,l.getTimeInformation());	
	            	}
	            });
	            if(item != null){
	            	MainApplication.log("Item: "+item+"");
	            	this.textProperty().set(item+"");
	            }
	        }
	    };
	}

}
