package com.api.bookrating.exception.details;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ValidationExceptionDetails {
    private String error;
    private int status;
    private String details;
    private String fields;
    private String fieldMessage;
    private LocalDateTime timeStamp;
}
