package br.org.cadi.academic.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for recording a student's attendance")
public class PresencaRequest {

    @NotNull(message = "Aula ID is required")
    @Schema(example = "1")
    private Long aulaId;

    @NotNull(message = "Student ID is required")
    @Schema(example = "1")
    private Long studentId;

    @Schema(example = "true")
    private boolean present;

    @Schema(example = "O aluno chegou com 15 minutos de atraso")
    private String observation;
}
