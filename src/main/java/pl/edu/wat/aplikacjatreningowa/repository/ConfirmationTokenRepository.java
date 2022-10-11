package pl.edu.wat.aplikacjatreningowa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.aplikacjatreningowa.models.ConfirmationToken;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
//
    Optional<ConfirmationToken> findByToken(String token);
    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c " +
            "SET c.confirmedDateTime = ?2 " +
            "WHERE c.token = ?1")
    int updateConfirmedAt(String token,
                          LocalDateTime confirmedDateTime);
}
