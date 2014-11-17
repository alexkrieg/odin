package application.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import application.MainApplication;
import application.controller.FieldViewListCellController;
import application.model.LearningField;

public class FieldViewListCell extends ListCell<LearningField> {
	@Override
	public void updateItem(LearningField field, boolean empty){
		super.updateItem(field, empty);
		if(field != null){
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FieldListViewCell.fxml"));
			MainApplication.log("Controlller:"+fxmlLoader.getController());
			try{
				fxmlLoader.load();
			} catch(Exception e) {
			}
			FieldViewListCellController c = fxmlLoader.getController();
			c.setLearningField(field);
			setGraphic(c.getHBox());
			c.setCell(this);
		}
	}
	
}
