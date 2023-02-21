package com.api.bookrating.util;

import com.api.bookrating.dto.RateDto;
import com.api.bookrating.model.Rate;
public class StartRate {

    public static Rate rate(){
        return new Rate(StartBook.bookExpected().getId(), StartUser.userExpected(), 5);
    }
    public static Rate rateExpected(){
        return new Rate(StartBook.bookExpected().getId(), StartUser.userExpected(), 4);
    }
    public static RateDto rateDto(){
        return new RateDto(StartBook.bookExpected().getId(), 4);
    }

    public static RateDto rateDtoNotFound(){
        return new RateDto(3L, 5);
    }

}
