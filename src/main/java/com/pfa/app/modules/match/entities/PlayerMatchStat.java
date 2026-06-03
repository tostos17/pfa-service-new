package com.pfa.app.modules.match.entities;

import com.pfa.app.modules.user.entities.PlayerProfile;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "player_match_stats")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PlayerMatchStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fixture_id", nullable = false)
    private Fixture fixture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_profile_id", nullable = false)
    private PlayerProfile playerProfile;

    @Column(name = "minutes_played", nullable = false)
    private Integer minutesPlayed = 0;

    @Column(nullable = false)
    private Integer goals = 0;

    @Column(nullable = false)
    private Integer assists = 0;

    @Column(name = "yellow_cards", nullable = false)
    private Integer yellowCards = 0;

    @Column(name = "red_cards", nullable = false)
    // Values limited by game rules: usually max 1 red card per match
    private Integer redCards = 0;

    @Column(name = "clean_sheet")
    private Boolean cleanSheet = false;

    @Column(name = "coach_rating")
    private Double coachRating;
}
