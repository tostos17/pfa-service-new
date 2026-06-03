package com.pfa.app.modules.term.repositories;

import com.pfa.app.modules.term.entities.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TermRepository extends JpaRepository<Term, Integer> {

    @Query("SELECT t FROM Term t WHERE t.isActive = true")
    Optional<Term> findActiveTerm();
}
