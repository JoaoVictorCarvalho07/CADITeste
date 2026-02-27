package br.org.cadi.finance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for creating or updating a project")
public class ProjectRequest {

    @NotBlank(message = "Project name is required")
    @Schema(example = "Projeto Inclusão Digital")
    private String name;

    @Schema(example = "Fornecimento de aulas de informática para crianças da comunidade")
    private String description;

    @Schema(example = "50000.00")
    private BigDecimal financialGoal;

    @Schema(example = "true")
    private boolean active;
}
