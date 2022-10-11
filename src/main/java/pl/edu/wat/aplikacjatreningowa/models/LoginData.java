package pl.edu.wat.aplikacjatreningowa.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginData
{
    private String login;
    private String password;
}
