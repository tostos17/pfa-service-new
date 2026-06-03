package com.pfa.app.modules.finance.controller;

import com.pfa.app.modules.finance.dto.FinancialReportDto;
import com.pfa.app.modules.finance.services.FinancialReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/finance")
@RequiredArgsConstructor
public class FinanceController {

    private final FinancialReportingService reportingService;

    @GetMapping("/reports/term/{termId}")
    public ResponseEntity<FinancialReportDto> getTermReport(@PathVariable Integer termId) {
        return ResponseEntity.ok(reportingService.generateTermReport(termId));
    }

    @GetMapping("/reports/range")
    public ResponseEntity<FinancialReportDto> getRangeReport(
            @RequestParam String label,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reportingService.generateDateRangeReport(label, startDate, endDate));
    }
}
