package br.org.cadi.academic;

import br.org.cadi.people.Person;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "turmas")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String schedule; // e.g., "Mon/Wed 14:00-16:00"

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Person professor;

    private boolean active;
}
