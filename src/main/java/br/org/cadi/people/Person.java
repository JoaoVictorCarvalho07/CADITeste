package br.org.cadi.people;

import br.org.cadi.auth.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "people")
@Schema(description = "Represents a person in the system (Student, Professor, Donor, etc.)")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(example = "João da Silva", description = "Full name of the person")
    private String name;

    @Column(unique = true)
    @Schema(example = "joao.silva@example.com")
    private String email;

    @Schema(example = "+55 11 99999-9999")
    private String phone;

    @Column(unique = true)
    @Schema(example = "123.456.789-00", description = "Brazilian tax identification number")
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

    @Enumerated(EnumType.STRING)
    @Schema(example = "STUDENT")
    private PersonType type;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
