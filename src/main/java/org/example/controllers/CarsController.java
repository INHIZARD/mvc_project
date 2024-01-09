package org.example.controllers;

import jakarta.validation.Valid;
import org.example.dao.CarDAO;
import org.example.dao.PersonDAO;
import org.example.models.Car;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/cars")
public class CarsController {
    private final CarDAO carDAO;
    private final PersonDAO personDAO;

    @Autowired
    public CarsController(CarDAO carDAO, PersonDAO personDAO) {
        this.carDAO = carDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("cars", carDAO.index());
        return "cars/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("car", carDAO.show(id));
        Optional<Person> owner = carDAO.carOwner(id);
        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        } else {
            model.addAttribute("people", personDAO.index());
        }
        return "cars/show";
    }

    @GetMapping("/new")
    public String newCar(@ModelAttribute("car") Car car) {
        return "cars/new";
    }

    @PostMapping
    public String create(@ModelAttribute("car") Car car) {
        carDAO.save(car);
        return "redirect:/cars";
    }

    @PatchMapping("/{id}/add")
    public String addPerson(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        carDAO.addOwner(id, person);
        return "redirect:/cars/" + id;
    }

    @PatchMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") int id) {
        carDAO.deleteOwner(id);
        return "redirect:/cars/" + id;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("car", carDAO.show(id));
        return "cars/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("car") @Valid Car car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/cars/edit";
        }
        carDAO.update(id, car);
        return "redirect:/cars/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        carDAO.delete(id);
        return "redirect:/cars";
    }
}
