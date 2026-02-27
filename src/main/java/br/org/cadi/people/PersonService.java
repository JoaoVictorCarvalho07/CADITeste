package br.org.cadi.people;

import br.org.cadi.auth.User;
import br.org.cadi.auth.UserRepository;
import br.org.cadi.people.dto.PersonRequest;
import br.org.cadi.people.dto.PersonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repository;
    private final UserRepository userRepository;

    public List<PersonResponse> findByType(PersonType type) {
        return repository.findByType(type).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PersonResponse save(PersonRequest request) {
        Person person = mapToEntity(request);
        if (request.getUserId() != null) {
            userRepository.findById(request.getUserId()).ifPresent(person::setUser);
        }
        return mapToResponse(repository.save(person));
    }

    public Optional<PersonResponse> findById(Long id) {
        return repository.findById(id).map(this::mapToResponse);
    }

    public Optional<PersonResponse> update(Long id, PersonRequest request) {
        return repository.findById(id)
                .map(person -> {
                    person.setName(request.getName());
                    person.setEmail(request.getEmail());
                    person.setPhone(request.getPhone());
                    person.setCpf(request.getCpf());
                    person.setBirthDate(request.getBirthDate());
                    person.setStreet(request.getStreet());
                    person.setNumber(request.getNumber());
                    person.setComplement(request.getComplement());
                    person.setNeighborhood(request.getNeighborhood());
                    person.setCity(request.getCity());
                    person.setState(request.getState());
                    person.setZipCode(request.getZipCode());
                    person.setType(request.getType());
                    return mapToResponse(repository.save(person));
                });
    }

    public Optional<Person> findEntityById(Long id) {
        return repository.findById(id);
    }

    public Optional<Person> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    private PersonResponse mapToResponse(Person person) {
        return PersonResponse.builder()
                .id(person.getId())
                .name(person.getName())
                .email(person.getEmail())
                .phone(person.getPhone())
                .cpf(person.getCpf())
                .birthDate(person.getBirthDate())
                .city(person.getCity())
                .type(person.getType())
                .build();
    }

    private Person mapToEntity(PersonRequest request) {
        return Person.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .cpf(request.getCpf())
                .birthDate(request.getBirthDate())
                .street(request.getStreet())
                .number(request.getNumber())
                .complement(request.getComplement())
                .neighborhood(request.getNeighborhood())
                .city(request.getCity())
                .state(request.getState())
                .zipCode(request.getZipCode())
                .type(request.getType())
                .build();
    }
}
