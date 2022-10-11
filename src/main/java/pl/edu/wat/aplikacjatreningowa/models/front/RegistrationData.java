package pl.edu.wat.aplikacjatreningowa.models.front;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationData
{
    private String login;
    private String password;
    private String email;
    private String name;
    private String surname;
}
