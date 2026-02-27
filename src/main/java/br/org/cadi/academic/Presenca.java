package br.org.cadi.academic;

import br.org.cadi.people.Person;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Represents a student's attendance in a specific class session")
public class Presenca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aula_id", nullable = false)
    private Aula aula;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Person student;

    @Schema(example = "true")
    private boolean present;

    @Schema(example = "O aluno chegou com 15 minutos de atraso")
    private String observation;
}
