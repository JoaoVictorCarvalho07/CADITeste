package br.org.cadi.finance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object representing a project")
public class ProjectResponse {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Projeto Inclusão Digital")
    private String name;

    @Schema(example = "50000.00")
    private BigDecimal financialGoal;

    @Schema(example = "true")
    private boolean active;
}
