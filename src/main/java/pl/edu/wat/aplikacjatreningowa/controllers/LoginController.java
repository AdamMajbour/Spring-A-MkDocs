package pl.edu.wat.aplikacjatreningowa.controllers;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.aplikacjatreningowa.models.LoginData;
import pl.edu.wat.aplikacjatreningowa.service.UserService;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@AllArgsConstructor
public class LoginController
{
    private final UserService userService;
    private final LoginData loginData = new LoginData();

    @GetMapping("/logowanie")
    public String loginPage(Model model)
    {
        model.addAttribute("loginData", loginData);
        model.addAttribute("activePage", "login");
        return "login";
    }

    @RequestMapping("/logowanie")
    public String loginReq(@RequestParam String errMessage,
                           @RequestParam LoginData loginData, Model model)
    {
        model.addAttribute("errMessage", errMessage);
        model.addAttribute("loginData", loginData);
        return "login";
    }

    @PostMapping("/logowanie")
    public String loginUserAccount(@Valid @ModelAttribute("userAccount") LoginData loginData,
                                   BindingResult result, Model model)
    {
        if(result.hasErrors()) return loginReq("Jakis ERROR", loginData, model);

        if (!userService.isLoginExist(loginData.getLogin()))
            return loginReq("Nie ma takiego użytkownika!", loginData, model);

        if (!userService.loginPasswVerif(loginData.getLogin(), loginData.getPassword()))
            return loginReq("Niepoprawne hasło!", loginData, model);

        if (!userService.isAccountActivated(loginData.getLogin()))
            return loginReq("Konto nie zostało aktywowane!", loginData, model);

        return loginInfo("Logowanie przebiegło pomyślnie.", model);
    }

    @RequestMapping("/logowanie/potwierdzenie")
    public String loginInfo(@RequestParam String message, Model model)
    {
        model.addAttribute("message", message);
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

        return "loginHome";
    }

    @GetMapping("/loginHome")
    public String startPage(Model model, Principal p) {
        System.out.println(p);
        model.addAttribute("activePage", "loginHome");
        return "loginHome";
    }
}
