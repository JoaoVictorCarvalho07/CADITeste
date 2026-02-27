package br.org.cadi.academic.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for registering a class session")
public class AulaRequest {

    @NotNull(message = "Turma ID is required")
    @Schema(example = "1")
    private Long turmaId;

    @Schema(example = "2024-03-26T14:00:00")
    private LocalDateTime dateTime;

    @Schema(example = "Álgebra Linear e Matrizes")
    private String topic;
}
