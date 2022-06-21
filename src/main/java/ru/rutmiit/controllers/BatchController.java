package ru.rutmiit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rutmiit.DAO.PeopleDAO;

@Controller
@RequestMapping("/test_batch_update")
public class BatchController {

    private final PeopleDAO peopleDAO;

    @Autowired
    public BatchController(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @GetMapping
    public String performanceTestPage(){
        return "batch/test_batch_update";
    }

    @GetMapping("/without")
    public String testMultipleUpdate(){
        peopleDAO.testMultipleUpdate();
        return "redirect:/people";
    }

    @GetMapping("/with")
    public String testWithBatch(){
        peopleDAO.testWithBatch();
        return "redirect:/people";
    }
}
