package pl.edu.wat.aplikacjatreningowa.models.front;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class ExerciseInfo {
    private Long id;
    private String name;
    private String description;
    private List<SeriesInfo> seriesInfos;

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
