package com.pfa.app.modules.match.services;

import com.pfa.app.modules.match.dto.PerformanceLeaderboardDto;
import com.pfa.app.modules.match.repositories.PlayerMatchStatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchPerformanceService {

    private final PlayerMatchStatRepository matchStatRepository;

    public List<PerformanceLeaderboardDto> getGoldenBootNominees(Integer termId) {
        List<PerformanceLeaderboardDto> leaderboard = matchStatRepository.getTopScorersLeaderboard(termId);

        // Take the top 5 goalscorers of the term
        return leaderboard.stream()
                .limit(5)
                .collect(Collectors.toList());
    }
}
