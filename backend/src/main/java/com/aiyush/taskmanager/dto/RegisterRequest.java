package com.aiyush.taskmanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

        @NotBlank
        private String username;
        @NotBlank
        private String password;
        @Email
        private String email;

}
