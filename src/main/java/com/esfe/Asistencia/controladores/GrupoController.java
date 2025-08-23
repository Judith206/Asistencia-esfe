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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esfe.Asistencia.Modelos.Grupo;
import com.esfe.Asistencia.Servicios.Interfaces.IGrupoService;
import com.esfe.Asistencia.Utilidades.PdfGeneratorService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/grupos")// Define la direccion  URL de  el controller
public class GrupoController {

    @Autowired
    private IGrupoService grupoService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        
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
    // Crear 

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("grupo", new Grupo());
        model.addAttribute("action", "create");
        return "grupo/mant";
    }

    //editar
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
       Grupo grupo = grupoService.buscarPorId(id).orElseThrow();
       model.addAttribute("grupo", grupo);
       model.addAttribute("action", "edit");
       return "grupo/mant";
    }
    //ver (solo lectura)
    @GetMapping("/view/{id}")
    public String view(@PathVariable Integer id, Model model){
        Grupo grupo = grupoService.buscarPorId(id).orElseThrow();
        model.addAttribute("grupo", grupo);
        model.addAttribute("action", "view");
        return "grupo/mant";
    
    }
    //Eliminar
    @GetMapping("/delete/{id}")
    public String deleteConfirm(@PathVariable Integer id, Model model) {
        Grupo grupo = grupoService.buscarPorId(id).orElseThrow();
        model.addAttribute("grupo", grupo);
        model.addAttribute("action", "delete");
        return "grupo/mant";
    }

    //procesar
    @PostMapping("/create")
    public String saveNuevo(@ModelAttribute Grupo grupo, BindingResult result, RedirectAttributes redirect, Model model) {
        if(result.hasErrors()){
            model.addAttribute("action", "create");
            return "grupo/mant";
        }
        grupoService.crearOeditar(grupo);
            redirect.addFlashAttribute("msg", "Grupo creado correctamente");
            return "redirect:/grupos";
    }
    @PostMapping("/edit")
    public String saveEditado(@ModelAttribute Grupo grupo, BindingResult result,RedirectAttributes redirect, Model model){
        if(result.hasErrors()){
            model.addAttribute("action", "edit");
            return "grupo/mant";
        }
        grupoService.crearOeditar(grupo);
        redirect.addFlashAttribute("msg", "Grupo actualizado correctamente");
        return "redirect:/grupos";
    }
    @PostMapping("/delete")
    public String deletegrupo (@ModelAttribute Grupo grupo, RedirectAttributes redirect){
        grupoService.eliminarPorId(grupo.getId());
        redirect.addFlashAttribute("msg", "Grupo eliminado correctamente");
        return "redirect:/grupos";

    }
    @GetMapping("/grupoPDF")
    public void generarPdf(Model model, HttpServletResponse response) throws Exception {
        // 1. Obtener datos a mostrar en el pdf
        List<Grupo> grupos = grupoService.obtenerTodos();

        // 2. Preparar datos para Thymeleaf
        Map<String, Object> data = new HashMap<>();
        data.put("grupos", grupos);

        //3. Generar PDF (con el nombre de la plantilla Thymeleaf que quieres usar)
        byte[] pdfBytes = pdfGeneratorService.generatePdfReport("grupo/RPGrupo", data);

        // 4. Configurar la respuesta HTTP para descargar o mostrar el PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=grupos.pdf");
        response.setContentLength(pdfBytes.length);

        //5. Escribir el PDF en la respuesta
        response.getOutputStream().write(pdfBytes);
        response.getOutputStream().flush();
    }
}
