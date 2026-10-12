package edu.westga.comp4420.library_app.test.model.openlibraryapi;

import edu.westga.comp4420.library_app.model.Book;
import edu.westga.comp4420.library_app.model.OpenLibraryAPI;
import javafx.application.Platform;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


class TestSearchBooks {
	private List<Book> search(String query) throws Exception {
		return new OpenLibraryAPI().searchBooks(query).get(30, TimeUnit.SECONDS);

	}
	@Test
	void testKnownBookReturnsResult() throws Exception {
		List<Book> result = this.search("crime and punishment");
		assertFalse(result.isEmpty());
		assertTrue(result.size() <= 10, "checking limit of 10");
	}
	@Test
	void testEveryBook() throws Exception {
		List<Book> result = this.search("dostoevsky");
		assertFalse(result.isEmpty());
		for (Book book : result) {
			assertNotNull(book.getTitle());
			assertFalse(book.getTitle().isEmpty());
			assertNotNull(book.getAuthor());
			assertFalse(book.getAuthor().isEmpty());
			assertNotNull(book.getPublishYear());
			assertNotNull(book.getPageCount());
			assertTrue(book.getCoverUrl().equals("https://placehold.co/100x160.png?text=Placeholder") || book.getCoverUrl().endsWith(".jpg"));
		}
	}
	@Test
	void testEmptyQuery() {
		assertThrows(IllegalArgumentException.class, () -> new OpenLibraryAPI().searchBooks(""));
	}
}
