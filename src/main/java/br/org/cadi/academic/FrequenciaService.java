package br.org.cadi.academic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FrequenciaService {

    private final AulaRepository aulaRepository;
    private final PresencaRepository presencaRepository;

    public Aula saveAula(Aula aula) {
        return aulaRepository.save(aula);
    }

    public List<Presenca> savePresencas(List<Presenca> presencas) {
        return presencaRepository.saveAll(presencas);
    }

    public List<Presenca> findByStudentId(Long studentId) {
        return presencaRepository.findByStudentId(studentId);
    }
}
