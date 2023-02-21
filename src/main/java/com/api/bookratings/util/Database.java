package com.api.bookratings.util;

import com.api.bookratings.model.Book;
import com.api.bookratings.model.User;
import com.api.bookratings.enums.Role;
import com.api.bookratings.repositories.BookRepository;
import com.api.bookratings.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Database {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner commandLineRunnerUsers (UserRepository userRepository) {
        return args -> {
            userRepository.save(new User(1L, "admin", passwordEncoder.encode("123"), Role.ADMIN));
            userRepository.save(new User(2L, "user", passwordEncoder.encode("123"), Role.USER));

        };
    }

    @Bean
    public CommandLineRunner commandLineRunnerBooks (BookRepository bookRepository){
        return args -> {
            bookRepository.save(new Book(1L, "The Hobbit", "J. R. R. Tolkien", "0261103342", 0, null));
            bookRepository.save(new Book(2L, "Mistborn: The Final Empire", "Brandon Sanderson", "0765350386", 0, null));
            bookRepository.save(new Book(3L, "Harry Potter and the Philosopher's Stone", "J.K. Rowling", "1594130000",  0, null));
            bookRepository.save(new Book(4L, "The Lightning Thief", "Rick Riordan", "0786856297",  0, null));
            bookRepository.save(new Book(5L, "A Game of Thrones: A Song of Ice and Fire", "George R. R. Martin", "0553103547",  0, null));
            bookRepository.save(new Book(6L, "The Name of the Wind", "Patrick Rothfuss", "0756404746",  0, null));
            bookRepository.save(new Book(7L, "The Lord of the Rings", "J. R. R. Tolkien", "0544273443",  0, null));
            bookRepository.save(new Book(8L, "The Lost Hero", "Rick Riordan", "1423113462",  0, null));
            bookRepository.save(new Book(9L, "Fire & Blood", "George R. R. Martin", "0593357531",  0, null));
            bookRepository.save(new Book(10L, "Dune", "Frank Herbert", "0340960191",  0, null));
        };
    }
}

