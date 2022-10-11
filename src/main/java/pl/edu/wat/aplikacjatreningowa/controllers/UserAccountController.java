package pl.edu.wat.aplikacjatreningowa.controllers;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import pl.edu.wat.aplikacjatreningowa.controllers.dtos.PasswordResponse;
import pl.edu.wat.aplikacjatreningowa.service.UserService;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class UserAccountController {

    UserService userService;

    @GetMapping(path = "/konto")
    public String getAcc(Model model, Principal principal)
    {
        model.addAttribute("accountInfo", userService.getAccountInfo(principal.getName()));

        model.addAttribute("password", new PasswordResponse());
        return "account/account";
    }

    @GetMapping(path = "/konto&usun")
    public RedirectView deleteAccount(Model model, Principal principal)
    {
        userService.deleteAccount(principal.getName());
        return new RedirectView("");
    }



    @GetMapping(path = "/konto/parametry&waga={weight}&wzrost={height}")
    public RedirectView changeUserParameters(@PathVariable("weight") int weight,
                                               @PathVariable("height") int height,
                                               Principal principal)
    {
        userService.changeParameters(principal.getName(), weight, height);

        return new RedirectView("/konto");
    }

    @PostMapping(path = "/konto/zmiana-hasla")
    public RedirectView changePassword(Principal principal, @ModelAttribute PasswordResponse passwordResponse)
    {
        userService.generateTokenToChangePassword(principal.getName(), passwordResponse);
        return new RedirectView("/konto");
    }

    @GetMapping(path = "/konto/potwierdzenie-hasla&token={tooken}")
    public RedirectView changePasswordConfirmation(@PathVariable String tooken)
    {
        userService.changePasword(tooken);
        return new RedirectView("/konto");
    }
}
