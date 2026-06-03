package com.pfa.app.modules.finance.services;

import com.pfa.app.modules.finance.dto.FinancialReportDto;
import com.pfa.app.modules.finance.entities.FinancialTransaction;
import com.pfa.app.modules.finance.repositories.FinancialTransactionRepository;
import com.pfa.app.modules.term.entities.Term;
import com.pfa.app.modules.term.repositories.TermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinancialReportingService {

    private final FinancialTransactionRepository transactionRepository;
    private final TermRepository termRepository;

    public FinancialReportDto generateTermReport(Integer termId) {
        Term term = termRepository.findById(termId)
                .orElseThrow(() -> new RuntimeException("Term not found."));

        List<FinancialTransaction> transactions = transactionRepository.findTransactionsByTerm(termId);
        return compileReport(term.getName(), transactions);
    }

    public FinancialReportDto generateDateRangeReport(String label, LocalDate startDate, LocalDate endDate) {
        List<FinancialTransaction> transactions = transactionRepository.findTransactionsInPeriod(startDate, endDate);
        return compileReport(label, transactions);
    }

    private FinancialReportDto compileReport(String label, List<FinancialTransaction> transactions) {
        BigDecimal totalInflow = BigDecimal.ZERO;
        BigDecimal totalOutflow = BigDecimal.ZERO;

        for (FinancialTransaction tx : transactions) {
            if ("INFLOW".equalsIgnoreCase(tx.getTransactionType())) {
                totalInflow = totalInflow.add(tx.getAmount());
            } else if ("OUTFLOW".equalsIgnoreCase(tx.getTransactionType())) {
                totalOutflow = totalOutflow.add(tx.getAmount());
            }
        }

        BigDecimal netProfit = totalInflow.subtract(totalOutflow);

        Map<String, BigDecimal> categoryBreakdown = transactions.stream()
                .collect(Collectors.groupingBy(
                        FinancialTransaction::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, FinancialTransaction::getAmount, BigDecimal::add)
                ));

        return new FinancialReportDto(label, totalInflow, totalOutflow, netProfit, categoryBreakdown);
    }
}
