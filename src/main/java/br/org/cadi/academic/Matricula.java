package br.org.cadi.academic;

import br.org.cadi.people.Person;
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
@Table(name = "matriculas")
@Schema(description = "Represents a student's enrollment in a class")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Person student;

    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @Schema(example = "2024-02-01")
    private LocalDate enrollmentDate;

    @Schema(example = "ACTIVE")
    private String status;
}
