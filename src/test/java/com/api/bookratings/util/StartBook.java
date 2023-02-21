package com.api.bookratings.util;

import com.api.bookratings.dto.BookDto;
import com.api.bookratings.model.Book;
import java.util.Optional;

public class StartBook {

    public static Book bookExpected(){
        return new Book(1L, "The Hobbit", "J. R. R. Tolkien", "0261103342", 0, null);
    }
    public static BookDto bookDto(){
        return new BookDto("The Hobbit", "J. R. R. Tolkien", "0261103342");
    }
    public static Optional<Book> bookOptional(){
        return Optional.of(new Book(1L, "The Hobbit", "J. R. R. Tolkien", "0261103342", 0, null));
    }
}
