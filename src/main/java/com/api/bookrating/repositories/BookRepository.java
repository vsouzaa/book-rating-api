package com.api.bookrating.repositories;

import com.api.bookrating.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository <Book, Long> {
    boolean existsByIsbn10(String isbn10);
    List<Book> findAllByOrderByRatingsavg();
    List<Book> findAllByOrderById();
}
