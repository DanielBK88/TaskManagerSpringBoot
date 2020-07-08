package daniil.tm2.controller.tasks;

import daniil.tm2.api.service.ITaskService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/processTaskCreate", method = RequestMethod.POST)
public class TasksCreateController {
    
    @Autowired
    private ITaskService taskService;

    @GetMapping
    @Secured("ROLE_NORMAL_USER")
    public String create(HttpServletRequest request) {
        String projectName = (String) request.getSession().getAttribute("selectedProject");
        String taskName = request.getParameter("taskName");
        taskService.createTaskByProject(projectName, taskName);
        return "redirect:/tasks?project="+projectName;
    }
    
}
