package pl.edu.wat.aplikacjatreningowa.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wat.aplikacjatreningowa.models.front.ExerciseInfo;
import pl.edu.wat.aplikacjatreningowa.models.front.ParameterInfo;
import pl.edu.wat.aplikacjatreningowa.models.front.SeriesInfo;
import pl.edu.wat.aplikacjatreningowa.models.main.*;
import pl.edu.wat.aplikacjatreningowa.repository.ExerciseRepository;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class ExerciseService {

    ExerciseRepository exerciseRepository;
    TrainingService trainingService;

    public void addExercise(String name, String description, List<Parameter> acceptableParameters)
    {
        Exercise exercise = new Exercise();
        exercise.setName(name);
        exercise.setDescription(description);
        exercise.setAcceptableParameters(acceptableParameters);
        exerciseRepository.save(exercise);
    }

    public List<Exercise> getAllExercises()
    {
        return exerciseRepository.findAll();
    }


    public List<Exercise> getAvailableExercises(Training training)
    {
        List<Exercise> allExercises = getAllExercises();
        List<Exercise> trainingExerxcises = trainingService.getTrainingExercises(training, training.getUser());
        for (Exercise exercise:
             trainingExerxcises) {
            allExercises.remove(exercise);
        }
        return allExercises;
    }

    public List<Exercise> getAvailableExercises(TrainingForm training)
    {
        List<Exercise> allExercises = getAllExercises();
        List<Exercise> trainingExerxcises = getTrainingFormExercisesWorking(training);
        for (Exercise exercise:
                trainingExerxcises) {
            allExercises.remove(exercise);
        }
        return allExercises;
    }

    public List<Exercise> getTrainingFormExercisesWorking(TrainingForm training)
    {
        List<ParametrizedExercise> parametrizedExerciseList = training.getParametrizedExerciseList();
        List<Exercise> trainingExerciseList = new LinkedList<>();
        for (ParametrizedExercise parametrizedExercise:
                parametrizedExerciseList) {
            if(!trainingExerciseList
                    .stream()
                    .map(Exercise::getName)
                    .filter(parametrizedExercise.getExercise().getName()::equals)
                    .findFirst()
                    .isPresent()
            ){
                trainingExerciseList
                        .add(getExerciseByName(parametrizedExercise
                                        .getExercise()
                                        .getName())
                        );
            }
        }
        return trainingExerciseList;
    }
    public Exercise getExerciseByName(String name)
    {
        return exerciseRepository.getExerciseByName(name);
    }

    public Exercise getExerciseById(Long id)
    {
        return exerciseRepository.getExerciseById(id);
    }

    public List<ParameterInfo> getParametrizedExerciseParametersInfo(ParametrizedExercise parametrizedExercise)
    {
        List<ValuedParameter> parametrizedExerciseValuedParameterList =parametrizedExercise.getValuedParameterList();
        List<ParameterInfo> parameterInfoList = new LinkedList<>();
        for (ValuedParameter valuedParameter:
                parametrizedExerciseValuedParameterList) {
            parameterInfoList
                    .add(new ParameterInfo(valuedParameter.getParameter().getName(), valuedParameter.getValue()));
        }
        return parameterInfoList;
    }

    public List<ParameterInfo> getExerciseParametersInfo(Long exerciseId)
    {
        List<Parameter> exerciseParameters = exerciseRepository.getExerciseById(exerciseId).getAcceptableParameters();
        List<ParameterInfo> parameterInfoList = new LinkedList<>();
        for (Parameter parameter:
             exerciseParameters) {
            parameterInfoList.add(new ParameterInfo(parameter.getName().toString(), 0));
        }
        return parameterInfoList;
    }

}
