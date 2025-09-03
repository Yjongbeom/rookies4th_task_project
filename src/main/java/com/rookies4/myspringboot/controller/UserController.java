package com.rookies4.myspringboot.controller;

import com.rookies4.myspringboot.entity.UserEntity;
import com.rookies4.myspringboot.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/users/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id,
                                 Model model) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "update-user";
    }
    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") long id,
                             @Valid @ModelAttribute("user") UserEntity user,
                             BindingResult result) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }
        userRepository.save(user);
        return "redirect:/users/index";
    }

    @GetMapping("/users/signup")
    public String showSignUpForm(@ModelAttribute("user") UserEntity user) {
        return "add-user";
    }

    @PostMapping("/users/adduser")
    public String addUser(@Valid @ModelAttribute("user") UserEntity user,
                          BindingResult result, Model model) {
        //검증 오류가 발생한 경우
        if (result.hasErrors()) {
            return "add-user";
        }
        //등록처리
        userRepository.save(user);
        //model.addAttribute("users", userRepository.findAll());
        //return "index";
        //User목록조회하는 Path로 URL Redirection 하기
        return "redirect:/users/index";
    }

    @GetMapping("/users/index")
    public ModelAndView index() {
        List<UserEntity> userEntityList = userRepository.findAll();
        return new ModelAndView("index","users", userEntityList);
    }

    @GetMapping("/thymeleaf")
    public String leaf(Model model) {
        model.addAttribute("name", "스프링부트");
        return "leaf";
    }
}