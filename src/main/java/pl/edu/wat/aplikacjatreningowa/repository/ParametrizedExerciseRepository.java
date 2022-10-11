package pl.edu.wat.aplikacjatreningowa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.wat.aplikacjatreningowa.models.main.ParametrizedExercise;
import pl.edu.wat.aplikacjatreningowa.models.main.Training;
import pl.edu.wat.aplikacjatreningowa.models.main.TrainingForm;


import java.util.List;

public interface ParametrizedExerciseRepository extends JpaRepository<ParametrizedExercise, Long> {
    ParametrizedExercise getParametrizedExerciseById(Long id);
    List<ParametrizedExercise> getParametrizedExercisesByTraining(Training training);
    List<ParametrizedExercise> getParametrizedExercisesByTrainingForm(TrainingForm training);

}
