package com.pfa.app.modules.match.entities;

import com.pfa.app.modules.term.entities.Term;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fixtures")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Fixture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id", nullable = false)
    private Term term;

    @Column(name = "opponent_name", nullable = false)
    private String opponentName;

    @Column(name = "match_date", nullable = false)
    private LocalDateTime matchDate;

    @Column(nullable = false)
    private String venue; // Home, Away

    @Column(nullable = false)
    private String status; // SCHEDULED, PLAYED, CANCELLED

    @Column(name = "academy_score")
    private Integer academyScore = 0;

    @Column(name = "opponent_score")
    private Integer opponentScore = 0;

    private String notes;
}
