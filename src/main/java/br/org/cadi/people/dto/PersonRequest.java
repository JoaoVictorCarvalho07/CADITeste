package br.org.cadi.people.dto;

import br.org.cadi.people.PersonType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "Request object for creating or updating a person")
public class PersonRequest {

    @NotBlank(message = "Name is required")
    @Schema(example = "João da Silva")
    private String name;

    @Email(message = "Email should be valid")
    @Schema(example = "joao.silva@example.com")
    private String email;

    @Schema(example = "+55 11 99999-9999")
    private String phone;

    @Schema(example = "123.456.789-00")
    private String cpf;

    @Schema(example = "1990-05-15")
    private LocalDate birthDate;

    @Schema(example = "Rua das Flores")
    private String street;

    @Schema(example = "123")
    private String number;

    @Schema(example = "Apto 42")
    private String complement;

    @Schema(example = "Jardim Paulista")
    private String neighborhood;

    @Schema(example = "São Paulo")
    private String city;

    @Schema(example = "SP")
    private String state;

    @Schema(example = "01234-567")
    private String zipCode;

    @NotNull(message = "Person type is required")
    @Schema(example = "STUDENT")
    private PersonType type;

    @Schema(example = "1", description = "Optional user ID to link with this person")
    private Long userId;
}
