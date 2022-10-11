package pl.edu.wat.aplikacjatreningowa;

import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.wat.aplikacjatreningowa.models.front.TrainingData;
import pl.edu.wat.aplikacjatreningowa.models.main.Training;
import pl.edu.wat.aplikacjatreningowa.repository.TrainingRepository;
import pl.edu.wat.aplikacjatreningowa.service.TrainingService;
import pl.edu.wat.aplikacjatreningowa.service.UserService;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@NoArgsConstructor
public class UnitTests {

    @Test
    public void shouldHashProperly(){
        UserService userService = new UserService(new BCryptPasswordEncoder(),
                null, null, null,
                null,null,
                null,null,
                null,null);
        String password = userService.hashPassword("haslo");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertTrue(encoder.matches("haslo", password));
    }

    private final TrainingRepository trainingRepository = Mockito.mock(TrainingRepository.class);

    @Test
    public void shouldDeleteTraining(){
        TrainingService trainingService = new TrainingService(trainingRepository, null, null, null);
        TrainingData training = new TrainingData();
        training.setTrainingName("Training test");
        training.setTrainingDescription("No description");
        Training trainingToRemove = trainingService.addTrainingTest(training);
        trainingService.deleteTrainingTest(trainingToRemove);
        assertEquals(List.of(),trainingRepository.findAll());
    }

}
