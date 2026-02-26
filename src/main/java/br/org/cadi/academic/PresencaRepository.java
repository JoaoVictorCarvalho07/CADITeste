package br.org.cadi.academic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PresencaRepository extends JpaRepository<Presenca, Long> {
    List<Presenca> findByAulaId(Long aulaId);
    List<Presenca> findByStudentId(Long studentId);
}
