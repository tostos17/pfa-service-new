package com.pfa.app.modules.match.repositories;

import com.pfa.app.modules.match.dto.PerformanceLeaderboardDto;
import com.pfa.app.modules.match.entities.PlayerMatchStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlayerMatchStatRepository extends JpaRepository<PlayerMatchStat, Long> {

    @Query("SELECT new com.pfa.app.modules.match.dto.PerformanceLeaderboardDto(" +
            "p.id, u.firstName, u.lastName, " +
            "SUM(m.goals), " +
            "SUM(m.assists), " +
            "COUNT(m.id), " +
            "AVG(m.coachRating)) " +
            "FROM PlayerMatchStat m " +
            "JOIN m.playerProfile p " +
            "JOIN p.user u " +
            "WHERE m.fixture.term.id = :termId " +
            "GROUP BY p.id, u.firstName, u.lastName " +
            "ORDER BY SUM(m.goals) DESC, SUM(m.assists) DESC")
    List<PerformanceLeaderboardDto> getTopScorersLeaderboard(@Param("termId") Integer termId);
}
