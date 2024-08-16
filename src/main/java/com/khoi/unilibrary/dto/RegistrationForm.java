package com.khoi.unilibrary.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RegistrationForm {

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Full name is required")
    private String fullName;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Confirm Password is required")
    private String confirmPassword;

//    @NotEmpty(message = "Confirm Password is required")
//    public String getConfirmPassword() {
//        return confirmPassword;
//    }
//
//    public void setConfirmPassword(@NotEmpty(message = "Confirm Password is required") String confirmPassword) {
//        this.confirmPassword = confirmPassword;
//    }
//
//    public @NotEmpty(message = "Password is required") String getPassword() {
//        return password;
//    }
//
//    public void setPassword(@NotEmpty(message = "Password is required") String password) {
//        this.password = password;
//    }
//
//    public @NotEmpty(message = "Username is required") String getUsername() {
//        return username;
//    }
//
//    public void setUsername(@NotEmpty(message = "Username is required") String username) {
//        this.username = username;
//    }
//
}

