package daniil.tm2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import daniil.tm2.entity.Domain;
import daniil.tm2.entity.Project;
import daniil.tm2.entity.Task;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
public class ProjectsCRUDTest {
    
    @Autowired
    public MockMvc mvc;

    private ObjectMapper mapper;

    /**
     * 1. Insert a new project.
     * 2. Retrieve and check the project.
     * 3. Update the project.
     * 4. Retrieve and check the project.
     * 5. Delete the project
     * 6. Verify, the project is deleted.
     **/
    @Test
    public void testCRUDProject() throws Exception {

        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // insert a new project
        Project project = createTestProject();
        callSaveProject(project);

        // check new inserted project

        Project fetchedProject = callFindProject();

        assertEquals(2, fetchedProject.getTasks().size());

        // add a new task to the project

        addNewTask(project);
        callSaveProject(project);

        // Again retrieve and check the project

        fetchedProject = callFindProject();

        assertEquals(3, fetchedProject.getTasks().size());

        // Finally, delete the project and verify, that it is deleted

        callDeleteProject();
        verifyDeleted();
    }

    private Project createTestProject() {
        Domain parentDomain = new Domain();
        parentDomain.setName("PARENT_DOMAIN");
        
        Project project = new Project();
        project.setName("SOME_TEST_PROJECT");
        project.setDomain(parentDomain);
        parentDomain.setProjects(Stream.of(project).collect(Collectors.toList()));
        
        Task task1 = new Task();
        task1.setName("TASK_1");
        task1.setProject(project);
        Task task2 = new Task();
        task2.setName("TASK_2");
        task2.setProject(project);

        project.setTasks(Arrays.asList(task1, task2));
        return project;
    }

    private void addNewTask(Project project) {
        Task newTask = new Task();
        newTask.setProject(project);
        newTask.setName("NEW_TASK");
        project.setTasks(new ArrayList<>(project.getTasks()));
        project.getTasks().add(newTask);
    }

    private void callSaveProject(Project project) throws Exception {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(project);

        mvc.perform(post("/saveProject?domainName=PARENT_DOMAIN")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    private void callDeleteProject() throws Exception {
        mvc.perform(delete("/deleteProject/SOME_TEST_PROJECT"))
                .andExpect(status().isOk());
    }

    private Project callFindProject() throws Exception {
        MvcResult result = mvc.perform(get("/findProject/SOME_TEST_PROJECT"))
                .andExpect(status().isOk())
                .andReturn();
        return  mapper.readValue(result.getResponse().getContentAsString(), Project.class);
    }

    private void verifyDeleted() throws Exception {
        MvcResult result = mvc.perform(get("/findProject/SOME_TEST_PROJECT"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().isEmpty());
    }
}
