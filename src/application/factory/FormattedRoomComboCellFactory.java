package application.factory;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import application.model.Room;

public class FormattedRoomComboCellFactory<S> implements Callback<ListView<Room>, ListCell<Room>> {

	public FormattedRoomComboCellFactory() {
	}
	@Override
	public ListCell<Room> call(ListView<Room> param) {
		// TODO Auto-generated method stub
		return new ListCell<Room>() {
	        @Override
	        protected void updateItem(Room item, boolean empty) {
	            super.updateItem(item, empty);
	            if(item == null){
	            	return;
	            }
	            HBox box = new HBox();
	            Label l = new Label();
	            l.setStyle("-fx-text-fill: black;");
	            l.setText(item.toString());
	            Pane p = new Pane();
	            p.setMaxHeight(10);p.setMinHeight(10);
	            p.setMaxWidth(10);p.setMinWidth(10);
	            if(item.isAvailable()){
	            	p.setStyle("-fx-background-color: green");
	            }else{
	            	p.setStyle("-fx-background-color: red");
	            }
	            box.getChildren().add(l);
	            box.getChildren().add(p);
	            setGraphic(box);
	        }
	    };
	}

} 