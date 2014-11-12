package application.view;

import application.controller.FieldViewListCellController;
import application.model.LearningField;
import javafx.scene.control.ListCell;

public class FieldViewListCell extends ListCell<LearningField> {
	@Override
	public void updateItem(LearningField field, boolean empty){
		super.updateItem(field, empty);
		if(field != null){
			FieldViewListCellController c = new FieldViewListCellController();
			c.setLearningField(field);
			setGraphic(c.getHBox());
		}
	}
}
