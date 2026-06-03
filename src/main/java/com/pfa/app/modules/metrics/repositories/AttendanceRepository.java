package com.pfa.app.modules.metrics.repositories;

import com.pfa.app.modules.metrics.dto.AwardLeaderboardDto;
import com.pfa.app.modules.metrics.entities.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("SELECT new com.pfa.app.modules.metrics.dto.AwardLeaderboardDto(" +
            "p.id, u.firstName, u.lastName, " +
            "COUNT(a.id), " +
            "SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN a.status = 'PRESENT' AND a.isPunctual = true THEN 1 ELSE 0 END), " +
            "(SUM(CASE WHEN a.status = 'PRESENT' THEN 1.0 ELSE 0.0 END) / COUNT(a.id)) * 100.0) " +
            "FROM Attendance a " +
            "JOIN a.playerProfile p " +
            "JOIN p.user u " +
            "WHERE a.term.id = :termId " +
            "GROUP BY p.id, u.firstName, u.lastName " +
            "ORDER BY SUM(CASE WHEN a.status = 'PRESENT' AND a.isPunctual = true THEN 1 ELSE 0 END) DESC, " +
            "(SUM(CASE WHEN a.status = 'PRESENT' THEN 1.0 ELSE 0.0 END) / COUNT(a.id)) * 100.0 DESC")
    List<AwardLeaderboardDto> getIronManLeaderboard(@Param("termId") Integer termId);
}
