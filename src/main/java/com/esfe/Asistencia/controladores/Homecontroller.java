package com.esfe.Asistencia.controladores;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;



import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/")
public class Homecontroller {
    @RequestMapping
    public String index() {
        return "home/index"; // This should match the path to your Thymeleaf template
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "home/formlogin"; // This should match the path to your login Thymeleaf template
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        return "redirect:/";
    }
    

}
