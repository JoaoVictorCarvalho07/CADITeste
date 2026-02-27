package br.org.cadi.academic.dto;

import br.org.cadi.people.dto.PersonResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object representing an enrollment")
public class MatriculaResponse {

    @Schema(example = "1")
    private Long id;

    private PersonResponse student;

    private TurmaResponse turma;

    @Schema(example = "2024-02-01")
    private LocalDate enrollmentDate;

    @Schema(example = "ACTIVE")
    private String status;
}
