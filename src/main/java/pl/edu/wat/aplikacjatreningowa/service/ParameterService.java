package pl.edu.wat.aplikacjatreningowa.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wat.aplikacjatreningowa.models.front.ParameterInfo;
import pl.edu.wat.aplikacjatreningowa.models.front.Parameters;
import pl.edu.wat.aplikacjatreningowa.models.front.SeriesData;
import pl.edu.wat.aplikacjatreningowa.models.front.SeriesInfo;
import pl.edu.wat.aplikacjatreningowa.models.main.ParametrizedExercise;
import pl.edu.wat.aplikacjatreningowa.repository.ParameterRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ParameterService {

    ValuedParameterService valuedParameterService;
    ParameterRepository parameterRepository;

    public void addToValuedParameters(Parameters parameters,
                                      ParametrizedExercise parametrizedExercise)
    {
        if(parameters.getRepsCount()!=0)
            valuedParameterService
                    .addValuedParameter(parameterRepository
                            .findParameterByName("Powtórzenia"),
                            parameters.getRepsCount(), parametrizedExercise);
        if(parameters.getTimeCount()!=0)
            valuedParameterService
                    .addValuedParameter(parameterRepository
                            .findParameterByName("Czas"),
                            parameters.getTimeCount(), parametrizedExercise);
        if(parameters.getDistanceCount()!=0)
            valuedParameterService
                    .addValuedParameter(parameterRepository
                            .findParameterByName("Dystans"),
                            parameters.getDistanceCount(), parametrizedExercise);
        if(parameters.getCaloriesCount()!=0)
            valuedParameterService
                    .addValuedParameter(parameterRepository
                            .findParameterByName("Kalorie spalane"),
                            parameters.getCaloriesCount(), parametrizedExercise);
        if(parameters.getWeightCount()!=0)
            valuedParameterService
                    .addValuedParameter(parameterRepository
                            .findParameterByName("Obciążenie"),
                            parameters.getWeightCount(), parametrizedExercise);

    }

}
