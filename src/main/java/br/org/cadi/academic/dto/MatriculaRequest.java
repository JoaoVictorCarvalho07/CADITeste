package br.org.cadi.academic.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for enrolling a student in a class")
public class MatriculaRequest {

    @NotNull(message = "Student ID is required")
    @Schema(example = "1")
    private Long studentId;

    @NotNull(message = "Turma ID is required")
    @Schema(example = "1")
    private Long turmaId;

    @Schema(example = "2024-02-01")
    private LocalDate enrollmentDate;

    @Schema(example = "ACTIVE")
    private String status;
}
