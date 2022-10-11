package pl.edu.wat.aplikacjatreningowa.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wat.aplikacjatreningowa.models.ConfirmationPasswordToken;
import pl.edu.wat.aplikacjatreningowa.models.ConfirmationToken;
import pl.edu.wat.aplikacjatreningowa.repository.ConfirmationPasswordTokenRepository;
import pl.edu.wat.aplikacjatreningowa.repository.ConfirmationTokenRepository;
import pl.edu.wat.aplikacjatreningowa.repository.UserRepository;

import javax.persistence.Access;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationPasswordTokenService {
    private final ConfirmationPasswordTokenRepository confirmationPasswordTokenRepository;


    public void setConfirmedAt(ConfirmationPasswordToken confirmationPasswordToken, String token) {
        confirmationPasswordToken.setConfirmedDateTime(LocalDateTime.now());
        confirmationPasswordTokenRepository.save(confirmationPasswordToken);
    }
}
