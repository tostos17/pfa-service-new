package com.pfa.app.modules.user.repositories;

import com.pfa.app.modules.user.entities.PlayerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlayerProfileRepository extends JpaRepository<PlayerProfile, Long> {
    // Finds all players managed under a specific parent's user ID
    List<PlayerProfile> findByParentId(Long parentId);
}