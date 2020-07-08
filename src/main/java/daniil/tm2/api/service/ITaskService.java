package daniil.tm2.api.service;

import daniil.tm2.entity.Task;
import java.util.List;

/**
 * @author Denis Volnenko
 */
public interface ITaskService {

    Task getTaskById(String id);

    Task merge(Task task);

    void removeTaskById(String id);

    List<Task> getListTask();

    void clear();

    Task createTaskByProject(String projectId, String taskName);

    void merge(Task... tasks);
}
