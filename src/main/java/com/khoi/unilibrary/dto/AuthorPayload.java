package com.khoi.unilibrary.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorPayload {

    private Integer id;

    private String firstName;

    private String lastName;

}