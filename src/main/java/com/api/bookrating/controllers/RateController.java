package com.api.bookrating.controllers;

import com.api.bookrating.dto.RateDto;
import com.api.bookrating.model.Book;
import com.api.bookrating.model.Rate;
import com.api.bookrating.services.BookService;
import com.api.bookrating.services.RateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
public class RateController {
    private RateService rateService;
    private BookService bookService;

    @GetMapping(path = "/top")
    public List<Book> topRatings(){
        List<Book> bookList = bookService.findAllByOrderByRatingsavg();
        Collections.reverse(bookList);
        return bookList.subList(0,5);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PostMapping(path = "/rate")
    public Rate save(@RequestBody @Valid RateDto rateDto){
        return rateService.save(rateDto);
    }
}
