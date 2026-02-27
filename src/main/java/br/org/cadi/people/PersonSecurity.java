package br.org.cadi.people;

import br.org.cadi.auth.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("personSecurity")
@RequiredArgsConstructor
public class PersonSecurity {

    private final PersonService personService;

    public boolean isSelf(Long personId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User user) {
            return personService.findEntityById(personId)
                    .map(person -> person.getUser() != null && person.getUser().getId().equals(user.getId()))
                    .orElse(false);
        }
        return false;
    }
}
