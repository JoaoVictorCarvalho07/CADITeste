package br.org.cadi.psychosocial.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for creating a psychosocial clinical note")
public class ProntuarioRequest {

    @NotNull(message = "Patient ID is required")
    @Schema(example = "1")
    private Long patientId;

    @NotBlank(message = "Clinical notes are required")
    @Schema(example = "O paciente apresenta melhora no comportamento social e maior interesse nas atividades escolares.")
    private String notes;
}
