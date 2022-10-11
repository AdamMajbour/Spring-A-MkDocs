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
import pl.edu.wat.aplikacjatreningowa.models.front.Parameters;
import pl.edu.wat.aplikacjatreningowa.models.front.SeriesData;
import pl.edu.wat.aplikacjatreningowa.models.front.SeriesInfo;
import pl.edu.wat.aplikacjatreningowa.service.*;

import java.security.Principal;


@Controller
@AllArgsConstructor
public class SeriesController {

    ParametrizedExerciseService parametrizedExerciseService;
    TrainingService trainingService;
    ExerciseService exerciseService;
    ValuedParameterService valuedParameterService;
    TrainingFormService trainingFormService;
    UserService userService;

    @PostMapping(path = "/treningi/trening={trainingID}/cwiczenie={exerciseID}")
    public RedirectView addSeriesToExercise(Model model,
                                            Principal principal,
                                            @PathVariable("trainingID") Long trainingID,
                                            @PathVariable("exerciseID") Long exerciseID,
                                            @ModelAttribute Parameters parameters) {
        if (userService.isValidUser(userService.getUserAccount(principal.getName()),
                trainingService.getTraining(trainingID).getUser())) {
            parametrizedExerciseService
                    .addParametrizedExercise(trainingService.getTraining(trainingID),
                            null, exerciseService.getExerciseById(exerciseID), parameters);
            return new RedirectView("/treningi/trening=" + trainingID + "/cwiczenie=" + exerciseID);
        } else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @PostMapping(path = "/ankiety/ankieta={trainingID}/cwiczenie={exerciseID}")
    public RedirectView addSeriesToExerciseForm(Model model,
                                            @PathVariable("trainingID") Long trainingID,
                                            @PathVariable("exerciseID") Long exerciseID,
                                            @ModelAttribute Parameters parameters,
                                                Principal principal)
    {
        if (userService.isValidUser(userService.getUserAccount(principal.getName()),
                trainingFormService.getTrainingForm(trainingID).getUser())) {
            parametrizedExerciseService
                    .addParametrizedExercise(null, trainingFormService.getTrainingForm(trainingID),
                            exerciseService.getExerciseById(exerciseID), parameters);
            return new RedirectView("/ankiety/ankieta=" + trainingID + "/cwiczenie=" + exerciseID);
        } else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @GetMapping (path = "/treningi/usuwanie/trening={trainingID}/cwiczenie={exerciseID}/seria={seriesID}")
    public RedirectView deleteSeries(@PathVariable("trainingID") Long trainingID,
                                     @PathVariable("exerciseID") Long exerciseID,
                                     @PathVariable("seriesID") Long seriesID,
                                     Principal principal) {
        if (userService.isValidUser(userService.getUserAccount(principal.getName()),
                trainingService.getTraining(trainingID).getUser())) {
            parametrizedExerciseService.deleteParametrizedExercise(parametrizedExerciseService.getParametrizedExercise(seriesID));
            RedirectView redirectView = new RedirectView("/treningi/trening=" + trainingID + "/cwiczenie=" + exerciseID);
            return redirectView;
        } else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @GetMapping (path = "/ankiety/usuwanie/ankieta={trainingID}/cwiczenie={exerciseID}/seria={seriesID}")
    public RedirectView deleteSeriesForm(@PathVariable("trainingID") Long trainingID,
                                     @PathVariable("exerciseID") Long exerciseID,
                                     @PathVariable("seriesID") Long seriesID,
                                         Principal principal)
    {
        if (userService.isValidUser(userService.getUserAccount(principal.getName()),
                trainingFormService.getTrainingForm(trainingID).getUser())) {
            parametrizedExerciseService.deleteParametrizedExercise(parametrizedExerciseService.getParametrizedExercise(seriesID));
            return new RedirectView("/ankiety/ankieta=" + trainingID + "/cwiczenie=" + exerciseID);
        } else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

}
