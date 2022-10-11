package pl.edu.wat.aplikacjatreningowa.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.wat.aplikacjatreningowa.models.main.TrainingForm;


import java.time.LocalDateTime;
import java.util.List;

public interface TrainingFormRepository extends JpaRepository<TrainingForm, Long> {

    @Query("select tf from TrainingForm tf")
    List<TrainingForm> findAllTrainingForms(Pageable page);


    @Query("select tf from TrainingForm tf where tf.fromDate <= ?3 and tf.toDate >= ?2 and tf.user.id = ?1")
    List<TrainingForm> findAllBetweenDates(Long userid, LocalDateTime beginning, LocalDateTime ending);

    TrainingForm getTrainingFormById(Long id);
}
