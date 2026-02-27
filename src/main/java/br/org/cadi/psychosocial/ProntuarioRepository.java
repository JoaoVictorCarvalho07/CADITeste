package br.org.cadi.psychosocial;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
    List<Prontuario> findByPatientId(Long patientId);
}
