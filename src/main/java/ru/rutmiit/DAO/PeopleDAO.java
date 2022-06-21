package ru.rutmiit.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.rutmiit.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class PeopleDAO {
    private static int PEOPLE_COUNT = 0;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PeopleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public Optional<Person> show(String email){
        return jdbcTemplate.query("SELECT * FROM Person WHERE email=?",
                        new Object[]{email},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO Person(name, email, age, address) VALUES(?, ?, ?, ?)",
                person.getName(), person.getEmail(), person.getAge(), person.getAddress());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=?, address=? WHERE id=?",
                person.getName(), person.getAge(), person.getEmail(), person.getAddress(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public void testMultipleUpdate() {
        List<Person> people = new ArrayList<>(create1000people());

        long before = System.currentTimeMillis();

        for (Person person : people) {
            jdbcTemplate.update("INSERT INTO Person(name, email, age, address) VALUES(?, ?, ?, ?)",
                    person.getName(),
                    person.getEmail(),
                    person.getAge(),
                    person.getAddress());
        }

        long after = System.currentTimeMillis();

        System.out.println("without batch " + (after-before));
    }

    public void testWithBatch() {
        List<Person> people = new ArrayList<>(create1000people());

        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO Person(name, email, age, address) VALUES(?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, people.get(i).getName());
                        ps.setString(2, people.get(i).getEmail());
                        ps.setInt(3, people.get(i).getAge());
                        ps.setString(4, people.get(i).getAddress());
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });

        long after = System.currentTimeMillis();

        System.out.println("with batch " + (after-before));
    }

    private List<Person> create1000people() {
        List<Person> people = new ArrayList<>();

        for(int i = 0; i<1000; i++){
            people.add(new Person(i, "Name"+i, 24, i+"@mail.ru",
                    "Country, Town, " + String.format("%06d", new Random().nextInt(999999))));
        }

        return people;
    }
}
