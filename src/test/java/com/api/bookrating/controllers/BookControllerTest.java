package com.api.bookrating.controllers;

import com.api.bookrating.model.Book;
import com.api.bookrating.services.BookService;
import com.api.bookrating.util.StartBook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class BookControllerTest {

    @InjectMocks
    private BookController bookController;
    @Mock
    private BookService bookService;

    @BeforeEach
    void setup(){
        Mockito.when(bookService.findAll()).thenReturn(List.of(StartBook.bookExpected()));
        Mockito.when(bookService.findById(ArgumentMatchers.anyLong())).thenReturn(StartBook.bookOptional());
    }

    @Test
    void findAllReturnsListOfBooksWhenSuccessful(){
        Assertions.assertNotNull(bookController.findAll());
        assertEquals(1, bookService.findAll().size());
    }

    @Test
    void findByIdReturnsBooksWhenSuccessful(){
        Book bookFound = bookController.findById(StartBook.bookExpected().getId());
        Assertions.assertNotNull(bookFound);
    }

    @Test
    void saveDtoReturnsBookNameWhenSuccessful(){
        String bookName = bookController.save(StartBook.bookDto());
        Assertions.assertNotNull(bookName);
        Assertions.assertTrue(bookName.contains(StartBook.bookDto().getName()));
    }

    @Test
    void deleteReturnsBookIdWhenSuccessful(){
        String bookDeleted = bookController.delete(StartBook.bookExpected().getId());
        Assertions.assertNotNull(bookDeleted);
        Assertions.assertTrue(bookDeleted.contains(StartBook.bookExpected().getId().toString()));
    }

    @Test
    void deleteAllReturnsStringWhenSuccessful(){
        String booksDeleted = bookController.deleteAll();
        Assertions.assertNotNull(booksDeleted);
    }

    @Test
    void updateReturnsBookNameWhenSuccessful(){
        String updatedName = bookController.updateBook(StartBook.bookExpected().getId(), StartBook.bookDto());
        Assertions.assertNotNull(updatedName);
        Assertions.assertTrue(updatedName.contains(StartBook.bookDto().getName()));
    }


}