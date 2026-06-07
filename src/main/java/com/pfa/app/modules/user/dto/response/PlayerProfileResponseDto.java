package com.pfa.app.modules.user.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerProfileResponseDto {

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


    private Integer jerseyNumber;
    private String position;
    private String dominantFoot;
    private Double heightCm;
    private Double weightKg;

    // Flattened parent info
    private Long parentId;
    private String parentFullName;

    // Simplified collection of age groups (extracts just IDs or Names)
    private Set<String> ageGroupNames;
}