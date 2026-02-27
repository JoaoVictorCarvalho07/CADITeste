package br.org.cadi.academic;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aulas")
@Schema(description = "Represents a specific occurrence of a class session")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @Schema(example = "2024-03-26T14:00:00")
    private LocalDateTime dateTime;

    @Schema(example = "Álgebra Linear e Matrizes", description = "Topic covered in the class")
    private String topic;
}
