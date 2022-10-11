package pl.edu.wat.aplikacjatreningowa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.aplikacjatreningowa.models.UserAccount;
import pl.edu.wat.aplikacjatreningowa.models.main.Training;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> {

    UserAccount findByEmail(String email);
    UserAccount findByLogin(String login);



}
