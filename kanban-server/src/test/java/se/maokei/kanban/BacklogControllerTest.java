package se.maokei.kanban;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
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
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
public class BacklogControllerTest {
    private static final String BACKLOG_BASE_URL = "/api/backlog/";
    private static final String updProjectStr = "clown project";
    private static final String updProjectIdentifier = "XXXX5";
    private static final String updSequence = "CLOWN-1";

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
    UserRepository userRepositor;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        Project project = new Project();
        project.setName(updProjectStr);
        project.setDescription("This is test project");
        project.setProjectIdentifier(updProjectIdentifier);
        //Backlog and test projectTask
        Backlog updBacklog = new Backlog();
        ProjectTask projectTask = new ProjectTask();
        projectTask.setProjectSequence(updSequence);
        projectTask.setSummary("Clown task one");
        projectTask.setPriority(3);
        projectTask.setProjectIdentifier(updProjectIdentifier);
        //Add backlog and task
        List<ProjectTask> taskList = new ArrayList<>();
        taskList.add(projectTask);
        updBacklog.setProjectTasks(taskList);

        User user = new User();
        user.setUsername("tester");
        user.setPassword("password1");
        user.setEmail("user@gmail.com");
        user.setName("John Doe");
        userRepositor.save(user);

        //Persist clown test project
        projectService.saveOrUpdateProject(project, "tester");
        projectTaskService.addProjectTask(updProjectIdentifier, projectTask);
    }

    @After
    public void afterAllTests() {
        userRepositor.deleteAll();
        projectRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void updateProjectTask() throws Exception {
        String newSummary = "This clown task has been updated!";
        Project pro = projectRepository.findByProjectIdentifier(updProjectIdentifier);
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
    public void deleteProjectTask() {
        //TODO
    }
}
