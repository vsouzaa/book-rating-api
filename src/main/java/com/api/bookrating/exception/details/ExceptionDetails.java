package com.api.bookrating.exception.details;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Builder
@Data
public class ExceptionDetails {
    private String error;
    private int status;
    private String details;
    private LocalDateTime timeStamp;
}
