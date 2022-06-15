package ru.rutmiit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/first")
public class FirstController {

    @GetMapping("/hello")
    public String helloPage(@RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "surname", required = false) String surname,
                            Model model) {

        String value = "Hello, " + name + " " + surname;

        model.addAttribute("message", value);

        return "first/hello";
    }

    @GetMapping("/bye")
    public String byePage(){
        return "first/bye";
    }

    @GetMapping("/calc")
    public String calc(@RequestParam(value = "num1",required = false) Integer num1,
                       @RequestParam(value = "num2", required = false) Integer num2,
                       @RequestParam(value = "operation",required = false) String operation,
                       Model model){

        int result = 0;
        switch(operation){
            case "addition":
                result = num1 + num2;
                break;
            case "subtraction":
                result = num1 - num2;
                break;
            case "division":
                result = num1 / num2;
                break;
            case "multiplication":
                result = num1 * num2;
        }

        model.addAttribute("result", result);

        return "first/calc";
    }
}
