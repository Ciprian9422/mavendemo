package com.ciprian.demo.controller;

import com.ciprian.demo.domain.Person;
import com.ciprian.demo.repository.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PersonController {

  private final PersonRepository personRepository;

  public PersonController(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  @GetMapping("people")
  String getPeople(Model model) {
    List<Person> personList = personRepository.findAll();

    model.addAttribute("something", "List of people");

    if(!personList.isEmpty()) {
      Person person1 = new Person();
      person1.setAge(18).setName("Ágnes");
      personList.add(person1);
      model.addAttribute("people", personList);
    }

    return "people";
  }
}
