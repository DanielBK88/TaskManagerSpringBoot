package daniil.tm2.service;

import daniil.tm2.api.repository.IUserRepository;
import daniil.tm2.entity.role.UserRole;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@Transactional
public class TMUserDetailsService implements UserDetailsService {
    
    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return Optional.ofNullable(userName)
                .filter(n -> !n.isEmpty())
                .flatMap(n -> userRepository.findById(n))
                .flatMap(tmUser -> Optional.of(User.builder()
                        .username(tmUser.getName())
                        .password(tmUser.getPasswordHash())
                        .roles(tmUser.getRoles().stream().map(UserRole::getName).toArray(String[]::new))
                        .build())
                ).orElseThrow(() -> new UsernameNotFoundException("Invalid user name or no such user!"));
    }

}
