package br.org.cadi.psychosocial;

import br.org.cadi.people.Person;
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
@Table(name = "prontuarios")
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Person patient;

    @ManyToOne
    @JoinColumn(name = "psychologist_id", nullable = false)
    private Person psychologist;

    @Column(nullable = false, length = 4000)
    private String notes;

    private LocalDateTime createdAt;

    private String confidentialCode; // Optional masking for high sensitivity
}
