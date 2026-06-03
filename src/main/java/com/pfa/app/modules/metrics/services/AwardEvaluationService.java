package com.pfa.app.modules.metrics.services;

import com.pfa.app.modules.metrics.dto.AwardLeaderboardDto;
import com.pfa.app.modules.metrics.repositories.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AwardEvaluationService {

    private final AttendanceRepository attendanceRepository;

    public List<AwardLeaderboardDto> getIronManNominees(Integer termId) {
        List<AwardLeaderboardDto> leaderboard = attendanceRepository.getIronManLeaderboard(termId);

        // Filter out nominees with perfect metrics or top 5 exceptional performers
        return leaderboard.stream()
                .filter(player -> player.getAttendancePercentage() >= 90.0)
                .limit(5)
                .collect(Collectors.toList());
    }
}
