package pl.edu.wat.aplikacjatreningowa.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;

import pl.edu.wat.aplikacjatreningowa.models.main.Exercise;
import pl.edu.wat.aplikacjatreningowa.models.main.ParametrizedExercise;
import pl.edu.wat.aplikacjatreningowa.models.main.Training;
import pl.edu.wat.aplikacjatreningowa.models.main.ValuedParameter;
import pl.edu.wat.aplikacjatreningowa.repository.*;

import javax.persistence.EntityManager;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ParametrizedExerciseServiceTest {


    private final ParametrizedExerciseRepository parametrizedExerciseRepository = Mockito.mock(ParametrizedExerciseRepository.class);
    private final ExerciseService exerciseService =Mockito.mock(ExerciseService.class);
    private final TrainingRepository trainingRepository =Mockito.mock(TrainingRepository.class);
    private final ExerciseRepository exerciseRepository =Mockito.mock(ExerciseRepository.class);
    private final ParameterService parameterService= Mockito.mock(ParameterService.class);
    private final ValuedParameterService valuedParameterService =Mockito.mock(ValuedParameterService.class);
    private final TrainingFormRepository trainingFormRepository =Mockito.mock(TrainingFormRepository.class);



    @Captor
    private final ArgumentCaptor<ParametrizedExercise> prametrizedExerciseArgumentCaptor = ArgumentCaptor.forClass(ParametrizedExercise.class);

    private ParametrizedExerciseService parametrizedExerciseService;

    @BeforeEach
    public void setup() {
        parametrizedExerciseService = new ParametrizedExerciseService(parametrizedExerciseRepository, exerciseService, trainingRepository,exerciseRepository,parameterService,valuedParameterService,trainingFormRepository);
    }


    @Test
    @DisplayName("Should Add Parametrized Exercise")
    public void shouldAddParametrizedExercise()
    {
        Training training = new Training(123L, "TrainingName", "TrainingDesciprion", null, null);
        Exercise exercise = new Exercise(125L, "Exercisename", "Exercisedescription", 123, null, null);
        parametrizedExerciseService.addParametrizedExercise(training, null, exercise);
        Mockito.verify(parametrizedExerciseRepository, Mockito.times(1)).save(prametrizedExerciseArgumentCaptor.capture());

        assertThat(prametrizedExerciseArgumentCaptor.getValue().getExercise().getId()).isEqualTo(125L);
        assertThat(prametrizedExerciseArgumentCaptor.getValue().getExercise().getName()).isEqualTo("Exercisename");
        assertThat(prametrizedExerciseArgumentCaptor.getValue().getExercise().getDescription()).isEqualTo("Exercisedescription");
        assertThat(prametrizedExerciseArgumentCaptor.getValue().getExercise().getCalories()).isEqualTo(123);
        assertThat(prametrizedExerciseArgumentCaptor.getValue().getTraining().getId()).isEqualTo(123L);
        assertThat(prametrizedExerciseArgumentCaptor.getValue().getTraining().getName()).isEqualTo("TrainingName");
        assertThat(prametrizedExerciseArgumentCaptor.getValue().getTraining().getDescription()).isEqualTo("TrainingDesciprion");

    }


    @Test
    @DisplayName("Should Delete Parametrized Exercise")
    public void shouldDeteteParametrizedExercise()
    {
        List<ValuedParameter> valuedParameterList = new LinkedList<>();
        Training training = new Training(123L, "TrainingName", "TrainingDesciprion", null, null);
        Exercise exercise = new Exercise(125L, "Exercisename", "Exercisedescription", 123, null, null);
        ParametrizedExercise parametrizedExercise = new ParametrizedExercise(111L, exercise, training, null,valuedParameterList );
        parametrizedExerciseService.deleteParametrizedExercise(parametrizedExercise);

        assertTrue(parametrizedExerciseRepository.findById(111L).isEmpty());
    }

}
