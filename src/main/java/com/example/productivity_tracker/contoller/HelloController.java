package com.example.productivity_tracker.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("message", "Привет, мир!");
        return "hello"; // Имя шаблона Thymeleaf
    }
}