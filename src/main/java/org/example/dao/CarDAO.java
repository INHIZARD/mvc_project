package org.example.dao;

import org.example.models.Car;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CarDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CarDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Car> index() {
        return jdbcTemplate.query("SELECT * FROM car", new BeanPropertyRowMapper<>(Car.class));
    }

    public Car show(int id) {
        return jdbcTemplate.query("SELECT * FROM car WHERE id=?", new BeanPropertyRowMapper<>(Car.class), id)
                .stream().findAny().orElse(null);
    }

    public void save(Car car) {
        jdbcTemplate
                .update("INSERT INTO car(brand_title, model_title, year_of_release, person_id) VALUES(?, ?, ?, ?)",
                        car.getBrandTitle(),
                        car.getModelTitle(),
                        car.getYearOfRelease(),
                        null);
    }

    public void update(int id, Car car) {
        jdbcTemplate
                .update("UPDATE car SET brand_title=?, model_title=?, year_of_release=? WHERE id=?",
                        car.getBrandTitle(),
                        car.getModelTitle(),
                        car.getYearOfRelease(),
                        id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM car WHERE id=?", id);
    }

    public Optional<Person> carOwner(int id) {
        return jdbcTemplate.query(
                "SELECT * FROM person WHERE person.id=(SELECT car.person_id FROM car WHERE car.id=?)",
                        new BeanPropertyRowMapper<>(Person.class), id).stream().findAny();
    }

    public void addOwner(int id, Person person) {
        jdbcTemplate.update("UPDATE car SET person_id=? WHERE id=?", person.getId(), id);
    }

    public void deleteOwner(int id) {
        jdbcTemplate.update("UPDATE car SET person_id=null WHERE id=?", id);
    }
}
