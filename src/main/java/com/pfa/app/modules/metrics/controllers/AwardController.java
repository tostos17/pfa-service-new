package com.pfa.app.modules.metrics.controllers;

import com.pfa.app.modules.metrics.dto.AwardLeaderboardDto;
import com.pfa.app.modules.metrics.services.AwardEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/awards")
@RequiredArgsConstructor
public class AwardController {

    private final AwardEvaluationService awardEvaluationService;

    @GetMapping("/term/{termId}/iron-man")
    public ResponseEntity<List<AwardLeaderboardDto>> getIronManLeaderboard(@PathVariable Integer termId) {
        return ResponseEntity.ok(awardEvaluationService.getIronManNominees(termId));
    }
}
