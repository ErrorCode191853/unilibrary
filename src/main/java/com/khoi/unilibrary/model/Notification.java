package com.khoi.unilibrary.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class Notification {
    private String message;
    private LocalDateTime timestamp;
}