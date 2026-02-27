package br.org.cadi.finance.dto;

import br.org.cadi.finance.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "Request object for registering a financial transaction")
public class FinancialTransactionRequest {

    @NotNull(message = "Transaction type is required")
    @Schema(example = "INCOME")
    private TransactionType type;

    @NotNull(message = "Value is required")
    @Schema(example = "250.50")
    private BigDecimal value;

    @Schema(example = "2024-03-26T10:30:00")
    private LocalDateTime dateTime;

    @Schema(example = "Doação mensal de padrinho")
    private String description;

    @Schema(example = "1", description = "ID of the linked project")
    private Long projectId;

    @Schema(example = "1", description = "ID of the person involved (donor/student)")
    private Long personId;

    @Schema(example = "https://storage.cadi.org/receipts/rec-12345.pdf")
    private String receiptUrl;
}
