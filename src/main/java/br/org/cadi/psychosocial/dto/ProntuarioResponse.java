package br.org.cadi.psychosocial.dto;

import br.org.cadi.people.dto.PersonResponse;
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
@Schema(description = "Response object representing a clinical note")
public class ProntuarioResponse {

    @Schema(example = "1")
    private Long id;

    private PersonResponse patient;

    @Schema(example = "O paciente apresenta melhora no comportamento social.")
    private String notes;

    private LocalDateTime createdAt;
}
