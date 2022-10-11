package pl.edu.wat.aplikacjatreningowa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.aplikacjatreningowa.models.main.Parameter;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {
    Parameter findParameterByName(String name);
}
