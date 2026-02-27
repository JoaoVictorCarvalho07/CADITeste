package br.org.cadi.psychosocial;

import br.org.cadi.people.Person;
import br.org.cadi.people.PersonRepository;
import br.org.cadi.people.dto.PersonResponse;
import br.org.cadi.psychosocial.dto.ProntuarioRequest;
import br.org.cadi.psychosocial.dto.ProntuarioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PsychosocialService {

    private final ProntuarioRepository repository;
    private final PersonRepository personRepository;

    @Transactional
    public ProntuarioResponse createProntuario(ProntuarioRequest request, Long psychologistUserId) {
        Person psychologist = personRepository.findByUserId(psychologistUserId)
                .orElseThrow(() -> new RuntimeException("Logged in user is not registered as a Person"));

        Person patient = personRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Prontuario prontuario = Prontuario.builder()
                .patient(patient)
                .psychologist(psychologist)
                .notes(request.getNotes())
                .createdAt(LocalDateTime.now())
                .build();

        return mapToResponse(repository.save(prontuario));
    }

    public List<ProntuarioResponse> getPatientHistory(Long patientId) {
        return repository.findByPatientId(patientId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ProntuarioResponse mapToResponse(Prontuario prontuario) {
        return ProntuarioResponse.builder()
                .id(prontuario.getId())
                .patient(mapPersonToResponse(prontuario.getPatient()))
                .notes(prontuario.getNotes())
                .createdAt(prontuario.getCreatedAt())
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
