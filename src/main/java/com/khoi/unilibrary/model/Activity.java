package com.khoi.unilibrary.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SecondaryRow;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class Activity {
    private String description;
    private LocalDateTime timestamp;
}
