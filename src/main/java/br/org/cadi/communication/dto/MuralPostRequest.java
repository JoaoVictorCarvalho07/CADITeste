package br.org.cadi.communication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for creating a mural post")
public class MuralPostRequest {

    @NotBlank(message = "Title is required")
    @Schema(example = "Novas Oficinas de Música")
    private String title;

    @NotBlank(message = "Content is required")
    @Schema(example = "Estão abertas as inscrições para as novas oficinas de violão e teclado.")
    private String content;

    @Schema(example = "https://cadi.org/images/musica.jpg")
    private String imageUrl;

    @Schema(description = "List of role names that can see this post")
    private Set<String> targetRoles;

    @Schema(description = "List of Turma IDs that can see this post")
    private Set<Long> targetTurmaIds;

    @Schema(example = "false")
    private boolean global;
}
