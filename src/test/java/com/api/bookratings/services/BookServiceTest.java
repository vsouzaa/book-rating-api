package com.api.bookratings.services;

import com.api.bookratings.exception.BadRequestException;
import com.api.bookratings.model.Book;
import com.api.bookratings.repositories.BookRepository;
import com.api.bookratings.util.StartBook;
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
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setup(){
        Mockito.when(bookRepository.findAll()).thenReturn(List.of(StartBook.bookExpected()));
        Mockito.when(bookRepository.findById(StartBook.bookExpected().getId())).thenReturn(StartBook.bookOptional());
        Mockito.when(bookRepository.save(ArgumentMatchers.any(Book.class))).thenReturn(StartBook.bookExpected());
    }

    @Test
    void findAllReturnsListOfBookWhenSuccessful(){
        Assertions.assertNotNull(bookService.findAll());
        assertEquals(1, bookRepository.findAll().size());
    }

    @Test
    void findByIdReturnsBookWhenSuccessful(){
        Optional<Book> bookOptional = bookService.findById(StartBook.bookExpected().getId());
        Book bookFound = bookOptional.orElse(null);
        assert bookFound != null;
        Assertions.assertEquals(bookFound.getId(), bookOptional.get().getId());
        Assertions.assertEquals(bookFound.getName(), bookOptional.get().getName());
        Assertions.assertEquals(bookFound.getIsbn10(), bookOptional.get().getIsbn10());
        Assertions.assertEquals(bookFound.getRatingsavg(), bookOptional.get().getRatingsavg());
        Assertions.assertEquals(bookFound.getRatings_list(), bookOptional.get().getRatings_list());
    }

    @Test
    void saveReturnsBookWhenSuccessful(){
        Book bookSaved = bookService.save(StartBook.bookExpected());
        Assertions.assertEquals(bookSaved.getId(), StartBook.bookExpected().getId());
        Assertions.assertEquals(bookSaved.getName(), StartBook.bookExpected().getName());
        Assertions.assertEquals(bookSaved.getIsbn10(), StartBook.bookExpected().getIsbn10());
        Assertions.assertEquals(bookSaved.getRatingsavg(), StartBook.bookExpected().getRatingsavg());
        Assertions.assertEquals(bookSaved.getRatings_list(), StartBook.bookExpected().getRatings_list());
    }

    @Test
    void saveDtoReturnsBookWhenSuccessful(){
        Mockito.when(bookRepository.existsByIsbn10(StartBook.bookDto().getIsbn10())).thenReturn(false);
        Book bookSaved = bookService.saveDto(StartBook.bookDto());
        Assertions.assertNotNull(bookSaved.getId());
        Assertions.assertEquals(bookSaved.getName(), StartBook.bookDto().getName());
        Assertions.assertEquals(bookSaved.getIsbn10(), StartBook.bookDto().getIsbn10());
    }

    @Test
    void saveDtoThrowsBadRequestExceptionWhenIsbn10AlreadyExists(){
        Mockito.when(bookRepository.existsByIsbn10(StartBook.bookDto().getIsbn10())).thenReturn(true);
        Assertions.assertThrows(BadRequestException.class, () ->  bookService.saveDto(StartBook.bookDto()));
    }

    @Test
    void updateSaveBookWhenSuccessful(){
        bookService.update(StartBook.bookExpected().getId(), StartBook.bookDto());
        Mockito.verify(bookRepository, Mockito.times(1)).save(StartBook.bookExpected());
    }

    @Test
    void updateThrowsBadRequestExceptionWhenBookNotFound(){
        Assertions.assertThrows(BadRequestException.class, () -> bookService.update(3L, StartBook.bookDto()));
    }

    @Test
    void existsByIsbn10ReturnsTrueWhenSuccessful(){
        Mockito.when(bookRepository.existsByIsbn10(StartBook.bookDto().getIsbn10())).thenReturn(true);
        Assertions.assertTrue(bookService.existsByIsbn10(StartBook.bookExpected().getIsbn10()));
        Assertions.assertFalse(bookService.existsByIsbn10("123456789X"));
    }

    @Test
    void deleteVerifyOneWhenSuccessful(){
        Mockito.doNothing().when(bookRepository).delete(StartBook.bookExpected());
        bookService.delete(StartBook.bookExpected().getId());
        Mockito.verify(bookRepository, Mockito.times(1)).delete(StartBook.bookExpected());
    }

    @Test
    void deleteThrowsBadRequestExceptionWhenBookNotFound(){
        Mockito.doThrow(BadRequestException.class).when(bookRepository).delete(StartBook.bookExpected());
        Assertions.assertThrows(BadRequestException.class, () -> bookService.delete(StartBook.bookExpected().getId()));
    }
    @Test
    void deleteAllVerifyOneWhenSuccessful(){
        Mockito.doNothing().when(bookRepository).deleteAll();
        bookService.deleteAll();
        Mockito.verify(bookRepository, Mockito.times(1)).deleteAll();
    }

}