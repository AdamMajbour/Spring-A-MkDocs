package pl.edu.wat.aplikacjatreningowa.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import pl.edu.wat.aplikacjatreningowa.models.UserAccount;
import pl.edu.wat.aplikacjatreningowa.models.front.*;
import pl.edu.wat.aplikacjatreningowa.models.front.TrainingFormData;
import pl.edu.wat.aplikacjatreningowa.models.front.TrainingFormInfo;
import pl.edu.wat.aplikacjatreningowa.models.front.TrainingFormInfoStringed;
import pl.edu.wat.aplikacjatreningowa.service.ExerciseService;
import pl.edu.wat.aplikacjatreningowa.service.TrainingFormService;
import pl.edu.wat.aplikacjatreningowa.service.TrainingService;
import pl.edu.wat.aplikacjatreningowa.service.UserService;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class TrainingFormController {

    private final TrainingFormService trainingFormService;
    private final TrainingService trainingService;
    private final ExerciseService exerciseService;
    private final UserService userService;

    @GetMapping("/ankiety")
    public String getTrainingForms(Model model, Principal principal)
    {
        UserAccount userAccount = userService.getUserAccount(principal.getName());
        model.addAttribute("trainingForms", trainingFormService.getUserTrainingForms(userAccount));
        model.addAttribute("trainingFormData", new TrainingFormData());
        return "trainingForms/forms";
    }

    @PostMapping(path = "/ankiety")
    public RedirectView addTrainingForm(@ModelAttribute TrainingFormData trainingFormData, Principal principal)
    {
        trainingFormService.addTrainingForm(trainingFormData, principal.getName());
        return new RedirectView("/ankiety");
    }

    @GetMapping(path = "/ankiety/usuwanie/ankieta={ID}")
    public RedirectView deleteTrainingForm(@PathVariable("ID") Long trainingID, Principal principal)
    {
        trainingFormService.deleteTrainingForm(trainingFormService.getTrainingFormById(trainingID),
                userService.getUserAccount(principal.getName()));
        return new RedirectView("/ankiety");
    }

    @GetMapping(path = "/ankiety/ankieta={ID}")
    public String getTrainingForm(Model model, @PathVariable("ID") Long trainingID, Principal principal)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        TrainingFormInfo trainingInfo = trainingFormService.getTrainingFormInfo(trainingID, userService.getUserAccount(principal.getName()));
        TrainingFormInfoStringed trainingFormInfoStringed = new TrainingFormInfoStringed(
                trainingInfo.getId()
                ,trainingInfo.getName()
                ,trainingInfo.getDescription()
                ,trainingInfo.getFromDate().format(formatter)
                ,trainingInfo.getToDate().format(formatter)
                ,trainingInfo.getExerciseInfoList());
        model.addAttribute("trainingFormInfo", trainingFormInfoStringed);
        model.addAttribute("exerciseData", new ExerciseData());
        System.out.println(trainingInfo.getFromDate().format(formatter));
        model.addAttribute("exerciseList", exerciseService
                .getAvailableExercises(trainingFormService.getTrainingFormById(trainingID)));

        model.addAttribute("trainings", trainingService.getUserTrainigs(principal.getName()));
        model.addAttribute("exerciseMoveData", new ExerciseMoveData());
        return "trainingForms/editForm";
    }

    @GetMapping(path = "/ankiety/ankieta={trainingID}/edycja/nazwa={trainingName}&&opis={trainingDescription}" +
            "&&od={trainingFromDate}&&do={trainingToDate}")
    public RedirectView editTraining(@PathVariable("trainingID") Long trainingID,
                                     @PathVariable("trainingName") String trainingName,
                                     @PathVariable("trainingDescription") String trainingDescription,
                                     @PathVariable("trainingFromDate") String trainingFromDate,
                                     @PathVariable("trainingToDate") String trainingToDate,
                                     Principal principal)
    {
        if(userService.isValidUser(userService.getUserAccount(principal.getName()),
                trainingFormService.getTrainingForm(trainingID).getUser())) {
            trainingFormService.updateTrainingForm(trainingID, trainingName,
                    trainingDescription, trainingFromDate, trainingToDate);
        }
        return new RedirectView("/treningi");
    }

    @PostMapping(path = "/ankietymove/ankieta={aID}")
    public RedirectView exercisesFromTrainingToTrainingForm(Model model,
                                        @PathVariable("aID") Long formID,
                                        @ModelAttribute ExerciseMoveData exerciseMoveData,
                                                            Principal principal)
    {
        if(userService.isValidUser(userService.getUserAccount(principal.getName()),
                trainingFormService.getTrainingForm(formID).getUser())) {
            trainingFormService.exercisesFromTrainingToTrainingForm(formID, exerciseMoveData.getTrainingId());
        }

        return new RedirectView("/ankiety/ankieta="+formID);
    }


    @GetMapping(path = "/ankiety/cyklicznosc/ankieta={ID}")
    public RedirectView cyclicTrainingForm(@PathVariable("ID") Long trainingID, Principal principal) {
        int c = trainingFormService.cyclicTrainingForm(trainingFormService.getTrainingFormById(trainingID),
                userService.getUserAccount(principal.getName()));
        String message = c > 0 ? "fail" : "success";
        return new RedirectView("/ankiety");
    }
}
