package pl.edu.wat.aplikacjatreningowa.models.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.wat.aplikacjatreningowa.models.UserAccount;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TrainingForm {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    @OneToMany(mappedBy = "trainingForm")
    private List<ParametrizedExercise> parametrizedExerciseList;

    @ManyToOne
    private UserAccount user;

}
