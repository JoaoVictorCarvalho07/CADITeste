package br.org.cadi.people;

import br.org.cadi.auth.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repository;

    public List<Person> findByType(PersonType type) {
        return repository.findByType(type);
    }

    public Person save(Person person) {
        return repository.save(person);
    }

    public Optional<Person> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Person> update(Long id, Person personDetails) {
        return repository.findById(id)
                .map(person -> {
                    person.setName(personDetails.getName());
                    person.setEmail(personDetails.getEmail());
                    person.setPhone(personDetails.getPhone());
                    // Update other fields as needed
                    return repository.save(person);
                });
    }

    public Optional<Person> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }
}
