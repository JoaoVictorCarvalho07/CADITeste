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
@Schema(description = "Response object representing a class")
public class TurmaResponse {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Matemática Básica")
    private String name;

    @Schema(example = "Segunda e Quarta, 14:00-16:00")
    private String schedule;

    private PersonResponse professor;

    @Schema(example = "true")
    private boolean active;
}
