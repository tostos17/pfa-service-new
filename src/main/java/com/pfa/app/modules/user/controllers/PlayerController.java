package com.pfa.app.modules.user.controllers;

import com.pfa.app.modules.user.service.PlayerCvService;
import com.pfa.app.modules.user.service.PlayerManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerManagementService playerManagementService;
    private final PlayerCvService playerCvService;

    @PostMapping("/{id}/groups/{groupId}")
    public ResponseEntity<String> assignToGroup(
            @PathVariable Long id,
            @PathVariable Integer groupId,
            @RequestParam(defaultValue = "false") boolean bypassAgeCheck) {
        playerManagementService.assignPlayerToGroup(id, groupId, bypassAgeCheck);
        return ResponseEntity.ok("Player assigned to group successfully.");
    }

    @GetMapping("/{id}/cv")
    public ResponseEntity<byte[]> downloadPlayerCv(@PathVariable Long id) {
        byte[] pdfContent = playerCvService.generatePlayerCvPdf(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=player_cv_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfContent);
    }
}
