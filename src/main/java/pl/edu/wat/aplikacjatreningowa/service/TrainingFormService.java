package pl.edu.wat.aplikacjatreningowa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wat.aplikacjatreningowa.models.UserAccount;
import pl.edu.wat.aplikacjatreningowa.models.front.ExerciseInfo;
import pl.edu.wat.aplikacjatreningowa.models.front.TrainingFormData;
import pl.edu.wat.aplikacjatreningowa.models.front.TrainingFormInfo;
import pl.edu.wat.aplikacjatreningowa.models.main.*;
import pl.edu.wat.aplikacjatreningowa.repository.TrainingFormRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingFormService {

    public final TrainingFormRepository trainingFormRepository;
    private final UserService userService;
    private final ParametrizedExerciseService parametrizedExerciseService;
    private final ExerciseService exerciseService;
    private final TrainingService trainingService;
    private final ValuedParameterService valuedParameterService;


    public List<TrainingFormInfo> getUserTrainingForms(UserAccount userAccount)
    {
        List<TrainingForm> userTrainingsDB = userService.getUserTrainingForms(userAccount.getLogin());
        List<TrainingFormInfo> userTrainingInfo = new LinkedList<>();
        for (TrainingForm trainingDB:
                userTrainingsDB) {
            userTrainingInfo.add(getTrainingFormInfo(trainingDB.getId(), userAccount));
        }
        return userTrainingInfo;
    }

    public TrainingFormInfo getTrainingFormInfo(Long id, UserAccount activeUser)
    {
        TrainingForm trainingDB = trainingFormRepository.getTrainingFormById(id);
        if(userService.isValidUser(trainingDB.getUser(), activeUser))
        return new TrainingFormInfo(trainingDB.getId(), trainingDB.getName(),
                trainingDB.getDescription(), trainingDB.getFromDate(), trainingDB.getToDate(),
                getTrainingFormExercisesInfo(trainingDB));
        else throw new IllegalStateException("This user cant get this training");
    }

    public List<ExerciseInfo> getTrainingFormExercisesInfo(TrainingForm training)
    {
        List<Exercise> exerciseList = getTrainingFormExercises(training);
        List<ExerciseInfo> exercisesInfoList = new LinkedList<>();
        for (Exercise exercise:
                exerciseList) {
            exercisesInfoList
                    .add(parametrizedExerciseService
                            .getExerciseInfoForm(exercise.getId(), training.getId()));
        }
        return exercisesInfoList;
    }

    public List<Exercise> getTrainingFormExercises(TrainingForm training)
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


    public TrainingForm addTrainingForm(TrainingFormData trainingFormData, String login) {
        TrainingForm trainingForm = new TrainingForm();
        trainingForm.setName(trainingFormData.getName());
        trainingForm.setDescription(trainingFormData.getDescription());
        trainingForm.setFromDate(
                LocalDateTime.parse(trainingFormData.getFromDate(),
                        DateTimeFormatter.ISO_DATE_TIME));
        trainingForm.setToDate(
                LocalDateTime.parse(trainingFormData.getToDate(),
                        DateTimeFormatter.ISO_DATE_TIME));
        trainingForm.setUser(userService.getUserAccount(login));
        trainingForm.setParametrizedExerciseList(new LinkedList<>());

        return trainingFormRepository.save(trainingForm);
    }

    public TrainingForm getTrainingFormById(Long trainingId)
    {
        return trainingFormRepository.getTrainingFormById(trainingId);
    }

    public TrainingForm updateTrainingForm(Long id, String trainingName, String trainingDescription, String trainingFromDate, String trainingToDate) {
        TrainingForm trainingDB = trainingFormRepository.getTrainingFormById(id);
        trainingDB.setName(trainingName);
        trainingDB.setDescription(trainingDescription);
        trainingDB.setFromDate(LocalDateTime.parse(trainingFromDate, DateTimeFormatter.ISO_DATE_TIME));
        trainingDB.setToDate(LocalDateTime.parse(trainingToDate, DateTimeFormatter.ISO_DATE_TIME));
        return trainingFormRepository.save(trainingDB);
    }

    public void deleteTrainingForm(TrainingForm training, UserAccount activeUser) {
        if(userService.isValidUser(activeUser, training.getUser())) {
            for (ParametrizedExercise parametrizedExercise :
                    training.getParametrizedExerciseList()) {
                parametrizedExerciseService.deleteParametrizedExercise(parametrizedExercise);
            }
            trainingFormRepository.delete(training);
        }
        else throw new IllegalStateException("This user cant delete this training");
    }

    public TrainingForm getTrainingForm(Long id) {
        return trainingFormRepository.getTrainingFormById(id);
    }

    public void exercisesFromTrainingToTrainingForm(Long formID, Long trainingID) {
        TrainingForm trainingForm = getTrainingForm(formID);

        List<ParametrizedExercise> exerciseList = trainingService.parametrizedExerciseService.getParametrizedExercises(trainingID, null);
        for(ParametrizedExercise exercise : exerciseList){
            ParametrizedExercise newExercise = new ParametrizedExercise();
            newExercise.setExercise(exercise.getExercise());
            newExercise.setTraining(null);
            newExercise.setTrainingForm(trainingForm);
            List<ValuedParameter> valuedParameters = valuedParameterService.getParametrizedExerciseValuedParameters(exercise);
            if(valuedParameters.isEmpty()) continue;
            parametrizedExerciseService.parametrizedExerciseRepository.save(newExercise);

            for(pl.edu.wat.aplikacjatreningowa.models.main.ValuedParameter param : valuedParameters){
                ValuedParameter newParameter = new ValuedParameter();
                newParameter.setParameter(param.getParameter());
                newParameter.setValue(param.getValue());
                newParameter.setParametrizedExercise(newExercise);
                valuedParameterService.valuedParameterRepository.save(newParameter);
            }
        }
    }

    public int cyclicTrainingForm(TrainingForm trainingForm, UserAccount userAccount) {
        if(userService.isValidUser(userAccount, trainingForm.getUser())) {
            int count = 0;
            for (int i = 1; i <= 3; i++) {
                List<TrainingForm> trs = trainingFormRepository.findAllBetweenDates(userAccount.getId(),
                        trainingForm.getFromDate().plusWeeks(i), trainingForm.getToDate().plusWeeks(i));
                if(trs.size() > 0) continue;
                TrainingFormData tfd = new TrainingFormData(
                        trainingForm.getName(),
                        trainingForm.getDescription(),
                        trainingForm.getFromDate().plusWeeks(i).toString(),
                        trainingForm.getToDate().plusWeeks(i).toString());
                TrainingForm newTf = addTrainingForm(tfd, userAccount.getLogin());
                List<ParametrizedExercise> exerciseList = parametrizedExerciseService.getParametrizedExercises(null, trainingForm.getId());
                List<ParametrizedExercise> peList = new LinkedList<>();
                for(ParametrizedExercise exercise : exerciseList){
                    ParametrizedExercise parametrizedExercise = new ParametrizedExercise();
                    parametrizedExercise.setExercise(exercise.getExercise());
                    parametrizedExercise.setTrainingForm(newTf);
                    parametrizedExercise.setTraining(null);
                    parametrizedExercise.setValuedParameterList(null);
                    peList.add(parametrizedExercise);
                    parametrizedExerciseService.parametrizedExerciseRepository.save(parametrizedExercise);
                }
                newTf.setParametrizedExerciseList(peList);
                System.out.println(newTf.getParametrizedExerciseList());
                trainingFormRepository.save(newTf);
                count++;
            }
            return count;
        }
        else throw new IllegalStateException("This user cant use this training");
    }

}
