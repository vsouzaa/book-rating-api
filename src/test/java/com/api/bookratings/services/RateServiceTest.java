package com.api.bookratings.services;

import com.api.bookratings.exception.BadRequestException;
import com.api.bookratings.model.Book;
import com.api.bookratings.model.Rate;
import com.api.bookratings.model.User;
import com.api.bookratings.repositories.RateRepository;
import com.api.bookratings.util.StartBook;
import com.api.bookratings.util.StartRate;
import com.api.bookratings.util.StartUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class RateServiceTest {

    @InjectMocks
    private RateService rateService;
    @Mock
    private RateRepository rateRepository;
    @Mock
    private BookService bookService;
    @Mock UserService userService;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    void setup(){
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(StartUser.userExpected().getUsername());
        Mockito.when(userService.existsByUsername(ArgumentMatchers.anyString())).thenReturn(true);
        Mockito.when(userService.findByUsername(ArgumentMatchers.anyString())).thenReturn(StartUser.userExpected());
    }

    @Test
    void authenticatedUserObjectReturnsUser() {
        User authenticatedUser = rateService.authenticatedUser();
        Assertions.assertNotNull(authenticatedUser.getClass() == User.class);
        Assertions.assertEquals(authenticatedUser.getId(), StartUser.userExpected().getId());
        Assertions.assertEquals(authenticatedUser.getUsername(), StartUser.userExpected().getUsername());
        Assertions.assertEquals(authenticatedUser.getPassword(), StartUser.userExpected().getPassword());
        Assertions.assertEquals(authenticatedUser.getRole(), StartUser.userExpected().getRole());
    }

    @Test
    void saveRateReturnsRateWhenSuccessful(){
        Mockito.when(bookService.findById(ArgumentMatchers.anyLong())).thenReturn(StartBook.bookOptional());
        Mockito.when(rateService.authenticatedUser()).thenReturn(StartUser.userExpected());
        Mockito.when(bookService.findAll()).thenReturn(List.of(StartBook.bookExpected()));
        Mockito.when(rateRepository.findAll()).thenReturn(List.of()).thenReturn(List.of(StartRate.rate()));

        Rate savedRate = rateService.save(StartRate.rateDto());
        Assertions.assertNotNull(savedRate);
        Assertions.assertEquals(savedRate.getBook_id(), savedRate.getBook_id());
        Assertions.assertEquals(savedRate.getUser(), savedRate.getUser());
        Assertions.assertEquals(savedRate.getRate(), savedRate.getRate());

        Optional<Book> bookOptional = bookService.findById(savedRate.getBook_id());
        Book savedBookWithRatings = bookOptional.orElse(null);
        assert savedBookWithRatings != null;

        Assertions.assertEquals(savedBookWithRatings.getRatings_list(), List.of(4,5));
        Assertions.assertEquals(savedBookWithRatings.getRatingsavg(), 4.5);

    }

    @Test
    void saveRateThrowsBadRequestExceptionWhenBookNotFound(){
        Assertions.assertThrows(BadRequestException.class, () -> rateService.save(StartRate.rateDtoNotFound()));
    }

    @Test
    void saveRateThrowsBadRequestExceptionWhenBookHasAlreadyBeenRatedByTheSameUser(){
        Mockito.when(bookService.findById(ArgumentMatchers.anyLong())).thenReturn(StartBook.bookOptional());
        Mockito.when(rateRepository.findAll()).thenReturn(List.of(StartRate.rate()));
        Assertions.assertThrows(BadRequestException.class, () -> rateService.save(StartRate.rateDto()));
    }

}