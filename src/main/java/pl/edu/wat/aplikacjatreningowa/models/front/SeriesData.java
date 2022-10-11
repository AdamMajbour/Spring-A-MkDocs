package pl.edu.wat.aplikacjatreningowa.models.front;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeriesData {
    private List<ParameterInfo> parameterInfoList;
}
