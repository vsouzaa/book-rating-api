package com.api.bookratings.controllers;

import com.api.bookratings.dto.RateDto;
import com.api.bookratings.model.Rate;
import com.api.bookratings.services.BookService;
import com.api.bookratings.services.RateService;
import com.api.bookratings.util.StartRate;
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