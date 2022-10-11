package pl.edu.wat.aplikacjatreningowa.service;

import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.wat.aplikacjatreningowa.models.front.*;
import pl.edu.wat.aplikacjatreningowa.models.main.*;
import pl.edu.wat.aplikacjatreningowa.repository.ExerciseRepository;
import pl.edu.wat.aplikacjatreningowa.repository.ParametrizedExerciseRepository;
import pl.edu.wat.aplikacjatreningowa.repository.TrainingFormRepository;
import pl.edu.wat.aplikacjatreningowa.repository.TrainingRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ParametrizedExerciseService {
    ParametrizedExerciseRepository parametrizedExerciseRepository;
    ExerciseService exerciseService;
    TrainingRepository trainingRepository;
    ExerciseRepository exerciseRepository;
    ParameterService parameterService;
    ValuedParameterService valuedParameterService;
    TrainingFormRepository trainingFormRepository;


    public void addParametrizedExercise(Training training, TrainingForm trainingForm, Exercise exercise)
    {
        ParametrizedExercise parametrizedExercise = new ParametrizedExercise();
        parametrizedExercise.setExercise(exercise);
        parametrizedExercise.setTraining(training);
        parametrizedExercise.setTrainingForm(trainingForm);
        parametrizedExerciseRepository.save(parametrizedExercise);
    }

    public void addParametrizedExercise(Training training, TrainingForm trainingForm, Exercise exercise, Parameters parameters)
    {
        ParametrizedExercise parametrizedExercise = new ParametrizedExercise();
        parametrizedExercise.setExercise(exercise);
        parametrizedExercise.setTraining(training);
        parametrizedExercise.setTrainingForm(trainingForm);
        parametrizedExerciseRepository.save(parametrizedExercise);
        parameterService.addToValuedParameters(parameters, parametrizedExercise);
        parametrizedExercise
                .setValuedParameterList(valuedParameterService
                        .getParametrizedExerciseValuedParameters(parametrizedExercise));
        parametrizedExerciseRepository.save(parametrizedExercise);
    }

    public ExerciseInfo getExerciseInfo(Long exerciseId, Long trainingId)
    {
        List<ParametrizedExercise> parametrizedExerciseList = parametrizedExerciseRepository.
                getParametrizedExercisesByTraining(trainingRepository.getTrainingById(trainingId));
        Exercise exercise = exerciseRepository.getExerciseById(exerciseId);
        List<SeriesInfo> sersiesInfo = new LinkedList<>();
        float caloriesSum;
        for (ParametrizedExercise parametrizedExercise:
             parametrizedExerciseList) {
            caloriesSum=0;
            if(parametrizedExercise.getExercise()==exercise){
                if(!checIfAllParametersAreNull(parametrizedExercise))
                {
                    for (ParameterInfo parameterInfo:
                         exerciseService.getParametrizedExerciseParametersInfo(parametrizedExercise)) {
                        caloriesSum+=parameterInfo.getCount()*parametrizedExercise.getExercise().getCalories();
                    }
                    sersiesInfo.add(new SeriesInfo(parametrizedExercise.getId(),
                            exerciseService.getParametrizedExerciseParametersInfo(parametrizedExercise), caloriesSum));
                }
            }
        }

        return new ExerciseInfo(exerciseId, exercise.getName(), exercise.getDescription(), sersiesInfo);
    }


    public ExerciseInfo getExerciseInfoForm(Long exerciseId, Long trainingId)
    {
        List<ParametrizedExercise> parametrizedExerciseList = parametrizedExerciseRepository.
                getParametrizedExercisesByTrainingForm(trainingFormRepository.getTrainingFormById(trainingId));
        Exercise exercise = exerciseRepository.getExerciseById(exerciseId);
        float caloriesSum;
        List<SeriesInfo> sersiesInfo = new LinkedList<>();
        for (ParametrizedExercise parametrizedExercise:
                parametrizedExerciseList) {
            caloriesSum=0;
            if(parametrizedExercise.getExercise()==exercise){
                for (ParameterInfo parameterInfo:
                        exerciseService.getParametrizedExerciseParametersInfo(parametrizedExercise)) {
                    caloriesSum+=parameterInfo.getCount()*parametrizedExercise.getExercise().getCalories();
                }
                sersiesInfo.add(new SeriesInfo(parametrizedExercise.getId(),
                        exerciseService.getParametrizedExerciseParametersInfo(parametrizedExercise),caloriesSum));
            }
        }

        return new ExerciseInfo(exerciseId, exercise.getName(), exercise.getDescription(), sersiesInfo);
    }



    public void deleteParametrizedExercise(ParametrizedExercise parametrizedExercise)
    {
        for (ValuedParameter valuedParameter:
             parametrizedExercise.getValuedParameterList()) {
            valuedParameterService.deleteValuedParameter(valuedParameter);
        }
        parametrizedExerciseRepository.delete(parametrizedExercise);
    }

    public ParametrizedExercise getParametrizedExercise(Long trainingId, Long exerciseId)
    {
        return parametrizedExerciseRepository.getParametrizedExerciseById(trainingRepository
                .getTrainingById(trainingId)
                .getParametrizedExerciseList().stream()
                .filter(paramExercise -> exerciseId.equals(paramExercise.getExercise().getId()))
                .findAny()
                .orElse(null)
                .getId());
    }

    public ParametrizedExercise getParametrizedExerciseFromForm(Long trainingId, Long exerciseId)
    {
        return parametrizedExerciseRepository.getParametrizedExerciseById(trainingFormRepository
                .getTrainingFormById(trainingId)
                .getParametrizedExerciseList().stream()
                .filter(paramExercise -> exerciseId.equals(paramExercise.getExercise().getId()))
                .findAny()
                .orElse(null)
                .getId());
    }


    public List<ParametrizedExercise> getParametrizedExercises(Long trainingId, Long trainingFormId){
        if(trainingFormId != null){
            return parametrizedExerciseRepository
                    .findAll()
                    .stream()
                    .filter(c -> c.getTrainingForm() != null && c.getTrainingForm().getId().equals(trainingFormId))
                    .collect(Collectors.toList());
        }
        return parametrizedExerciseRepository
                .findAll()
                .stream()
                .filter(c -> c.getTraining() != null && c.getTraining().getId().equals(trainingId))
                .collect(Collectors.toList());
    }

    public ParametrizedExercise getParametrizedExercise(Long parametrizedExerciseId)
    {
        return parametrizedExerciseRepository.getParametrizedExerciseById(parametrizedExerciseId);
    }


    public boolean checIfAllParametersAreNull(ParametrizedExercise parametrizedExercise)
    {
        for (ValuedParameter valuedParameter:
             parametrizedExercise.getValuedParameterList()) {
            if(valuedParameter.getValue()!=0)
                return false;
        }
        return true;
    }

}
