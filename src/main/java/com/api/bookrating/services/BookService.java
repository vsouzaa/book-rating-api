package com.api.bookrating.services;

import com.api.bookrating.dto.BookDto;
import com.api.bookrating.exception.BadRequestException;
import com.api.bookrating.model.Book;
import com.api.bookrating.repositories.BookRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {
    private BookRepository bookRepository;
    public List<Book> findAllByOrderByRatingsavg(){
        return bookRepository.findAllByOrderByRatingsavg();
    }
    public List<Book> findAllByOrderById(){
        return bookRepository.findAllByOrderById();
    }
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }
    @Transactional
    public Book save(Book book){
        return bookRepository.save(book);
    }
    @Transactional
    public Book saveDto(BookDto bookDto){
        if(existsByIsbn10(bookDto.getIsbn10())){
            throw new BadRequestException(bookDto.getIsbn10() + " already exists!");
        }
        Book book = new Book();
        BeanUtils.copyProperties(bookDto, book, "id");
        return bookRepository.save(book);
    }
    @Transactional
    public void update(Long id, BookDto bookDto){
        Optional<Book> bookOptional = findById(id);
        if(bookOptional.isEmpty()){
            throw new BadRequestException("Book not found! id: " + id);
        }
        Book book = bookOptional.get();
        List<Book> booksList = findAll();
        ArrayList<String> isbn10List = new ArrayList<>();
        booksList.forEach(b -> isbn10List.add(b.getIsbn10()));
        isbn10List.remove(book.getIsbn10());
        if (!Objects.equals(bookDto.getIsbn10(), book.getIsbn10())) {
            if(isbn10List.contains(bookDto.getIsbn10())){
                throw new BadRequestException(bookDto.getIsbn10() + " already exists!");
            }
        }
        BeanUtils.copyProperties(bookDto, book, "id");
        bookRepository.save(book);
    }

    public boolean existsByIsbn10(String isbn10){
        return bookRepository.existsByIsbn10(isbn10);
    }
    @Transactional
    public void delete(Long id) {
        Optional<Book> bookOptional = findById(id);
        if(bookOptional.isEmpty()){
            throw new BadRequestException("Book not found! id: " + id);
        }
        bookRepository.delete(bookOptional.get());
    }

    public void deleteAll(){
        bookRepository.deleteAll();
    }

}
