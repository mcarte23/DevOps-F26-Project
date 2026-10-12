package edu.westga.comp4420.library_app.view.codebehind;

import edu.westga.comp4420.library_app.model.Book;
import edu.westga.comp4420.library_app.model.OpenLibraryAPI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.List;

import edu.westga.comp4420.library_app.Main;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * CodeBehind To Handle Processing for the MainWindow
 *
 * @author	Comp 4420
 * @version Spring 2025
 */
public class MainWindow {
	@FXML private TilePane bookGrid;
	private static final double CARD_WIDTH = 120;
	private static final double IMAGE_WIDTH = 100;
	private static final double IMAGE_HEIGHT = 160;

	private VBox createBookCard(Book book) {
		Label title = new Label(book.getTitle());
		title.setMaxWidth(CARD_WIDTH);
		title.setWrapText(true);
		Label author = new Label(book.getAuthor());
		author.setMaxWidth(CARD_WIDTH);
		author.setWrapText(true);
		VBox card;

		ImageView cover = new ImageView();
		cover.setImage(new Image(book.getCoverUrl(), true));
		cover.setFitWidth(IMAGE_WIDTH);
		cover.setFitHeight(IMAGE_HEIGHT);
		card = new VBox(4, cover, title, author);

        card.setMaxWidth(CARD_WIDTH);
		card.setCursor(Cursor.HAND);
		card.setOnMouseClicked(e -> this.openBookDetails(book));
		return card;
	}

	private void displayBooks(List<Book> books) {
		for (Book book : books) {
			this.bookGrid.getChildren().add(this.createBookCard(book));
		}
	}
	private void openBookDetails(Book book) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(Main.BOOK_DETAILS_RESOURCE));
			loader.load();
			Parent parent = loader.getRoot();
			BookDetails details = loader.getController();
			details.setBookDetails(book);
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setTitle(book.getTitle());
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);


			stage.showAndWait();
		} catch (IOException error) {
			Alert errorBox = new Alert(Alert.AlertType.ERROR);
			errorBox.setContentText("Unable to open add window");
			errorBox.showAndWait();
		}
	}
    private final OpenLibraryAPI api = new OpenLibraryAPI();

	@FXML
	void initialize() {
		assert this.bookGrid != null : "fx:id=\"bookGrid\" was not injected";
		this.api.searchBooks("crime and punishment").thenAccept(books -> Platform.runLater(() -> this.displayBooks(books)));
	}

}
