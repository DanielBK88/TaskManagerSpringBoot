package daniil.tm2.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class StartController {

    @GetMapping
    public ModelAndView start(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated() 
                ? new ModelAndView("domains") 
                : new ModelAndView("start");
    }
    
}
