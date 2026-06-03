package com.pfa.app.modules.metrics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AwardLeaderboardDto {
    private Long playerProfileId;
    private String firstName;
    private String lastName;
    private Long totalSessions;
    private Long sessionsAttended;
    private Long punctualityCount;
    private Double attendancePercentage;
}
