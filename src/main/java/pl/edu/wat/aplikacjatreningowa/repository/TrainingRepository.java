package pl.edu.wat.aplikacjatreningowa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.wat.aplikacjatreningowa.models.main.Training;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    Training getTrainingById(Long id);
}
