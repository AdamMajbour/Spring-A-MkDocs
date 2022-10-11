package pl.edu.wat.aplikacjatreningowa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.aplikacjatreningowa.models.main.ParametrizedExercise;
import pl.edu.wat.aplikacjatreningowa.models.main.ValuedParameter;

import java.util.List;

@Repository
public interface ValuedParameterRepository extends JpaRepository<ValuedParameter, Long> {
    List<ValuedParameter> findValuedParametersByParametrizedExercise(ParametrizedExercise parametrizedExercise);

    ValuedParameter findValuedParameterById(Long id);

}
