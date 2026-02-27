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
@Schema(description = "Response object representing a mural post")
public class MuralPostResponse {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Novas Oficinas de Música")
    private String title;

    @Schema(example = "Estão abertas as inscrições para as novas oficinas de violão e teclado.")
    private String content;

    @Schema(example = "https://cadi.org/images/musica.jpg")
    private String imageUrl;

    private LocalDateTime createdAt;

    @Schema(example = "Secretaria")
    private String authorName;
}
