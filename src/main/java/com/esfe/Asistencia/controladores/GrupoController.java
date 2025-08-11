package com.esfe.Asistencia.controladores;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



import com.esfe.Asistencia.Modelos.Grupo;
import com.esfe.Asistencia.Servicios.Interfaces.IGrupoService;

@Controller
@RequestMapping("/grupos")// Define la direccion  URL de  el controller
public class GrupoController {

    @Autowired
    private IGrupoService grupoService;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        
        Page<Grupo> grupos = grupoService.buscarTodos(pageable);
        model.addAttribute("grupos", grupos);

        int totalPages = grupos.getTotalPages();
        if (totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "grupo/index";

    }
    
    
}
