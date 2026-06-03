package com.pfa.app.modules.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Map;

@Getter @Setter
@AllArgsConstructor
public class FinancialReportDto {
    private String reportPeriod; // e.g., "Term 1 2026", "May 2026"
    private BigDecimal totalInflow;
    private BigDecimal totalOutflow;
    private BigDecimal netProfit;
    private Map<String, BigDecimal> breakdownByCategory;
}
