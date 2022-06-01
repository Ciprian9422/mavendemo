package com.ciprian.demo.controller;

import com.ciprian.demo.domain.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class PersonController {

  @GetMapping("people")
  String getPeople(Model model) {
    Person person1 = new Person();
    person1.setAge(18).setName("Ágnes");

    model.addAttribute("something", "List of people");
    model.addAttribute("people",
        Arrays.asList(new Person("Károly", 28), new Person("Krisztián", 30), person1));
    return "people";
  }
}
