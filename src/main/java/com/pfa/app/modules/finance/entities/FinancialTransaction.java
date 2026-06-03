package com.pfa.app.modules.finance.entities;

import com.pfa.app.modules.term.entities.Term;
import com.pfa.app.modules.user.entities.PlayerProfile;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "financial_transactions")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class FinancialTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id")
    private Term term;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_profile_id")
    private PlayerProfile playerProfile;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType; // INFLOW, OUTFLOW

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "reference_number", unique = true)
    private String referenceNumber;

    private String description;
}
