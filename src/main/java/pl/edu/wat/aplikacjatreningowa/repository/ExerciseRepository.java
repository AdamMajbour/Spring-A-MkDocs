package pl.edu.wat.aplikacjatreningowa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.wat.aplikacjatreningowa.models.main.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Exercise getExerciseById(Long id);
    Exercise getExerciseByName(String name);
}
