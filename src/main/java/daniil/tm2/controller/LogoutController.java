package daniil.tm2.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/perform_logout", method = RequestMethod.POST)
public class LogoutController {

    @GetMapping
    public String logout(HttpServletRequest request) {
        try {
            request.logout();
            SecurityContextHolder.getContext().setAuthentication(null);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return "redirect:/start";
    }
}
