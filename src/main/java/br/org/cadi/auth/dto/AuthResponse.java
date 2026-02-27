package br.org.cadi.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response object containing the JWT token")
public class AuthResponse {
    @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2FvLnNpbHZhIiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE1MTYyNDI2MjJ9...", description = "The generated JWT token for authentication")
    private String token;
}
