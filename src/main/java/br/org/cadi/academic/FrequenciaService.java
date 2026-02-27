package br.org.cadi.academic;

import br.org.cadi.academic.dto.AulaRequest;
import br.org.cadi.academic.dto.AulaResponse;
import br.org.cadi.academic.dto.PresencaRequest;
import br.org.cadi.academic.dto.PresencaResponse;
import br.org.cadi.people.Person;
import br.org.cadi.people.PersonRepository;
import br.org.cadi.people.dto.PersonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FrequenciaService {

    private final AulaRepository aulaRepository;
    private final PresencaRepository presencaRepository;
    private final TurmaRepository turmaRepository;
    private final PersonRepository personRepository;

    public AulaResponse saveAula(AulaRequest request) {
        Aula aula = Aula.builder()
                .dateTime(request.getDateTime())
                .topic(request.getTopic())
                .build();
        turmaRepository.findById(request.getTurmaId()).ifPresent(aula::setTurma);
        return mapAulaToResponse(aulaRepository.save(aula));
    }

    public List<PresencaResponse> savePresencas(List<PresencaRequest> requests) {
        List<Presenca> presencas = requests.stream().map(request -> {
            Presenca presenca = Presenca.builder()
                    .present(request.isPresent())
                    .observation(request.getObservation())
                    .build();
            aulaRepository.findById(request.getAulaId()).ifPresent(presenca::setAula);
            personRepository.findById(request.getStudentId()).ifPresent(presenca::setStudent);
            return presenca;
        }).collect(Collectors.toList());

        return presencaRepository.saveAll(presencas).stream()
                .map(this::mapPresencaToResponse)
                .collect(Collectors.toList());
    }

    public List<PresencaResponse> findByStudentId(Long studentId) {
        return presencaRepository.findByStudentId(studentId).stream()
                .map(this::mapPresencaToResponse)
                .collect(Collectors.toList());
    }

    private AulaResponse mapAulaToResponse(Aula aula) {
        return AulaResponse.builder()
                .id(aula.getId())
                .turmaId(aula.getTurma() != null ? aula.getTurma().getId() : null)
                .dateTime(aula.getDateTime())
                .topic(aula.getTopic())
                .build();
    }

    private PresencaResponse mapPresencaToResponse(Presenca presenca) {
        return PresencaResponse.builder()
                .id(presenca.getId())
                .aulaId(presenca.getAula() != null ? presenca.getAula().getId() : null)
                .student(presenca.getStudent() != null ? mapPersonToResponse(presenca.getStudent()) : null)
                .present(presenca.isPresent())
                .observation(presenca.getObservation())
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
