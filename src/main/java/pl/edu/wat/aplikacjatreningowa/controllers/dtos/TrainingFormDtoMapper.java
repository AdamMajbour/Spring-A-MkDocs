package pl.edu.wat.aplikacjatreningowa.controllers.dtos;

import pl.edu.wat.aplikacjatreningowa.models.main.TrainingForm;

import java.util.List;
import java.util.stream.Collectors;

public class TrainingFormDtoMapper {

    private TrainingFormDtoMapper(){
    }

    public static TrainingFromDto mapToTrainingFormDto(TrainingForm trainingForm){
        return TrainingFromDto.builder()
                .id(trainingForm.getId())
                .name(trainingForm.getName())
                .description(trainingForm.getDescription())
                .fromDate(trainingForm.getFromDate())
                .toDate(trainingForm.getToDate())
                .build();
    }

    public static List<TrainingFromDto> mapToTrainingFormDtos(List<TrainingForm> trainingFormList){
        return trainingFormList.stream().map(TrainingFormDtoMapper::mapToTrainingFormDto).collect(Collectors.toList());
    }

}
