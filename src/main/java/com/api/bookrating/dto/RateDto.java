package com.api.bookrating.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RateDto {
    @NotNull(message = "book_id must not be blank")
    private Long book_id;
    @NotNull(message = "rate must not be blank")
    @Max(5)
    private Integer rate;
}
