package br.org.cadi.academic;

import br.org.cadi.academic.dto.TurmaRequest;
import br.org.cadi.academic.dto.TurmaResponse;
import br.org.cadi.people.Person;
import br.org.cadi.people.PersonRepository;
import br.org.cadi.people.dto.PersonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TurmaService {

    private final TurmaRepository repository;
    private final PersonRepository personRepository;

    public List<TurmaResponse> findAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public TurmaResponse save(TurmaRequest request) {
        Turma turma = mapToEntity(request);
        if (request.getProfessorId() != null) {
            personRepository.findById(request.getProfessorId()).ifPresent(turma::setProfessor);
        }
        return mapToResponse(repository.save(turma));
    }

    public Optional<TurmaResponse> findById(Long id) {
        return repository.findById(id).map(this::mapToResponse);
    }

    private TurmaResponse mapToResponse(Turma turma) {
        return TurmaResponse.builder()
                .id(turma.getId())
                .name(turma.getName())
                .schedule(turma.getSchedule())
                .active(turma.isActive())
                .professor(turma.getProfessor() != null ? mapPersonToResponse(turma.getProfessor()) : null)
                .build();
    }

    private Turma mapToEntity(TurmaRequest request) {
        return Turma.builder()
                .name(request.getName())
                .description(request.getDescription())
                .schedule(request.getSchedule())
                .active(request.isActive())
                .build();
    }

    private PersonResponse mapPersonToResponse(Person person) {
        return PersonResponse.builder()
                .id(person.getId())
                .name(person.getName())
                .email(person.getEmail())
                .type(person.getType())
                .build();
    }
}
