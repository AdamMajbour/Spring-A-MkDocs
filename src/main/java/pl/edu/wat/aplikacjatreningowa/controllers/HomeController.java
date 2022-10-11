package pl.edu.wat.aplikacjatreningowa.controllers;


import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;


import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/start")
    public String startPage(Model model, Principal p) {
        System.out.println(p);
        model.addAttribute("activePage", "home");
        return "home";
    }

    @GetMapping("/")
    public RedirectView indexPage(Model model, Principal p) {
        if(p==null) return new RedirectView("/start");
        else return new RedirectView("/loginHome");

    }

}
