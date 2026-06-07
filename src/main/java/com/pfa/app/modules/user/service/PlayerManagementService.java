package com.pfa.app.modules.user.service;

import com.pfa.app.modules.user.entities.AgeGroup;
import com.pfa.app.modules.user.entities.PlayerProfile;
import com.pfa.app.modules.user.repositories.AgeGroupRepository;
import com.pfa.app.modules.user.repositories.PlayerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerManagementService {

    private final PlayerProfileRepository playerRepository;
    private final AgeGroupRepository ageGroupRepository;

    public ResponseEntity<List<PlayerProfile>> getAllPlayers(String search) {
        if (search != null && !search.trim().isEmpty()) {
            return ResponseEntity.ok(playerRepository.searchPlayers(search));
        }
        return ResponseEntity.ok(playerRepository.findAll());
    }

    public PlayerProfile onboardPlayer(PlayerProfile profile) {
        return playerRepository.save(profile);
    }

    @Transactional
    public void assignPlayerToGroup(Long playerProfileId, Integer ageGroupId, boolean bypassAgeCheck) {
        PlayerProfile player = playerRepository.findById(playerProfileId)
                .orElseThrow(() -> new RuntimeException("Player profile not found."));
        AgeGroup group = ageGroupRepository.findById(ageGroupId)
                .orElseThrow(() -> new RuntimeException("Age group not found."));

        if (!bypassAgeCheck) {
            int age = Period.between(player.getDateOfBirth(), LocalDate.now()).getYears();
            if (age < group.getMinAge() || age > group.getMaxAge()) {
                throw new IllegalArgumentException("Player age (" + age + ") does not meet criteria for group " + group.getName());
            }
        }

        player.getAgeGroups().add(group);
        playerRepository.save(player);
    }
}
