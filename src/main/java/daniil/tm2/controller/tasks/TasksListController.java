package daniil.tm2.controller.tasks;

import daniil.tm2.api.service.ITaskService;
import daniil.tm2.entity.Task;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping(value = "/tasks", method = RequestMethod.POST)
public class TasksListController {
    
    @Autowired
    private ITaskService taskService;

    @GetMapping
    @Secured("ROLE_NORMAL_USER")
    public ModelAndView domains(HttpServletRequest request) {
        String project = request.getParameter("project");
        List<Task> tasks = taskService.getListTask().stream()
                .filter(task -> task.getProject().getName().equals(project))
                .collect(Collectors.toList());
        request.getSession().setAttribute("selectedProject", project);
        request.getSession().setAttribute("tasks", tasks);
        return new ModelAndView("tasks");
    }
    
}
