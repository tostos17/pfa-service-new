package com.pfa.app.modules.match.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class PerformanceLeaderboardDto {
    private Long playerProfileId;
    private String firstName;
    private String lastName;
    private Long totalGoals;
    private Long totalAssists;
    private Long matchesPlayed;
    private Double averageCoachRating;
}
