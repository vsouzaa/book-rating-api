package com.api.bookrating.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {
    @NotBlank(message = "name must not be blank")
    private String name;
    @NotBlank(message = "author must not be blank")
    private String author;
    @NotBlank(message = "isbn10 must not be blank")
    @Size(min = 10, max = 10)
    private String isbn10;
}
