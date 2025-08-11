package com.esfe.Asistencia.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Homecontroller {
    @RequestMapping
    public String index() {
        return "home/index"; // This should match the path to your Thymeleaf template
    }

}
