package br.org.cadi.academic.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object representing a class session")
public class AulaResponse {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "1", description = "Turma ID")
    private Long turmaId;

    @Schema(example = "2024-03-26T14:00:00")
    private LocalDateTime dateTime;

    @Schema(example = "Álgebra Linear e Matrizes")
    private String topic;
}
