package pl.edu.wat.aplikacjatreningowa.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.wat.aplikacjatreningowa.models.UserAccount;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class ConfirmationPasswordToken {

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_VERIFIED = "VERIFIED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private String status;
    private String password;
    private LocalDateTime expiredDateTime;
    private LocalDateTime issuedDateTime;
    private LocalDateTime confirmedDateTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    public ConfirmationPasswordToken(String token, LocalDateTime expiredDateTime, LocalDateTime issuedDateTime, UserAccount userAccount, String password) {
        this.token = token;
        this.expiredDateTime = expiredDateTime;
        this.issuedDateTime = issuedDateTime;
        this.userAccount = userAccount;
        this.password = password;
    }
}
