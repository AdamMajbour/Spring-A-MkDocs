package pl.edu.wat.aplikacjatreningowa.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.wat.aplikacjatreningowa.controllers.dtos.AccountInfoRequest;
import pl.edu.wat.aplikacjatreningowa.controllers.dtos.PasswordResponse;
import pl.edu.wat.aplikacjatreningowa.models.ConfirmationPasswordToken;
import pl.edu.wat.aplikacjatreningowa.models.ConfirmationToken;
import pl.edu.wat.aplikacjatreningowa.models.front.RegistrationData;
import pl.edu.wat.aplikacjatreningowa.models.UserAccount;
import pl.edu.wat.aplikacjatreningowa.models.main.Training;
import pl.edu.wat.aplikacjatreningowa.models.main.TrainingForm;
import pl.edu.wat.aplikacjatreningowa.repository.ConfirmationPasswordTokenRepository;
import pl.edu.wat.aplikacjatreningowa.repository.ConfirmationTokenRepository;
import pl.edu.wat.aplikacjatreningowa.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final TrainingService trainingService;
    private final ConfirmationPasswordTokenRepository confirmationPasswordTokenRepository;
    private final ConfirmationPasswordTokenService confirmationPasswordTokenService;
    private final MyUserDetailsService myUserDetailsService;
    private EntityManager em;

    public List<UserAccount> getUserAccounts(){
        return userRepository.findAll();
    }

    public UserAccount getUserAccount(String login){return userRepository.findByLogin(login);}

    public void addUserAccount(RegistrationData registrationData){

        UserAccount userAccount = new UserAccount();
        userAccount.setName(registrationData.getName());
        userAccount.setSurname(registrationData.getSurname());
        userAccount.setLogin(registrationData.getLogin());
        userAccount.setEmail(registrationData.getEmail());
        userAccount.setPassword(registrationData.getPassword());
        userAccount.setAccountActive(Boolean.FALSE);
        userAccount.setDateRegistered(LocalDate.now());
        userAccount.setLastSeen(LocalDate.now());
        userAccount.setPassword(hashPassword(userAccount.getPassword()));
        userRepository.save(userAccount);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now().plusMinutes(30),
                LocalDateTime.now(), userAccount);

        confirmationTokenRepository.save(confirmationToken);
        emailService.sendEmail(userAccount, confirmationToken, "Aby potwiedzić swój e-mail kliknij w link poniżej:\n", "Potiwerdzenie maila");

    }

    @Transactional
    public String confirmEmail(String token)
    {
        UserAccount userAccount;
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).
                orElseThrow(()->new IllegalStateException("Token not found"));

        if(confirmationToken.getConfirmedDateTime() !=null)
        {
            return "Twój email jest już potwiedzony";
        }
        if(confirmationToken.getExpiredDateTime().isBefore(LocalDateTime.now()))
            return "Token stracił ważność";

        confirmationTokenService.setConfirmedAt(confirmationToken, token);
        userAccount = userRepository.findByEmail(confirmationToken.getUserAccount().getEmail());
        userAccount.setAccountActive(true);
        userRepository.save(userAccount);
        System.out.println(confirmationToken.getUserAccount());

        return "Twój email został zweryfikowany";
    }

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    public boolean isEmailExist(String email)
    {
        if(userRepository.findByEmail(email)== null)
            return false;
        else return true;
    }

    public boolean isLoginExist(String login)
    {
        if (login.contains("@"))
        {
            if ((userRepository.findByEmail(login)== null)) return false;
            else return true;
        }else if(userRepository.findByLogin(login)== null) return false;
        return true;
    }

    public boolean loginPasswVerif(String login, String password)
    {
        UserAccount userAccount;
        if (login.contains("@")) userAccount = userRepository.findByEmail(login);
        else userAccount = userRepository.findByLogin(login);
        if (passwordEncoder.matches(password, userAccount.getPassword())) return true;
        return false;
    }

    public boolean isAccountActivated(String login)
    {
        if (login.contains("@")) return userRepository.findByEmail(login).getAccountActive();
        return userRepository.findByLogin(login).getAccountActive();
    }

    public void deleteAccount(String userLogin)
    {
        UserAccount userAccount = userRepository.findByLogin(userLogin);
        for (Training training:
             userAccount.getTrainings()) {
            trainingService.deleteTraining(training, userAccount);
        }
        userRepository.delete(userAccount);
    }



    public void changeParameters(String userLogin, int userWeight, int userHeight)
    {
        UserAccount userAccount = userRepository.findByLogin(userLogin);
        userAccount.setWeight(userWeight);
        userAccount.setHeight(userHeight);
        userRepository.save(userAccount);
    }

    public void generateTokenToChangePassword(String userLogin, PasswordResponse passwordResponse)
    {
        UserAccount userAccount= userRepository.findByLogin(userLogin);
        String token = UUID.randomUUID().toString();
        ConfirmationPasswordToken confirmationPasswordToken = new ConfirmationPasswordToken(token, LocalDateTime.now().plusMinutes(30),
                LocalDateTime.now(), userAccount, passwordResponse.getPassword());
        System.out.println("haslo to :"+passwordResponse.getPassword());

        confirmationPasswordTokenRepository.save(confirmationPasswordToken);
        emailService.sendEmail(userAccount, confirmationPasswordToken, "Aby potwierdzić zmianę hasła kliknij w link poniżej:\n", "Zmiana hasła");
    }

    public String changePasword(String token)
    {
        ConfirmationPasswordToken confirmationPasswordToken = confirmationPasswordTokenRepository.findByToken(token)
                .orElseThrow(()->new IllegalStateException("Token not found"));


        if(confirmationPasswordToken.getConfirmedDateTime() !=null)
        {
            return "Twój email jest już potwiedzony";
        }
        if(confirmationPasswordToken.getExpiredDateTime().isBefore(LocalDateTime.now()))
            return "Token stracił ważność";

        confirmationPasswordTokenService.setConfirmedAt(confirmationPasswordToken, token);
        UserAccount userAccount = userRepository.findByEmail(confirmationPasswordToken.getUserAccount().getEmail());
        userAccount.setPassword(confirmationPasswordToken.getPassword());
        userRepository.save(userAccount);

        return "Twój email został zweryfikowany";
    }

    public List<Training> getUserTrainings(String login)
    {
        return userRepository.findByLogin(login).getTrainings();
    }


    public List<TrainingForm> getUserTrainingForms(String login)
    {
        return userRepository.findByLogin(login).getTrainingForms();
    }


    public boolean isValidUser(UserAccount user1, UserAccount user2)
    {
        if (user1==user2)
            return true;
        else return false;
    }

    public AccountInfoRequest getAccountInfo(String userLogin)
    {
        UserAccount userAccount = userRepository.findByLogin(userLogin);
        return new AccountInfoRequest(userAccount.getId(), userAccount.getLogin(), userAccount.getHeight(), userAccount.getWeight());
    }

}
