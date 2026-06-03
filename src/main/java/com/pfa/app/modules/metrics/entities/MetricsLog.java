package com.pfa.app.modules.metrics.entities;

import com.pfa.app.modules.term.entities.Term;
import com.pfa.app.modules.user.entities.PlayerProfile;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "metrics_logs")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MetricsLog {

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

    @Column(name = "discipline_score")
    private Integer disciplineScore;

    @Column(name = "work_ethic_score")
    private Integer workEthicScore;

    @Column(name = "leadership_score")
    private Integer leadershipScore;

    @Column(name = "coach_feedback")
    private String coachFeedback;
}
