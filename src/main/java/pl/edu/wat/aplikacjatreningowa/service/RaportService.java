package pl.edu.wat.aplikacjatreningowa.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wat.aplikacjatreningowa.controllers.RaportController;
import pl.edu.wat.aplikacjatreningowa.models.UserAccount;
import pl.edu.wat.aplikacjatreningowa.models.front.*;

import java.security.Principal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class RaportService {

    private final TrainingFormService trainingFormService;

    public RaportInfo getTrainingRaportInfo(Long trainingId, UserAccount userAccount)
    {
        RaportInfo raportInfo = new RaportInfo();
        TrainingFormInfo trainingFormInfo = trainingFormService.getTrainingFormInfo(trainingId, userAccount);
        raportInfo.setTrainingName(trainingFormInfo.getName());
        raportInfo.setDateFrom(trainingFormInfo.getFromDate());
        raportInfo.setExerciseCount(trainingFormInfo.getExerciseInfoList().size());
        raportInfo.setCaloriesSum(getTrainingCalories(trainingFormInfo));
        raportInfo.setWeightSum(getTrainingWeight(trainingFormInfo));
        raportInfo.setTrainingTime(Duration.between(trainingFormInfo.getFromDate(), trainingFormInfo.getToDate()).toMinutes());
        raportInfo.setExerciseNames(getExerciseNames(trainingFormInfo));
        return raportInfo;
    }


    public float getTrainingCalories(TrainingFormInfo trainingFormInfo)
    {
        float calories=0;
        for (ExerciseInfo exerciseInfo:
             trainingFormInfo.getExerciseInfoList()) {
            for (SeriesInfo seriesInfo:
                 exerciseInfo.getSeriesInfos()) {
                calories += seriesInfo.getCalories();
            }
        }
        return calories;
    }

    public int getTrainingWeight(TrainingFormInfo trainingFormInfo)
    {
        int weight=0;
        for (ExerciseInfo exerciseInfo:
                trainingFormInfo.getExerciseInfoList()) {
            for (SeriesInfo seriesInfo:
                    exerciseInfo.getSeriesInfos()) {
                for (ParameterInfo parameterInfo:
                     seriesInfo.getParameterInfoList()) {
                    if(parameterInfo.getName()=="obicazenie")
                        weight+=parameterInfo.getCount();
                }
            }
        }
        return weight;
    }


    public List<String> getExerciseNames(TrainingFormInfo trainingFormInfo)
    {
        List<String> exerciseNames = new LinkedList<>();
        for (ExerciseInfo exerciseInfo:
             trainingFormInfo.getExerciseInfoList()) {
            exerciseNames.add(exerciseInfo.getName());
        }
        return exerciseNames;
    }


}
