package br.org.cadi.finance;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
@Schema(description = "Represents a social or educational project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(example = "Projeto Inclusão Digital", description = "Name of the project")
    private String name;

    @Schema(example = "Fornecimento de aulas de informática para crianças da comunidade")
    private String description;

    @Schema(example = "50000.00", description = "Financial goal for the project")
    private BigDecimal financialGoal;

    @Schema(example = "true")
    private boolean active;
}
