package br.org.cadi.communication;

import br.org.cadi.auth.User;
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
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String message;

    private String type; // e.g., "MURAL", "ACADEMIC", "FINANCE"

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
}
