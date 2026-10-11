package edu.westga.comp4420.library_app.view.codebehind;

import edu.westga.comp4420.library_app.model.Book;
import edu.westga.comp4420.library_app.model.OpenLibraryAPI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.List;

import edu.westga.comp4420.library_app.Main;

/**
 * CodeBehind To Handle Processing for the MainWindow
 *
 * @author	Comp 4420
 * @version Spring 2025
 */
public class MainWindow {
	@FXML private TilePane bookGrid;
	private List<Book> bookList;
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
		if (!book.getCoverUrl().isEmpty()) {
			ImageView cover = new ImageView();
			cover.setImage(new Image(book.getCoverUrl(), true));
			cover.setFitWidth(IMAGE_WIDTH);
			cover.setFitHeight(IMAGE_HEIGHT);
			card = new VBox(4, cover, title, author);
		} else {
			card = new VBox(4, title, author);
		}
        card.setMaxWidth(CARD_WIDTH);
		return card;
	}

	private void displayBooks(List<Book> books) {
		for (Book book : books) {
			this.bookGrid.getChildren().add(this.createBookCard(book));
		}
	}
    private OpenLibraryAPI api = new OpenLibraryAPI();

	@FXML
	void initialize() {
		assert this.bookGrid != null : "fx:id=\"bookGrid\" was not injected";
		api.searchBooks("crime and punishment").thenAccept(books -> Platform.runLater(() -> displayBooks(books)));
	}

}
