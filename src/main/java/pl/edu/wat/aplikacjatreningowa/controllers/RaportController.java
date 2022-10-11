package pl.edu.wat.aplikacjatreningowa.controllers;


import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.wat.aplikacjatreningowa.models.UserAccount;
import pl.edu.wat.aplikacjatreningowa.models.front.RaportInfo;
import pl.edu.wat.aplikacjatreningowa.service.RaportService;
import pl.edu.wat.aplikacjatreningowa.service.TrainingFormService;
import pl.edu.wat.aplikacjatreningowa.service.UserService;

import java.io.IOException;
import java.security.Principal;

@Controller
@AllArgsConstructor

public class RaportController {

    private final RaportService raportService;
    private final UserService userService;



    @GetMapping(path = "/raport/trainingId={id}")
    public String getTrainingRaport(@PathVariable("id") Long id,
                                    Principal principal,
                                    Model model) {
        UserAccount userAccount = userService.getUserAccount(principal.getName());
        model.addAttribute("raportInfo",raportService.getTrainingRaportInfo(id, userAccount));
        return "/raport/raport";
    }
}
