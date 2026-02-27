package br.org.cadi.academic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AulaRepository extends JpaRepository<Aula, Long> {
    List<Aula> findByTurmaId(Long turmaId);
}
