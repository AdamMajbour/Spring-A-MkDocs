package pl.edu.wat.aplikacjatreningowa.models.main;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private float calories;

    @OneToMany(mappedBy = "exercise")
    private List<ParametrizedExercise> parametrizedExerciseList;

    @ManyToMany
    @JoinTable(
            joinColumns = {@JoinColumn(name="EXERCISE_ID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name="PARAMETER_ID", referencedColumnName = "id")}
    )
    private List<Parameter> acceptableParameters;

}


