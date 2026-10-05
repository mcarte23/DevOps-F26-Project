package edu.westga.comp4420.library_app.view.codebehind;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

import edu.westga.comp4420.library_app.model.GroceryItem;
import edu.westga.comp4420.library_app.Main;

/**
 * CodeBehind To Handle Processing for the MainWindow
 *
 * @author	Comp 4420
 * @version Spring 2025
 */
public class MainWindow {
	@FXML private AnchorPane guiPane;

	@FXML
	void addItem(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.load();
			Parent parent = loader.getRoot();
			Scene scene = new Scene(parent);

		} catch (IOException error) {
			Alert errorBox = new Alert(AlertType.ERROR);
			errorBox.setContentText("Unable to open add window");
			errorBox.showAndWait();
		}
	}
	
	@FXML
	void initialize() {
		assert this.guiPane != null : "fx:id=\"guiPane\" was not injected: check your FXML file 'MainWindow.fxml'.";
	}
}
