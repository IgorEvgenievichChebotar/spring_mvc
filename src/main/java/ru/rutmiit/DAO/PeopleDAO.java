package ru.rutmiit.DAO;

import org.springframework.stereotype.Component;
import ru.rutmiit.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PeopleDAO {
    private static int PEOPLE_COUNT = 0;
    List<Person> people;

    {
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT, "Igor"));
        people.add(new Person(++PEOPLE_COUNT, "Sasha"));
        people.add(new Person(++PEOPLE_COUNT, "Max"));
        people.add(new Person(++PEOPLE_COUNT, "Kate"));
    }

    public List<Person> index(){
        return people;
    }

    public Person show(int id){
        return people.stream().filter(person -> person.getId()==id).findAny().orElse(null);
    }

    public void save(Person person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person person) {
        Person personThatShouldUpdates = show(id);

        personThatShouldUpdates.setName(person.getName());
    }

    public void delete(int id) {
        people.removeIf(person -> person.getId()==id);
    }
}
