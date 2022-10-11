package pl.edu.wat.aplikacjatreningowa.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;
import pl.edu.wat.aplikacjatreningowa.models.front.*;
import pl.edu.wat.aplikacjatreningowa.models.main.Exercise;
import pl.edu.wat.aplikacjatreningowa.models.main.Parameter;
import pl.edu.wat.aplikacjatreningowa.service.ExerciseService;
import pl.edu.wat.aplikacjatreningowa.service.ParametrizedExerciseService;
import pl.edu.wat.aplikacjatreningowa.service.TrainingFormService;
import pl.edu.wat.aplikacjatreningowa.service.TrainingService;
import pl.edu.wat.aplikacjatreningowa.service.UserService;

import java.security.Principal;
import java.util.List;



@Controller
@AllArgsConstructor
public class ExerciseController {

    ParametrizedExerciseService parametrizedExerciseService;
    TrainingService trainingService;
    ExerciseService exerciseService;
    TrainingFormService trainingFormService;
    UserService userService;

    @GetMapping(path = "/treningi/trening={trainingID}/cwiczenie={exerciseID}")
    public String getExercise(Model model, 
                              Principal principal,
                              @PathVariable("trainingID") Long trainingID,
                              @PathVariable("exerciseID") Long exerciseID)
    {
        if(userService.isValidUser(userService.getUserAccount(principal.getName()),
                trainingService.getTraining(trainingID).getUser())) {
            model.addAttribute("trainingId", trainingID);
            model.addAttribute("exerciseInfo", parametrizedExerciseService.getExerciseInfo(exerciseID, trainingID));
            model.addAttribute("seriesData", exerciseService.getExerciseParametersInfo(exerciseID));
            model.addAttribute("parameters", new Parameters());
            return "training/editExercise";
        }
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    //unsafe?
    @GetMapping(path = "/ankiety/ankieta={trainingID}/cwiczenie={exerciseID}")
    public String getExerciseFromForm(Model model,
                              @PathVariable("trainingID") Long trainingID,
                              @PathVariable("exerciseID") Long exerciseID,
                                      Principal principal)
    {
        if(userService.isValidUser(userService.getUserAccount(principal.getName()),
                trainingFormService.getTrainingForm(trainingID).getUser())) {
            model.addAttribute("trainingId", trainingID);
            model.addAttribute("exerciseInfo", parametrizedExerciseService.getExerciseInfoForm(exerciseID, trainingID));
            model.addAttribute("seriesData", exerciseService.getExerciseParametersInfo(exerciseID));
            model.addAttribute("parameters", new Parameters());
            return "trainingForms/editExerciseForm";
        }
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }


    @PostMapping(path = "/treningi/trening={trainingID}")
    public RedirectView addExercise(Model model,
                                    Principal principal,
                                    @PathVariable("trainingID") Long trainingID,
                                    @ModelAttribute TrainingInfo trainingInfo,
                                    @ModelAttribute ExerciseData exerciseData) {
        if (userService.isValidUser(userService.getUserAccount(principal.getName()),
                trainingService.getTraining(trainingID).getUser())) {
            parametrizedExerciseService.
                    addParametrizedExercise(trainingService.getTraining(trainingID),
                            null, exerciseService.getExerciseByName(exerciseData.getName()));

            return new RedirectView("/treningi/trening=" + trainingID);
        } else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @PostMapping(path = "/ankiety/ankieta={trainingID}")
    public RedirectView addExerciseForm(Model model,
                                    Principal principal,
                                    @PathVariable("trainingID") Long trainingID,
                                    @ModelAttribute ExerciseData exerciseData)
    {
        if (userService.isValidUser(userService.getUserAccount(principal.getName()),
                trainingFormService.getTrainingForm(trainingID).getUser())) {
            String[] s = exerciseData.getName().split(",");
            parametrizedExerciseService.
                    addParametrizedExercise(null, trainingFormService.getTrainingForm(trainingID),
                            exerciseService.getExerciseByName(s[s.length-1]));

            return new RedirectView("/ankiety/ankieta="+trainingID);
        } else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @GetMapping(path = "/treningi/usuwanie/trening={trainingID}/cwiczenie={exerciseID}")
    public RedirectView deleteExercise(@PathVariable("trainingID") Long trainingID,
                                       @PathVariable("exerciseID") Long exerciseID,
                                       Principal principal)
    {
        if(userService.isValidUser(userService.getUserAccount(principal.getName()),
                trainingService.getTraining(trainingID).getUser())) {
            parametrizedExerciseService
                    .deleteParametrizedExercise(parametrizedExerciseService
                            .getParametrizedExercise(trainingID, exerciseID));
            return new RedirectView("/treningi/trening=" + trainingID);
        }
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @GetMapping(path = "/ankiety/usuwanie/ankieta={ID}/cwiczenie={eID}")
    public RedirectView deleteExerciseFromForm(@PathVariable("ID") Long trainingID,
                                       @PathVariable("eID") Long exerciseID, Principal principal)
    {
        if(userService.isValidUser(userService.getUserAccount(principal.getName()),
                trainingFormService.getTrainingForm(trainingID).getUser())) {
            parametrizedExerciseService
                    .deleteParametrizedExercise(parametrizedExerciseService
                            .getParametrizedExerciseFromForm(trainingID, exerciseID));
            return new RedirectView("/ankiety/ankieta="+trainingID);
        }
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}
