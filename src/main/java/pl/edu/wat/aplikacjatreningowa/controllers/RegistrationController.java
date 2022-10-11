package pl.edu.wat.aplikacjatreningowa.controllers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.aplikacjatreningowa.models.front.RegistrationData;
import pl.edu.wat.aplikacjatreningowa.service.UserService;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final RegistrationData registrationData = new RegistrationData();

    @GetMapping("/rejestracja")
    public String registrationPage(Model model) {
        model.addAttribute("registrationData", registrationData);
        model.addAttribute("activePage", "registration");
        return "registration";
    }

    @RequestMapping("/rejestracja")
    public String registrationReq(@RequestParam String errMessage,
                                  @RequestParam RegistrationData registrationData,
                                  Model model)
    {
        model.addAttribute("errMessage", errMessage);
        model.addAttribute("registrationData", registrationData);
        return "registration";
    }

    @PostMapping("/rejestracja")
    public String registerUserAccount(@Valid @ModelAttribute("userAccount") RegistrationData registrationData,
                                      BindingResult result, Model model) {
        if (result.hasErrors()) {
            return registrationReq("Jakis ERROR", registrationData, model);
        }
        if (userService.isEmailExist(registrationData.getEmail())) {
            return registrationReq("Istnieje już użytkownik z takim mailem!!!", registrationData, model);
        }
        if (userService.isLoginExist(registrationData.getLogin())) {
            return registrationReq("Istnieje już użytkownik z takim loginem!!!", registrationData, model);
        }
        System.out.println(userService.getUserAccounts());
        userService.addUserAccount(registrationData);
        return registrationInfo("Rejestracja przebiegła pomyślnie. \n" +
                "Potiwerdź stwojego maila", model);
    }

    @GetMapping("/rejestracja/potwierdzenie")
    public String confirmRegistration(@RequestParam("token") String token, Model model)
    {
       model.addAttribute("message", userService.confirmEmail(token));
       return "registrationInfo";

    }

    @RequestMapping("/rejestracja/potwierdzenie")
    public String registrationInfo(@RequestParam String message, Model model)
    {
        model.addAttribute("message", message);
        return "registrationInfo";
    }
}
