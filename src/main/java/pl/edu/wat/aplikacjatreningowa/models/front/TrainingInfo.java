package pl.edu.wat.aplikacjatreningowa.models.front;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrainingInfo {
    private Long id;
    private String trainingName;
    private String trainingDescription;
    private List<ExerciseInfo> exerciseInfoList;


    @Override
    public int hashCode() {
        return Objects.hash(trainingName, trainingDescription, exerciseInfoList);
    }
}
