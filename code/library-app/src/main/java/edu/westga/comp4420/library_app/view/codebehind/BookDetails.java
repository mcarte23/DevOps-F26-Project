package edu.westga.comp4420.library_app.view.codebehind;

import edu.westga.comp4420.library_app.model.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class BookDetails {
    @FXML private Label titleLabel;
    @FXML private Label authorLabel;
    @FXML private Label publishYearLabel;
    @FXML private Label pageCountLabel;
    @FXML private ImageView coverImage;
    @FXML private VBox guiPane;
    private void closeWindow() {
        this.guiPane.getScene().getWindow().hide();
    }
    public void setBookDetails(Book book) {
        this.titleLabel.setText("Title " + book.getTitle());
        this.authorLabel.setText("Author: " + book.getAuthor());
        this.publishYearLabel.setText("Published: " + book.getPublishYear());
        this.pageCountLabel.setText("Page Count: " + book.getPageCount());
        this.coverImage.setImage(new Image(book.getCoverUrl(), true));
    }
    @FXML void onCloseButton(ActionEvent event) {
        this.closeWindow();
    }

}
