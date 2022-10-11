package pl.edu.wat.aplikacjatreningowa.controllers.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
public class TrainingFromDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
}
