package br.org.cadi.people;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByType(PersonType type);
    Optional<Person> findByUserId(Long userId);
}
