package pl.edu.wat.aplikacjatreningowa.models.main;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Training {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "training")
    private List<ParametrizedExercise> parametrizedExerciseList;

    @ManyToOne
    private UserAccount user;

}
