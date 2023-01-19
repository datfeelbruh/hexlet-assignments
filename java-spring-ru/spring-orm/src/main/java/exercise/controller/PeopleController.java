package exercise.controller;

import exercise.model.Person;
import exercise.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.Map;

@RestController
@RequestMapping("/people")
public class PeopleController {

    // Автоматически заполняем значение поля
    @Autowired
    private PersonRepository personRepository;

    @GetMapping(path = "/{id}")
    public Person getPerson(@PathVariable long id) {
        return this.personRepository.findById(id);
    }

    @GetMapping(path = "")
    public Iterable<Person> getPeople() {
        return this.personRepository.findAll();
    }

    // BEGIN
    @PostMapping(path = "")
    public void createPeople(@RequestBody Map<String, Object> json) {
        Person person = new Person();
        person.setFirstName(json.get("firstName").toString());
        person.setLastName(json.get("lastName").toString());
        personRepository.save(person);
    }

    @DeleteMapping(path = "/{id}")
    public void deletePeople(@PathVariable long id) {
        personRepository.deleteById(id);
    }

    @PatchMapping(path = "/{id}")
    public void updatePeople(@PathVariable long id, @RequestBody Map<String, Object> json) {
        Person person = personRepository.findById(id);
        person.setFirstName(json.get("firstName").toString());
        person.setLastName(json.get("lastName").toString());
        personRepository.save(person);
    }
    // END
}
