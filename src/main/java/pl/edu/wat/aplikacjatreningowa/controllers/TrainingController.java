package pl.edu.wat.aplikacjatreningowa.controllers;

import lombok.AllArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import pl.edu.wat.aplikacjatreningowa.models.front.*;
import pl.edu.wat.aplikacjatreningowa.models.main.Training;
import pl.edu.wat.aplikacjatreningowa.models.main.TrainingForm;
import pl.edu.wat.aplikacjatreningowa.service.ExerciseService;
import pl.edu.wat.aplikacjatreningowa.service.ParametrizedExerciseService;
import pl.edu.wat.aplikacjatreningowa.service.TrainingService;
import pl.edu.wat.aplikacjatreningowa.service.UserService;
import java.security.Principal;

import java.util.LinkedList;


@Controller
@AllArgsConstructor
public class TrainingController {

    TrainingService trainingService;
    ParametrizedExerciseService parametrizedExerciseService;
    ExerciseService exerciseService;
    UserService userService;

    @GetMapping("/treningi")
    public String getTrainings(Model model,
                               Principal principal)
    {
        model.addAttribute("trainings", trainingService.getUserTrainigs(principal.getName()));
        model.addAttribute("trainingData", new TrainingData());
        return "training/training";
    }

    @PostMapping(path = "/treningi")
    public RedirectView addTraining(Model model,
                              Authentication authentication,
                              @ModelAttribute TrainingData trainingData,
                                    Principal principal)
    {
        trainingService.addTraining(trainingData, principal.getName());
        return new RedirectView("/treningi");
    }


    @GetMapping(path = "/treningi/trening={trainingID}")
    public String getTraining(Model model,
                              @PathVariable("trainingID") Long trainingID,
                              Principal principal)
    {
        TrainingInfo trainingInfo = trainingService.getTrainingInfo(trainingID,
                userService.getUserAccount(principal.getName()));
        model.addAttribute("trainingInfo", trainingInfo);
        model.addAttribute("exercseData", new ExerciseData());
        model.addAttribute("exerciseList", exerciseService
                .getAvailableExercises(trainingService.getTrainingById(trainingID)));
        return "training/editTraining";
    }

    @GetMapping(path = "/treningi/usuwanie/trening={trainingID}")
    public RedirectView deleteTraining(Principal principal,
                                       @PathVariable("trainingID") Long trainingID)
    {
        trainingService.deleteTraining(trainingService.getTrainingById(trainingID),
                userService.getUserAccount(principal.getName()));
        return new RedirectView("/treningi");
    }


    @GetMapping(path = "/treningi/trening={trainingID}/edycja/nazwa={trainingName}&&opis={trainingDescription}")
    public RedirectView editTraining(Principal principal,
                                     @PathVariable("trainingID") Long trainingID,
                                     @PathVariable("trainingName") String trainingName,
                                     @PathVariable("trainingDescription") String trainingDescription)
    {
        if(userService.isValidUser(userService.getUserAccount(principal.getName()),
                trainingService.getTraining(trainingID).getUser())) {
            trainingService.updateTraining(trainingID, trainingName, trainingDescription);
        }
        return new RedirectView("/treningi");
    }

}
