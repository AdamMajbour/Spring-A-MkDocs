package pl.edu.wat.aplikacjatreningowa.models.front;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrainingFormInfo {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private List<ExerciseInfo> exerciseInfoList;

    @Override
    public int hashCode() {
        return Objects.hash(name, description, fromDate, toDate, exerciseInfoList);
    }
}
