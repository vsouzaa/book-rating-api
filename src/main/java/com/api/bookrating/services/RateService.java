package com.api.bookrating.services;

import com.api.bookrating.dto.RateDto;
import com.api.bookrating.exception.BadRequestException;
import com.api.bookrating.model.Book;
import com.api.bookrating.model.Rate;
import com.api.bookrating.model.User;
import com.api.bookrating.repositories.RateRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RateService {

    private RateRepository rateRepository;
    private BookService bookService;
    private UserService userService;

    public User authenticatedUser(){
        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        if(userService.existsByUsername(authUserName)){
            return userService.findByUsername(authUserName);
        }
        return null;
    }
    @Transactional
    public Rate save(RateDto rateDto) {
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Book> bookOptional = bookService.findById(rateDto.getBook_id());
        if(bookOptional.isEmpty()){
            throw new BadRequestException("Book not found! id: " + rateDto.getBook_id());
        }
        for(Rate r : rateRepository.findAll()){
            if(r.getUser().getId().equals(userService.findByUsername(authenticatedUsername).getId())){
                if(r.getBook_id().equals(rateDto.getBook_id())){
                    throw new BadRequestException("Can't rate the same book!");
                }
            }
        }
        List<Integer> longList = new ArrayList<>();
        longList.add(rateDto.getRate());
        bookOptional.get().setRatings_list(longList);
        Rate rate = new Rate(bookOptional.get().getId(), authenticatedUser(), rateDto.getRate());
        for(Rate r : rateRepository.findAll()){
            for(Book b : bookService.findAll()){
                if(r.getBook_id().equals(b.getId())){
                    if(r.getBook_id().equals(rateDto.getBook_id())){
                        longList.add(r.getRate());
                        b.setRatings_list(longList);
                        bookService.save(b);
                    }
                }
            }
        }
        Book book = bookOptional.get();
        int countRate1 = Collections.frequency(book.getRatings_list(), 1);
        int countRate2 = Collections.frequency(book.getRatings_list(), 2);
        int countRate3 = Collections.frequency(book.getRatings_list(), 3);
        int countRate4 = Collections.frequency(book.getRatings_list(), 4);
        int countRate5 = Collections.frequency(book.getRatings_list(), 5);
        double sum = (countRate1)+(countRate2*2)+(countRate3*3)+(countRate4*4)+(countRate5*5);
        double weightedAverage = sum/(countRate1+countRate2+countRate3+countRate4+countRate5);
        book.setRatingsavg(weightedAverage);

        bookService.save(book);
        rateRepository.save(rate);
        return rate;
    }

}
