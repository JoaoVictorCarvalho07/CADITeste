package br.org.cadi.finance;

import br.org.cadi.people.Person;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "financial_transactions")
@Schema(description = "Represents a single financial entry (Income or Expense)")
public class FinancialTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Schema(example = "INCOME")
    private TransactionType type;

    @Column(name = "transaction_value")
    @Schema(example = "250.50", description = "Monetary value of the transaction")
    private BigDecimal value;

    @Schema(example = "2024-03-26T10:30:00")
    private LocalDateTime dateTime;

    @Schema(example = "Doação mensal de padrinho", description = "Short description of the transaction")
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Schema(example = "https://storage.cadi.org/receipts/rec-12345.pdf", description = "URL to the digital receipt or proof of transaction")
    private String receiptUrl;
}
