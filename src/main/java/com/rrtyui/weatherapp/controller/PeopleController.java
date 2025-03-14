package com.rrtyui.weatherapp.controller;

import com.rrtyui.weatherapp.dao.PersonDao;
import com.rrtyui.weatherapp.model.Cart;
import com.rrtyui.weatherapp.model.Person;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDao personDao;
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public PeopleController(PersonDao personDao, HttpServletRequest httpServletRequest) {
        this.personDao = personDao;
        this.httpServletRequest = httpServletRequest;
    }

    @GetMapping()
    public String index(Model model) {
        HttpSession session = httpServletRequest.getSession();

        String user = (String) session.getAttribute("current_user");

        if (user == null) {
            // response для нового пользователя
            // авторизация
             // регистрация
             // session.setAttribute("current_user", ID);
        } else {
            // response для авторизованного пользователя
        }


//        String name = httpServletRequest.getParameter("name");
//        int quantity = Integer.parseInt(httpServletRequest.getParameter("quantity"));
//
//        Cart cart = (Cart)session.getAttribute("cart");
//
//        if (cart == null) {
//            cart = new Cart();
//
//            cart.setName(name);
//            cart.setQuantity(quantity);
//        }
//
//        session.setAttribute("cart", cart);


        model.addAttribute("people", personDao.index());
        model.addAttribute("cart", cart);

        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model) {
        model.addAttribute("person", personDao.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/new";
        }

        personDao.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDao.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }

        personDao.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDao.delete(id);
        return "redirect:/people";
    }
}
