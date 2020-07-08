package daniil.tm2.service;

import daniil.tm2.api.repository.IUserRepository;
import daniil.tm2.entity.TMUser;
import daniil.tm2.entity.role.UserRole;
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
        TMUser user = userRepository.findById(userName).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return User.builder()
                .username(user.getName())
                .password(user.getPasswordHash())
                .roles(user.getRoles().stream().map(UserRole::getName).toArray(String[]::new))
                .build();
    }

}
