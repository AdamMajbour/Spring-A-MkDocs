package pl.edu.wat.aplikacjatreningowa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.wat.aplikacjatreningowa.auth.MyPrincipal;
import pl.edu.wat.aplikacjatreningowa.models.UserAccount;
import pl.edu.wat.aplikacjatreningowa.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserAccount userAccount = userRepository.findByLogin(s);
        if(userAccount == null){
            throw new UsernameNotFoundException("N0T F0UND");
        }
        return new MyPrincipal(userAccount);
    }
}
