package pl.edu.wat.aplikacjatreningowa.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.aplikacjatreningowa.models.UserAccount;
import pl.edu.wat.aplikacjatreningowa.service.TrainingFormService;
import pl.edu.wat.aplikacjatreningowa.service.UserService;

import java.security.Principal;

@RestController
public class CalendarController
{
    private final UserService userService;
    private final TrainingFormService trainingFormService;

    @Autowired
    public CalendarController(UserService userService, TrainingFormService trainingFormService) {
        this.userService = userService;
        this.trainingFormService = trainingFormService;
    }

    @GetMapping("/calendar")
    public String getTrainingForms(Principal principal) throws JsonProcessingException
    {
        UserAccount userAccount = userService.getUserAccount(principal.getName());

        String json = new Gson().toJson(trainingFormService.getUserTrainingForms(userAccount));

        return json;
    }
}
