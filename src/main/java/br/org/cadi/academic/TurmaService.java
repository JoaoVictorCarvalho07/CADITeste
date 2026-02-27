package br.org.cadi.academic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TurmaService {

    private final TurmaRepository repository;

    public List<Turma> findAll() {
        return repository.findAll();
    }

    public Turma save(Turma turma) {
        return repository.save(turma);
    }

    public Optional<Turma> findById(Long id) {
        return repository.findById(id);
    }
}
