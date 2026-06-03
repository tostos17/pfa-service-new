package com.pfa.app.modules.user.repositories;

import com.pfa.app.modules.user.entities.AgeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AgeGroupRepository extends JpaRepository<AgeGroup, Integer> {

    // Finds an age category by its name designation (e.g., "U10", "U13")
    Optional<AgeGroup> findByName(String name);

    // Custom query to find groups a player is age-eligible for based on their calculated age
    @Query("SELECT g FROM AgeGroup g WHERE :age BETWEEN g.minAge AND g.maxAge")
    java.util.List<AgeGroup> findEligibleGroupsForAge(@Param("age") Integer age);
}
