package application.factory;

import application.model.Teacher;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class FormattedTeacherComboCellFactory<S> implements Callback<ListView<Teacher>, ListCell<Teacher>> {

	public FormattedTeacherComboCellFactory() {
	}
	@Override
	public ListCell<Teacher> call(ListView<Teacher> param) {
		// TODO Auto-generated method stub
		return new ListCell<Teacher>() {
	        @Override
	        protected void updateItem(Teacher item, boolean empty) {
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
