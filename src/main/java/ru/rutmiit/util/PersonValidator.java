package ru.rutmiit.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.rutmiit.DAO.PeopleDAO;
import ru.rutmiit.models.Person;

@Component
public class PersonValidator implements Validator {

    private final PeopleDAO peopleDAO;

    @Autowired
    public PersonValidator(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Person.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if(peopleDAO.show(person.getEmail()).isPresent())
            errors.rejectValue("email", "", "Email is already taken");
    }
}
