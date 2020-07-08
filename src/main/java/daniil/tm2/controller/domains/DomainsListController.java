package daniil.tm2.controller.domains;

import daniil.tm2.api.service.IDomainService;
import daniil.tm2.entity.Domain;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/domains", method = RequestMethod.POST)
public class DomainsListController {
    
    @Autowired
    private IDomainService domainService;

    @GetMapping
    @Secured("ROLE_NORMAL_USER")
    public ModelAndView domains(Model model) {
        List<Domain> domains = domainService.findAll();
        model.addAttribute("domains", domains);
        return new ModelAndView("domains");
    }
    
}
