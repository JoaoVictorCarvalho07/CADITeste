package br.org.cadi.academic.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for creating or updating a class")
public class TurmaRequest {

    @NotBlank(message = "Name is required")
    @Schema(example = "Matemática Básica")
    private String name;

    @Schema(example = "Introdução aos conceitos fundamentais de matemática")
    private String description;

    @Schema(example = "Segunda e Quarta, 14:00-16:00")
    private String schedule;

    @Schema(example = "1", description = "ID of the professor in charge")
    private Long professorId;

    @Schema(example = "true")
    private boolean active;
}
