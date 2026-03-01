package br.org.cadi.academic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    List<Turma> findByProfessorUserId(Long userId);
}
