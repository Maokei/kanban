package se.maokei.kanban;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import se.maokei.kanban.domain.Backlog;
import se.maokei.kanban.domain.Project;
import se.maokei.kanban.domain.ProjectTask;
import se.maokei.kanban.domain.User;
import se.maokei.kanban.repositories.BacklogRepository;
import se.maokei.kanban.repositories.ProjectRepository;
import se.maokei.kanban.repositories.UserRepository;
import se.maokei.kanban.services.ProjectService;
import se.maokei.kanban.services.ProjectTaskService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BacklogControllerTest {
    private static final String BACKLOG_BASE_URL = "/api/backlog/";
    private static final String UPD_PROJECT_STR = "clown project";
    private static final String UPD_PROJECT_IDENTIFIER = "XXXX5";
    private static final String UPD_SEQUENCE = "CLOWN-1";

    @Autowired
    private WebApplicationContext context;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectService projectService;
    @Autowired
    BacklogRepository backlogRepository;
    @Autowired
    ProjectTaskService projectTaskService;
    @Autowired
    UserRepository userRepository;

    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        Project project = new Project();
        project.setName(UPD_PROJECT_STR);
        project.setDescription("This is test project");
        project.setProjectIdentifier(UPD_PROJECT_IDENTIFIER);

        //Backlog and test projectTask
        Backlog updBacklog = new Backlog();
        ProjectTask projectTask = new ProjectTask();
        projectTask.setProjectSequence(UPD_SEQUENCE);
        projectTask.setSummary("Clown task one");
        projectTask.setPriority(3);
        projectTask.setProjectIdentifier(UPD_PROJECT_IDENTIFIER);

        //Add backlog and task
        List<ProjectTask> taskList = new ArrayList<>();
        taskList.add(projectTask);
        updBacklog.setProjectTasks(taskList);

        User user = new User();
        user.setUsername("tester");
        user.setPassword("password1");
        user.setEmail("user@gmail.com");
        user.setName("John Doe");
        userRepository.save(user);

        //Persist clown test project
        projectService.saveOrUpdateProject(project, user.getUsername());
        projectTaskService.addProjectTask(UPD_PROJECT_IDENTIFIER, projectTask, user.getUsername());
    }

    @AfterAll
    public void tearDown() {
        userRepository.deleteAll();
        projectRepository.deleteAll();
        backlogRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "tester", password = "password1", roles = "USER")
    public void updateProjectTask() throws Exception {
        String newSummary = "This clown task has been updated!";
        Project pro = projectRepository.findByProjectIdentifier(UPD_PROJECT_IDENTIFIER);
        Backlog backlog = pro.getBacklog();

        List<ProjectTask> tasks = backlog.getProjectTasks();
        assertEquals("Should only be one task in the clown project", 1, tasks.size());
        ProjectTask pt = tasks.get(0);
        String sequence = pt.getProjectSequence();

        pt.setSummary(newSummary);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(patch(BACKLOG_BASE_URL + backlog.getProjectIdentifier() + "/" + sequence)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pt))
                .with(csrf())
        ).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        //fetch and assert Project Task
        mockMvc.perform(get(BACKLOG_BASE_URL + backlog.getProjectIdentifier() + "/" + sequence))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(newSummary)));
    }

    @Test
    @WithMockUser(username = "tester", password = "password1", roles = "USER")
    public void deleteProjectTask() throws Exception {
        Project pro = projectRepository.findByProjectIdentifier(UPD_PROJECT_IDENTIFIER);
        Backlog backlog = pro.getBacklog();
        List<ProjectTask> tasks = backlog.getProjectTasks();
        ProjectTask pt = tasks.get(0);
        String sequence = pt.getProjectSequence();
        mockMvc.perform(delete(BACKLOG_BASE_URL + backlog.getProjectIdentifier() + "/" + sequence))
                .andExpect(status().isOk());
    }
}
