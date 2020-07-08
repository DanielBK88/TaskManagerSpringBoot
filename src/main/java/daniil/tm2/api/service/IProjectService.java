package daniil.tm2.api.service;

import daniil.tm2.entity.Project;
import java.util.List;

/**
 * @author Denis Volnenko
 */
public interface IProjectService {

    Project createProject(String name, String domainName);

    Project merge(Project project);

    Project getProjectById(String id);

    void removeProjectById(String id);

    List<Project> getListProject();

    void clear();

    void merge(Project... projects);
}
