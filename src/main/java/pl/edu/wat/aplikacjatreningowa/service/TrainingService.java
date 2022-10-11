package pl.edu.wat.aplikacjatreningowa.service;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.edu.wat.aplikacjatreningowa.models.UserAccount;
import pl.edu.wat.aplikacjatreningowa.models.front.ExerciseInfo;
import pl.edu.wat.aplikacjatreningowa.models.front.SeriesInfo;
import pl.edu.wat.aplikacjatreningowa.models.front.TrainingData;
import pl.edu.wat.aplikacjatreningowa.models.front.TrainingInfo;
import pl.edu.wat.aplikacjatreningowa.models.main.Exercise;
import pl.edu.wat.aplikacjatreningowa.models.main.ParametrizedExercise;
import pl.edu.wat.aplikacjatreningowa.models.main.Training;
import pl.edu.wat.aplikacjatreningowa.repository.TrainingRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class TrainingService {
    TrainingRepository trainingRepository;
    UserService userService;
    ParametrizedExerciseService parametrizedExerciseService;
    ExerciseService exerciseService;

    public TrainingService(@Lazy TrainingRepository trainingRepository, @Lazy UserService userService, @Lazy ParametrizedExerciseService parametrizedExerciseService, @Lazy ExerciseService exerciseService) {
        this.trainingRepository = trainingRepository;
        this.userService = userService;
        this.parametrizedExerciseService = parametrizedExerciseService;
        this.exerciseService = exerciseService;
    }

    public boolean addTraining(TrainingData trainingData, String login)
    {
        Training training = new Training();
        training.setName(trainingData.getTrainingName());
        training.setDescription(trainingData.getTrainingDescription());
        training.setUser(userService.getUserAccount(login));
        training.setParametrizedExerciseList(new LinkedList<>());
        trainingRepository.save(training);

        return true;
    }

    public Training addTrainingTest(TrainingData trainingData)
    {
        Training training = new Training();
        training.setName(trainingData.getTrainingName());
        training.setDescription(trainingData.getTrainingDescription());
        return trainingRepository.save(training);
    }

    public void deleteTrainingTest(Training training)
    {
        trainingRepository.delete(training);
    }


    public void deleteTraining(Training training, UserAccount activeUser)
    {
        if(userService.isValidUser(activeUser, training.getUser()))
        {
            for (ParametrizedExercise parametrizedExercise:
                    training.getParametrizedExerciseList()) {
                parametrizedExerciseService.deleteParametrizedExercise(parametrizedExercise);
            }
            trainingRepository.delete(training);
        }
        else throw new IllegalStateException("This user cant delete this training");
    }



    public List<TrainingInfo> getUserTrainigs(String login)
    {
        List<Training> userTrainingsDB= userService.getUserTrainings(login);
        List<TrainingInfo> userTrainingInfo = new LinkedList<>();
        for (Training trainingDB:
             userTrainingsDB) {
            userTrainingInfo.add(getTrainingInfo(trainingDB.getId(), trainingDB.getUser()));
        }
        return userTrainingInfo;
    }


    public List<ExerciseInfo> getTrainingExercisesInfo(Training training)
    {

        List<Exercise> exerciseList = getTrainingExercises(training, training.getUser());
        List<ExerciseInfo> exercisesInfoList = new LinkedList<>();
        for (Exercise exercise:
                exerciseList) {
            exercisesInfoList
                    .add(parametrizedExerciseService
                            .getExerciseInfo(exercise.getId(), training.getId()));
        }
        return exercisesInfoList;
    }


    public TrainingInfo getTrainingInfo(Long id, UserAccount activeUser)
    {
        Training trainingDB = trainingRepository.getTrainingById(id);
        if(userService.isValidUser(trainingDB.getUser(), activeUser))
        {
            return new TrainingInfo(trainingDB.getId(), trainingDB.getName(),
                    trainingDB.getDescription(), getTrainingExercisesInfo(trainingDB));
        }
        else throw new IllegalStateException("This user cant get this training");
    }

    public Training updateTraining(Long id, String trainingName, String trainingDescription)
    {
        Training trainingDB = trainingRepository.getTrainingById(id);
        trainingDB.setName(trainingName);
        trainingDB.setDescription(trainingDescription);
        return trainingRepository.save(trainingDB);
    }

    public Training getTraining(Long id)
    {
        return trainingRepository.getTrainingById(id);
    }

    public List<Exercise> getTrainingExercises(Training training, UserAccount activeUser)
    {
        if(userService.isValidUser(activeUser, training.getUser()))
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
                            .add(exerciseService
                                    .getExerciseByName(parametrizedExercise
                                            .getExercise()
                                            .getName())
                            );
                }
            }
            return trainingExerciseList;
        }
        else throw new IllegalStateException("This user cant get this training exercises");
    }


    public Training getTrainingById(Long trainingId)
    {
        return trainingRepository.getTrainingById(trainingId);
    }


}
