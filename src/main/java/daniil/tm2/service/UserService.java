package daniil.tm2.service;

import daniil.tm2.api.repository.IRoleRepository;
import daniil.tm2.api.repository.IUserRepository;
import daniil.tm2.api.service.IUserService;
import daniil.tm2.entity.TMUser;
import daniil.tm2.entity.role.UserRole;
import java.time.LocalDate;
import java.util.Collections;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        TMUser user = userRepository.findById(loginName).orElse(null);
        if (user != null) {
            return null;
        }
        UserRole role = roleRepository.findById(roleName).orElse(null);
        if (role == null) {
            role = new UserRole();
            role.setName(roleName);
            roleRepository.save(role);
        }
        user = new TMUser();
        user.setDateJoined(LocalDate.now());
        user.setName(loginName);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
        return user;
    }

}
