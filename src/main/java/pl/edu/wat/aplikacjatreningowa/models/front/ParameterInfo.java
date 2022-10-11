package pl.edu.wat.aplikacjatreningowa.models.front;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.wat.aplikacjatreningowa.models.main.ParameterEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParameterInfo {
    private String name;
    private int count;
}
