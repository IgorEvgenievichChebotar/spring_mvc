package ru.rutmiit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rutmiit.DAO.PeopleDAO;
import ru.rutmiit.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleDAO peopleDAO;

    @Autowired
    public PeopleController(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @GetMapping
    public String index(Model model){
        // Получим всех людей из DAO и передадим на отображение в представление
        model.addAttribute("people", peopleDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id){
        // Получим одного человека из DAO и передадим на отображение в представление
        model.addAttribute("person", peopleDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute Person person){
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute Person person){
        peopleDAO.save(person);
        return "redirect:people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable int id){
        model.addAttribute("person", peopleDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute Person person, @PathVariable int id){
        peopleDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable int id){
        peopleDAO.delete(id);
        return "redirect:/people";
    }
}
