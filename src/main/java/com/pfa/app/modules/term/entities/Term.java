package com.pfa.app.modules.term.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "terms")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name; // e.g., 'Term 1 2026'

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "academic_year", nullable = false, length = 9)
    private String academicYear; // e.g., '2025/2026'

    @Column(name = "is_active")
    private Boolean isActive = false;
}
