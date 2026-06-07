package com.pfa.app.modules.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerProfileRequestDto {
    private Long id;

    private Long userId;

    private long parentId;

    @NotNull( message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotBlank(message = "State of origin is required")
    private String originState;

    @NotBlank(message = "Nationality is required")
    private String nationality;

    private String playerPhone;

    @NotBlank(message = "Player address is required")
    private String playerAddress;

    private String playerEmail;

    @NotBlank(message = "Health concern is required")
    private String hasHealthConcern;

    private String healthConcernDescription;

}
