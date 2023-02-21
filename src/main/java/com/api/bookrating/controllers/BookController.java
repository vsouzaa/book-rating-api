package com.api.bookrating.controllers;

import com.api.bookrating.dto.BookDto;
import com.api.bookrating.exception.BadRequestException;
import com.api.bookrating.model.Book;
import com.api.bookrating.services.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@AllArgsConstructor
public class BookController {

    private  BookService bookService;

    @GetMapping(path = "/books")
    public List<Book> findAll(){
        return bookService.findAllByOrderById();
    }

    @GetMapping(path = "/books/releases")
    public List<Book> findLastFive(){
        List<Book> bookList = bookService.findAllByOrderById();
        Collections.reverse(bookList);
        return bookList.subList(0,5);
    }

    @GetMapping(path = "/books/{id}")
    public Book findById(@PathVariable Long id){
        Optional<Book> bookOptional = bookService.findById(id);
        if(bookOptional.isEmpty()){
            throw new BadRequestException("Book not found");
        }
        return bookOptional.get();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/books/add")
    public String save(@RequestBody @Valid BookDto bookDto){
        bookService.saveDto(bookDto);
        return bookDto.getName() + " book created";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(path = "/books/delete-all")
    public String deleteAll(){
        bookService.deleteAll();
        return "All books deleted";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(path = "/books/delete/{id}")
    public String delete(@PathVariable Long id){
        bookService.delete(id);
        return "Book deleted! id: " + id;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(path = "/books/update/{id}")
    public String updateBook(@PathVariable Long id, @RequestBody @Valid BookDto bookDto){
        bookService.update(id, bookDto);
        return bookDto.getName() + " updated!";
    }
}
