package com.esfe.Asistencia.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.esfe.Asistencia.Utilidades.PdfGeneratorService;

import com.esfe.Asistencia.Modelos.Docente;

import com.esfe.Asistencia.Servicios.Interfaces.IDocenteService;

import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/docentes")
public class DocenteController {

    @Autowired
    private IDocenteService docenteService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        
        Page<Docente> docentes = docenteService.buscarTodosPaginados(pageable);
        model.addAttribute("docentes", docentes);

        int totalPages = docentes.getTotalPages();
        if (totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "docente/index";

    }
    // Crear 

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("docente", new Docente());
        model.addAttribute("action", "create");
        return "docente/mant";
    }

    //editar
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
       Docente docente = docenteService.buscarPorId(id).orElseThrow();
       model.addAttribute("docente", docente);
       model.addAttribute("action", "edit");
       return "docente/mant";
    }
    //ver (solo lectura)
    @GetMapping("/view/{id}")
    public String view(@PathVariable Integer id, Model model){
        Docente docente = docenteService.buscarPorId(id).orElseThrow();
        model.addAttribute("docente", docente);
        model.addAttribute("action", "view");
        return "docente/mant";
    
    }
    //Eliminar
    @GetMapping("/delete/{id}")
    public String deleteConfirm(@PathVariable Integer id, Model model) {
        Docente docente = docenteService.buscarPorId(id).orElseThrow();
        model.addAttribute("docente", docente);
        model.addAttribute("action", "delete");
        return "docente/mant";
    }

    //procesar
    @PostMapping("/create")
    public String saveNuevo(@ModelAttribute Docente docente, BindingResult result, RedirectAttributes redirect, Model model) {
        if(result.hasErrors()){
            model.addAttribute("action", "create");
            return "docente/mant";
        }
        docenteService.crearOeditar(docente);
            redirect.addFlashAttribute("msg", "Docente creado correctamente");
            return "redirect:/docentes";
    }
    @PostMapping("/edit")
    public String saveEditado(@ModelAttribute Docente docente, BindingResult result,RedirectAttributes redirect, Model model){
        if(result.hasErrors()){
            model.addAttribute("action", "edit");
            return "docente/mant";
        }
        docenteService.crearOeditar(docente);
        redirect.addFlashAttribute("msg", "Docente actualizado correctamente");
        return "redirect:/docentes";
    }
    @PostMapping("/delete")
    public String deletedocente (@ModelAttribute Docente docente, RedirectAttributes redirect){
        docenteService.eliminarPorId(docente.getId());
        redirect.addFlashAttribute("msg", "Docente eliminado correctamente");
        return "redirect:/docentes";

    }
    @GetMapping("/docentePDF")
    public void generarPdf(Model model, HttpServletResponse response) throws Exception {
        // 1. Obtener datos a mostrar en el pdf
        List<Docente> docentes = docenteService.obtenerTodos();

        // 2. Preparar datos para Thymeleaf
        Map<String, Object> data = new HashMap<>();
        data.put("docentes", docentes);

        //3. Generar PDF (con el nombre de la plantilla Thymeleaf que quieres usar)
        byte[] pdfBytes = pdfGeneratorService.generatePdfReport("docente/RPDocente", data);

        // 4. Configurar la respuesta HTTP para descargar o mostrar el PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=docentes.pdf");
        response.setContentLength(pdfBytes.length);

        //5. Escribir el PDF en la respuesta
        response.getOutputStream().write(pdfBytes);
        response.getOutputStream().flush();
    }


}
