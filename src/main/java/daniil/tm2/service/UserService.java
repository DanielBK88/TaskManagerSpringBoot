package daniil.tm2.service;

import daniil.tm2.api.repository.IRoleRepository;
import daniil.tm2.api.repository.IUserRepository;
import daniil.tm2.api.service.IUserService;
import daniil.tm2.entity.TMUser;
import daniil.tm2.entity.role.UserRole;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;
    
    @Autowired
    private IRoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostConstruct
    private void initUsers() {
        addUser("Anton", "APW", "NORMAL_USER");
        addUser("Bert", "BPW", "NORMAL_USER");
    }
    
    @Override
    public TMUser addUser(String loginName, String password, String roleName) {
        return Optional.ofNullable(loginName)
                .filter(n -> !n.isEmpty())
                .filter(n -> !userRepository.findById(n).isPresent())
                .flatMap(n -> Optional.of(userRepository.save(new TMUser(
                        loginName,
                        Optional.ofNullable(password).filter(pw -> !pw.isEmpty())
                                .flatMap(pw -> Optional.of(passwordEncoder.encode(pw)))
                                .orElseThrow(() -> new IllegalArgumentException("Null or empty password!")),
                        Optional.ofNullable(roleName).filter(r -> !r.isEmpty())
                                .flatMap(r -> Optional.of(new UserRole(r)))
                                .flatMap(r -> Optional.of(Stream.of(r).collect(toList())))
                                .orElseThrow(() -> new IllegalArgumentException("Null or empty role name!")),
                        LocalDate.now()
                )))).orElse(null);
    }

}
