package pl.edu.wat.aplikacjatreningowa.models.front;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeriesInfo {
    private Long id;
    private List<ParameterInfo> parameterInfoList;
    private float calories;

    @Override
    public int hashCode() {
        return Objects.hash(id, parameterInfoList);
    }
}
