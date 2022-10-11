package pl.edu.wat.aplikacjatreningowa.models.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ValuedParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer value;

    @ManyToOne
    private ParametrizedExercise parametrizedExercise;

    @ManyToOne
    private Parameter parameter;

}
