package com.pfa.app.modules.metrics.entities;

import com.pfa.app.modules.term.entities.Term;
import com.pfa.app.modules.user.entities.PlayerProfile;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "attendance")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_profile_id", nullable = false)
    private PlayerProfile playerProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id", nullable = false)
    private Term term;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String status; // PRESENT, ABSENT, EXCUSED

    @Column(name = "is_punctual")
    private Boolean isPunctual = true;

    private String notes;
}
