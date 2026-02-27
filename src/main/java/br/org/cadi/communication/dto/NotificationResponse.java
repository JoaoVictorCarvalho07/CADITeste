package br.org.cadi.communication.dto;

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
@Schema(description = "Response object representing a notification for a user")
public class NotificationResponse {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Reunião de Pais")
    private String title;

    @Schema(example = "A reunião de pais e mestres ocorrerá nesta sexta-feira às 19:00.")
    private String message;

    @Schema(example = "MURAL")
    private String type;

    private LocalDateTime createdAt;

    @Schema(example = "false")
    private boolean read;
}
