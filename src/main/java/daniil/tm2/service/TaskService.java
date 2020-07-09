package daniil.tm2.service;

import daniil.tm2.api.repository.IProjectRepository;
import daniil.tm2.api.repository.ITaskRepository;
import daniil.tm2.api.service.ITaskService;
import daniil.tm2.entity.Task;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Denis Volnenko
 */
@Service
@Transactional
public class TaskService implements ITaskService {

    @Autowired
    private ITaskRepository taskRepository;
    
    @Autowired
    private IProjectRepository projectRepository;

    @Override
    public Task getTaskById(final String id) {
        return taskRepository.findById(
                Optional.ofNullable(id)
                        .filter(n -> !n.isEmpty())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid id of task to find: " + id))
        ).orElse(null);
    }

    @Override
    public Task merge(final Task task) {
        return taskRepository.save(
                Optional.ofNullable(task)
                        .orElseThrow(() -> new IllegalArgumentException("The task to merge is null!"))
        );
    }

    @Override
    public void removeTaskById(final String id) {
        taskRepository.delete(Optional.ofNullable(id)
                .filter(i -> !i.isEmpty())
                .flatMap(i -> taskRepository.findById(i))
                .flatMap(t -> {
                    t.getProject().getTasks().remove(t);
                    return Optional.of(t);
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid task id or no such task!"))
        );
    }

    @Override
    public List<Task> getListTask() {
        return taskRepository.findAll();
    }

    @Override
    public void clear() {
        taskRepository.deleteAll();
    }

    @Override
    public Task createTaskByProject(final String projectId, final String taskName) {
        return taskRepository.save(
                Optional.ofNullable(taskName)
                        .filter(n -> !n.isEmpty())
                        .flatMap(n -> Optional.of(new Task(n, Optional.ofNullable(projectId)
                                .filter(pn -> !pn.isEmpty())
                                .flatMap(pn -> projectRepository.findById(pn))
                                .orElseThrow(() -> new IllegalArgumentException(
                                        "Invalid project id or project not found!")))))
                        .orElseThrow(() -> new IllegalArgumentException("Invalid name of task to create: " + taskName))
        );
    }

    @Override
    public void merge(Task... tasks) {
        Arrays.stream(tasks)
                .map(task -> Optional.ofNullable(task).orElseThrow(
                        () -> new IllegalArgumentException("Cannot merge null task!")
                ))
                .forEach(this::merge);
    }

}
