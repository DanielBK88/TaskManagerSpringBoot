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
public class DomainsCRUDTest {

    @Autowired
    public MockMvc mvc;
    
    private ObjectMapper mapper;
    
    /**
     * 1. Insert a new domain.
     * 2. Retrieve and check the domain.
     * 3. Update the domain.
     * 4. Retrieve and check the domain.
     * 5. Delete the domain
     * 6. Verify, the domain is deleted.
     **/
    @Test
    public void testCRUDDomain() throws Exception {

        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        // insert a new domain
        Domain domain = createTestDomain();
        callSaveDomain(domain);
        
        // check new inserted domain
        
        Domain fetchedDomain = callFindDomain();
        
        assertEquals(2, fetchedDomain.getProjects().size());
        assertEquals(3, fetchedDomain.getTasks().size());
        
        // add a new project with two tasks to the domain
        
        addNewProject(domain);
        callSaveDomain(domain);
        
        // Again retrieve and check the domain

        fetchedDomain = callFindDomain();

        assertEquals(3, fetchedDomain.getProjects().size());
        assertEquals(5, fetchedDomain.getTasks().size());
        
        // Finally, delete the domain and verify, that it is deleted
        
        callDeleteDomain();
        verifyDeleted();
    }
    
    private Domain createTestDomain() {
        Domain domain = new Domain();
        domain.setName("SOME_TEST_DOMAIN");
        Project project1 = new Project();
        project1.setName("FIRST_PROJECT");
        project1.setDomain(domain);
        Project project2 = new Project();
        project2.setName("SECOND_PROJECT");
        project2.setDomain(domain);
        Task proj1Task1 = new Task();
        proj1Task1.setName("FIRST_PROJ_TASK_1");
        proj1Task1.setProject(project1);
        Task proj1Task2 = new Task();
        proj1Task2.setProject(project1);
        proj1Task2.setName("FIRST_PROJ_TASK_2");
        Task proj2Task = new Task();
        proj2Task.setName("SECOND_PROJ_TASK");
        proj2Task.setProject(project2);
        project1.setTasks(Arrays.asList(proj1Task1, proj1Task2));
        project2.setTasks(Arrays.asList(proj2Task));
        domain.setProjects(Arrays.asList(project1, project2));
        return domain;
    }
    
    private void addNewProject(Domain domain) {
        Project newProject = new Project();
        newProject.setName("NEW_PROJECT");
        newProject.setDomain(domain);
        Task newTask1 = new Task();
        newTask1.setProject(newProject);
        newTask1.setName("NEW_TASK_1");
        Task newTask2 = new Task();
        newTask2.setProject(newProject);
        newTask2.setName("NEW_TASK_2");
        domain.setProjects(new ArrayList<>(domain.getProjects()));
        domain.getProjects().add(newProject);
        newProject.setTasks(new ArrayList<>(domain.getProjects().get(0).getTasks()));
        newProject.getTasks().add(newTask1);
        newProject.getTasks().add(newTask2);
    }
    
    private void callSaveDomain(Domain domain) throws Exception {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(domain);

        mvc.perform(post("/saveDomain")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson))
                .andExpect(status().isOk());
    }
    
    private void callDeleteDomain() throws Exception {
        mvc.perform(delete("/deleteDomain/SOME_TEST_DOMAIN"))
                .andExpect(status().isOk());
    }
    
    private Domain callFindDomain() throws Exception {
        MvcResult result = mvc.perform(get("/findDomain/SOME_TEST_DOMAIN"))
                .andExpect(status().isOk())
                .andReturn();
        return  mapper.readValue(result.getResponse().getContentAsString(), Domain.class);
    }
    
    private void verifyDeleted() throws Exception {
        MvcResult result = mvc.perform(get("/findDomain/SOME_TEST_DOMAIN"))
                .andExpect(status().isOk())
                .andReturn();
        
        assertTrue(result.getResponse().getContentAsString().isEmpty());
    }
    
}
