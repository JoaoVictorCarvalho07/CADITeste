package br.org.cadi.academic;

import br.org.cadi.people.Person;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "presencas")
public class Presenca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aula_id", nullable = false)
    private Aula aula;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Person student;

    private boolean present;

    private String observation;
}
