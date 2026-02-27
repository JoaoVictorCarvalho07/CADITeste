package br.org.cadi.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request object for user authentication")
public class AuthRequest {

    @NotBlank(message = "Username is required")
    @Schema(example = "joao.silva", description = "The unique username of the user")
    private String username;

    @NotBlank(message = "Password is required")
    @Schema(example = "P@ssw0rd123", description = "The user's password")
    private String password;
}
