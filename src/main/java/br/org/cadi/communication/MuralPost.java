package br.org.cadi.communication;

import br.org.cadi.academic.Turma;
import br.org.cadi.auth.Role;
import br.org.cadi.auth.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mural_posts")
public class MuralPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String content;

    private String imageUrl;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToMany
    @JoinTable(
            name = "mural_post_roles",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> targetRoles;

    @ManyToMany
    @JoinTable(
            name = "mural_post_turmas",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "turma_id")
    )
    private Set<Turma> targetTurmas;

    private boolean global;
}
