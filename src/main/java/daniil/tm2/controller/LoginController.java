package daniil.tm2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/perform_login", method = RequestMethod.POST)
public class LoginController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping
    public String login(
            @RequestParam String username,
            @RequestParam String password
            ) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("NORMAL_USER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                Optional.ofNullable(username)
                        .filter(name -> !name.isEmpty())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid user name!")),
                Optional.ofNullable(password)
                        .filter(pw -> !pw.isEmpty())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid password!")), 
                authorities);
        try {
            authentication = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            authentication = null;
        }
        return authentication != null && authentication.isAuthenticated()
                ? "redirect:/domains"
                : "redirect:/login_error";
    }
    
}
