package pl.edu.wat.aplikacjatreningowa.models.front;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingFormData {
    private String name;
    private String description;
    private String fromDate;
    private String toDate;
}
