package br.org.cadi.finance.dto;

import br.org.cadi.finance.TransactionType;
import br.org.cadi.people.dto.PersonResponse;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Response object representing a financial transaction")
public class FinancialTransactionResponse {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "INCOME")
    private TransactionType type;

    @Schema(example = "250.50")
    private BigDecimal value;

    @Schema(example = "2024-03-26T10:30:00")
    private LocalDateTime dateTime;

    @Schema(example = "Doação mensal de padrinho")
    private String description;

    private ProjectResponse project;

    private PersonResponse person;

    @Schema(example = "https://storage.cadi.org/receipts/rec-12345.pdf")
    private String receiptUrl;
}
