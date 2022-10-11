package pl.edu.wat.aplikacjatreningowa.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.edu.wat.aplikacjatreningowa.models.UserAccount;
import pl.edu.wat.aplikacjatreningowa.repository.ConfirmationTokenRepository;
import pl.edu.wat.aplikacjatreningowa.models.ConfirmationToken;
import pl.edu.wat.aplikacjatreningowa.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(ConfirmationToken confirmationToken, String token) {
        confirmationToken.setConfirmedDateTime(LocalDateTime.now());
        confirmationTokenRepository.save(confirmationToken);
    }



}
