package br.org.cadi.academic.dto;

import br.org.cadi.people.dto.PersonResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object representing a student's attendance")
public class PresencaResponse {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "1", description = "Aula ID")
    private Long aulaId;

    private PersonResponse student;

    @Schema(example = "true")
    private boolean present;

    @Schema(example = "O aluno chegou com 15 minutos de atraso")
    private String observation;
}
