package pl.edu.wat.aplikacjatreningowa.models.main;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;


import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class ParametrizedExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Exercise exercise;

    @ManyToOne
    private Training training;

    @ManyToOne
    private TrainingForm trainingForm;


    @OneToMany(mappedBy = "parametrizedExercise", cascade = CascadeType.ALL)
    private List<ValuedParameter> valuedParameterList;

}

