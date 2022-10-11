package pl.edu.wat.aplikacjatreningowa.models.front;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TrainingFormInfoStringed {
    private Long id;
    private String name;
    private String description;
    private String fromDate;
    private String toDate;
    private List<ExerciseInfo> exerciseInfoList;

    @Override
    public int hashCode() {
        return Objects.hash(name, description, fromDate, toDate, exerciseInfoList);
    }
}