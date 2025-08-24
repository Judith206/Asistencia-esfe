package com.esfe.Asistencia.controladores;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

import com.esfe.Asistencia.Modelos.*;
import com.esfe.Asistencia.Servicios.Interfaces.*;
import com.esfe.Asistencia.Utilidades.PdfGeneratorService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/matriculas")
public class EstudianteGrupoController {
    @Autowired
    private IEstudianteGrupoService estudianteGrupoService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @Autowired
    private IGrupoService grupoService;

    @Autowired
    private IEstudianteService estudianteService;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        
        Page<EstudianteGrupo> matriculas = estudianteGrupoService.buscarTodosPaginados(pageable);
        model.addAttribute("matriculas", matriculas);

        int totalPages = matriculas.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "matricula/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("estudianteGrupo", new EstudianteGrupo());
        model.addAttribute("estudiantes", estudianteService.obtenerTodos());
        model.addAttribute("grupos", grupoService.obtenerTodos());
        model.addAttribute("action", "create");
        return "matricula/mant";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        EstudianteGrupo estudianteGrupo = estudianteGrupoService.buscarPorId(id);
        model.addAttribute("estudianteGrupo", estudianteGrupo);
        model.addAttribute("estudiantes", estudianteService.obtenerTodos());
        model.addAttribute("grupos", grupoService.obtenerTodos());
        model.addAttribute("action", "edit");
        return "matricula/mant";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Integer id, Model model) {
        EstudianteGrupo estudianteGrupo = estudianteGrupoService.buscarPorId(id);
        model.addAttribute("estudianteGrupo", estudianteGrupo);
        model.addAttribute("estudiantes", estudianteService.obtenerTodos());
        model.addAttribute("grupos", grupoService.obtenerTodos());
        model.addAttribute("action", "view");
        return "matricula/mant";
    }

    @GetMapping("/delete/{id}")
    public String deleteConfirm(@PathVariable Integer id, Model model) {
        EstudianteGrupo estudianteGrupo = estudianteGrupoService.buscarPorId(id);
        model.addAttribute("estudianteGrupo", estudianteGrupo);
        model.addAttribute("estudiantes", estudianteService.obtenerTodos());
        model.addAttribute("grupos", grupoService.obtenerTodos());
        model.addAttribute("action", "delete");
        return "matricula/mant";
    }

    @PostMapping("/create")
    public String saveNuevo(@ModelAttribute EstudianteGrupo estudianteGrupo,
                                BindingResult result,
                                RedirectAttributes redirect, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("estudiantes", estudianteService.obtenerTodos());
            model.addAttribute("grupos", grupoService.obtenerTodos());
            model.addAttribute("action", "Create");
            return "matricula/mant";
        }
        estudianteGrupoService.crearOeditar(estudianteGrupo);
        redirect.addFlashAttribute("message", "Matrícula creada correctamente.");
        return "redirect:/matriculas";
    }

    @PostMapping("/edit")
    public String saveEditado(@ModelAttribute EstudianteGrupo estudianteGrupo,
                              BindingResult result,
                              RedirectAttributes redirect, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("estudiantes", estudianteService.obtenerTodos());
            model.addAttribute("grupos", grupoService.obtenerTodos());
            model.addAttribute("action", "Edit");
            return "matricula/mant";
        }
        estudianteGrupoService.crearOeditar(estudianteGrupo);
        redirect.addFlashAttribute("message", "Matrícula actualizada correctamente.");
        return "redirect:/matriculas";
    }

    @PostMapping("/delete")
    public String deleteEstudianteGrupo(@ModelAttribute EstudianteGrupo estudianteGrupo,
                                RedirectAttributes redirect) {
        estudianteGrupoService.eliminarPorId(estudianteGrupo.getId());
        redirect.addFlashAttribute("message", "Matrícula eliminada correctamente.");
        return "redirect:/matriculas";
    }
    
    @GetMapping("/estudiantegrupoPDF")
    public void generarPdf(Model model, HttpServletResponse response) throws Exception {
        List<EstudianteGrupo> estudianteGrupo = estudianteGrupoService.obtenerTodos();

        Map<String, Object> data = new HashMap<>();
        data.put("matriculas", estudianteGrupo);

        byte[] pdfBytes = pdfGeneratorService.generatePdfReport("matricula/RPEstudianteGrupo", data);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=matriculas.pdf");
        response.setContentLength(pdfBytes.length);

        response.getOutputStream().write(pdfBytes);
        response.getOutputStream().flush();
    }
}