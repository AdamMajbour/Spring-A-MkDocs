package pl.edu.wat.aplikacjatreningowa.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountInfoRequest {
    private Long id;
    private String login;
    private int height;
    private int weight;
}
