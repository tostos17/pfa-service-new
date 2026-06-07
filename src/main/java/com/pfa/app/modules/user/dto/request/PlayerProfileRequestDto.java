package com.pfa.app.modules.user.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public class PlayerProfileRequestDto {
    private Long id;
    @NotNull( message = "User ID is required")
    private Long userId;

    @NotNull( message = "Firstname is required")
    private String firstname;

    @NotNull( message = "Middlename is required")
    private String middlename;

    @NotNull( message = "Lastname is required")
    private String lastname;

    @NotNull( message = "Date of birth is required")
    private LocalDate dateOfBirth;

}
