package com.pfa.app.modules.match.controllers;

import com.pfa.app.modules.match.dto.PerformanceLeaderboardDto;
import com.pfa.app.modules.match.services.MatchPerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/match-analytics")
@RequiredArgsConstructor
public class MatchAnalyticsController {

    private final MatchPerformanceService matchPerformanceService;

    @GetMapping("/term/{termId}/golden-boot")
    public ResponseEntity<List<PerformanceLeaderboardDto>> getGoldenBootLeaderboard(@PathVariable Integer termId) {
        return ResponseEntity.ok(matchPerformanceService.getGoldenBootNominees(termId));
    }
}
