package edu.westga.comp4420.library_app.test.model.book;

import static org.junit.jupiter.api.Assertions.*;
import edu.westga.comp4420.library_app.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


class TestConstructor {
	
	@ParameterizedTest
	@CsvSource({"Crime and Punishment, Fyodor Dostoevsky, https://covers.openlibrary.org/b/olid/OL9004678M-L.jpg, 1866, 520"})
	void testValidInput(String title, String author, String coverUrl, String publishYear, String pageCount) {
		Book result = new Book(title, author, coverUrl, publishYear, pageCount);
		assertEquals(title, result.getTitle(), "checking title");
		assertEquals(author, result.getAuthor(), "checking author");
		assertEquals(coverUrl, result.getCoverUrl(), "checking url");
		assertEquals(publishYear, result.getPublishYear(), "check publish year");
		assertEquals(pageCount, result.getPageCount(), "checking page count");
	}

	@Test
	void testNullTitle() {
		assertThrows(IllegalArgumentException.class, () -> new Book(null, "Fyodor Dostoevsky", "url", "1866", "520"));
	}
	@Test
	void testNullAuthor() {
		assertThrows(IllegalArgumentException.class, () -> new Book("Crime and Punishment", null, "url", "1866", "520"));
	}
	@Test
	void testNullCover() {
		assertThrows(IllegalArgumentException.class, () -> new Book("Crime and Punishment", "Fyodor Dostoevsky", null, "1866", "520"));
	}
	@Test
	void testNullPublishYear() {
		assertThrows(IllegalArgumentException.class, () -> new Book("Crime and Punishment", "Fyodor Dostoevsky", "url", null, "520"));
	}
	@Test
	void testNullPageCount() {
		assertThrows(IllegalArgumentException.class, () -> new Book("Crime and Punishment", "Fyodor Dostoevsky", "url", "1866", null));
	}


}
