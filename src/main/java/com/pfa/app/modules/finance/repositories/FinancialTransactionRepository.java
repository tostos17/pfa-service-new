package com.pfa.app.modules.finance.repositories;

import com.pfa.app.modules.finance.entities.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {

    @Query("SELECT t FROM FinancialTransaction t WHERE t.transactionDate BETWEEN :startDate AND :endDate")
    List<FinancialTransaction> findTransactionsInPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT t FROM FinancialTransaction t WHERE t.term.id = :termId")
    List<FinancialTransaction> findTransactionsByTerm(@Param("termId") Integer termId);
}
