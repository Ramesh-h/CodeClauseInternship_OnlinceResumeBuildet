package com.resume.builder;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lowagie.text.DocumentException;

@Controller
@RequestMapping("/resume")
public class ResumeController {
	
	@Autowired
	private PdfGenerationService pdfGenerationService;
    
    @Autowired
    private ResumeService resumeService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("person", new Person());
        return "createResume";
    }

    @PostMapping("/create")
    public String createResume(@ModelAttribute Person person) {
        resumeService.save(person);
        return "redirect:/resume/view/" + person.getId();
    }

    @GetMapping("/view/{id}")
    public String viewResume(@PathVariable Long id, Model model) {
        Person person = resumeService.getById(id);
        model.addAttribute("person", person);
        return "viewResume";
    }
    
    @GetMapping("/download-pdf/{id}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) throws IOException, DocumentException {
        Person person = resumeService.getById(id);
        byte[] pdfBytes = pdfGenerationService.generatePdf(person);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "resume.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}
