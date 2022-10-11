package pl.edu.wat.aplikacjatreningowa.models;

import lombok.*;
import pl.edu.wat.aplikacjatreningowa.models.main.ParametrizedExercise;
import pl.edu.wat.aplikacjatreningowa.models.main.Training;
import pl.edu.wat.aplikacjatreningowa.models.main.TrainingForm;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min=4, max=32, message = "Login powinien zawierać 4-32 znaków")
    private String login;
    @Size(min=8, max=64, message = "Hasło powinno zawierać 8-64 znaków")
    private String password;
    @Pattern(regexp = "^(.+)@(.+)$", message = "Niepoprawny format e-mail'a")
    private String email;
    private LocalDate dateRegistered;
    private LocalDate lastSeen;
    private Boolean accountActive;
    private String name;
    private String surname;
    private int height;
    private int weight;

    @OneToMany(mappedBy = "user")
    private List<Training> trainings;

    @OneToMany(mappedBy = "user")
    private List<TrainingForm> trainingForms;

}
