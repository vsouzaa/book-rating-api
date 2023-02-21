package com.api.bookrating.controllers;

import com.api.bookrating.dto.RateDto;
import com.api.bookrating.model.Rate;
import com.api.bookrating.services.BookService;
import com.api.bookrating.services.RateService;
import com.api.bookrating.util.StartRate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class RateControllerTest {

    @InjectMocks
    private RateController rateController;
    @Mock
    private RateService rateService;
    @Mock
    private BookService bookService;

    @BeforeEach
    void setup(){
        Mockito.when(rateService.save(ArgumentMatchers.any(RateDto.class))).thenReturn(StartRate.rateExpected());
    }

    @Test
    void saveReturnsRateWhenSuccessful(){
        Rate savedRate = rateController.save(StartRate.rateDto());
        Assertions.assertNotNull(savedRate);
        Assertions.assertEquals(savedRate.getBook_id(), StartRate.rateDto().getBook_id());
        Assertions.assertEquals(savedRate.getRate(), StartRate.rateDto().getRate());
        Assertions.assertNotNull(savedRate.getUser());
    }

}