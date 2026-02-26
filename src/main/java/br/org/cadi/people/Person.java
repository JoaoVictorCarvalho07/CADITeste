package br.org.cadi.people;

import br.org.cadi.auth.User;
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
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    private String phone;

    @Column(unique = true)
    private String cpf;

    private LocalDate birthDate;

    // Address fields
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String zipCode;

    @Enumerated(EnumType.STRING)
    private PersonType type;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
