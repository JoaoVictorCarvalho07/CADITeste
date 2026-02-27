package br.org.cadi.communication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for sending a notification")
public class NotificationRequest {

    @NotBlank(message = "Title is required")
    @Schema(example = "Reunião de Pais")
    private String title;

    @NotBlank(message = "Message is required")
    @Schema(example = "A reunião de pais e mestres ocorrerá nesta sexta-feira às 19:00.")
    private String message;

    @Schema(example = "MURAL")
    private String type;

    @Schema(description = "List of user IDs to receive the notification")
    private List<Long> recipientIds;
}
