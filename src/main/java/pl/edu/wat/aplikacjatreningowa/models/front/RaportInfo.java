package pl.edu.wat.aplikacjatreningowa.models.front;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RaportInfo {
    private String trainingName;
    private LocalDateTime dateFrom;
    private Long trainingTime;
    private int exerciseCount;
    private int weightSum;
    private float caloriesSum;
    private List<String> exerciseNames;
}
