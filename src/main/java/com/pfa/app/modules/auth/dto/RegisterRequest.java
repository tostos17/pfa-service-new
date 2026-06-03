package com.pfa.app.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String password_hash; // Maps to the frontend property name
    private String firstName;
    private String lastName;
    private Long roleId;

}