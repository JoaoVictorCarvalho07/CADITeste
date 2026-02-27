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
@Table(name = "turmas")
@Schema(description = "Represents a class or group of students")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(example = "Matemática Básica", description = "Name of the class/subject")
    private String name;

    @Schema(example = "Introdução aos conceitos fundamentais de matemática")
    private String description;

    @Schema(example = "Segunda e Quarta, 14:00-16:00", description = "Class schedule")
    private String schedule;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Person professor;

    @Schema(example = "true")
    private boolean active;
}
