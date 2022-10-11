package pl.edu.wat.aplikacjatreningowa.service;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.tags.Param;
import pl.edu.wat.aplikacjatreningowa.models.main.Parameter;
import pl.edu.wat.aplikacjatreningowa.models.main.ParametrizedExercise;
import pl.edu.wat.aplikacjatreningowa.models.main.ValuedParameter;
import pl.edu.wat.aplikacjatreningowa.repository.ValuedParameterRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ValuedParameterService {

    ValuedParameterRepository valuedParameterRepository;

    public void addValuedParameter(Parameter parameter, int value, ParametrizedExercise parametrizedExercise)
    {
        ValuedParameter valuedParameter = new ValuedParameter();
        valuedParameter.setParameter(parameter);
        valuedParameter.setValue(value);
        valuedParameter.setParametrizedExercise(parametrizedExercise);
        valuedParameterRepository.save(valuedParameter);
    }


    public List<ValuedParameter> getParametrizedExerciseValuedParameters(ParametrizedExercise parametrizedExercise)
    {
        return valuedParameterRepository.findValuedParametersByParametrizedExercise(parametrizedExercise);
    }

    public void deleteValuedParameter(ValuedParameter valuedParameter)
    {
        valuedParameterRepository.delete(valuedParameter);
    }

    public ValuedParameter getValuedParameterById(Long id)
    {
        return valuedParameterRepository.findValuedParameterById(id);
    }
}
