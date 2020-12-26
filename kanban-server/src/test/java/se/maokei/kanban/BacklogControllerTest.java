package se.maokei.kanban;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import se.maokei.kanban.domain.Backlog;
import se.maokei.kanban.domain.Project;
import se.maokei.kanban.domain.ProjectTask;
import se.maokei.kanban.repositories.BacklogRepository;
import se.maokei.kanban.repositories.ProjectRepository;
import se.maokei.kanban.services.ProjectService;
import se.maokei.kanban.services.ProjectTaskService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class BacklogControllerTest {
    private static final String updProjectStr = "clown project";
    private static final String updProjectIdentifier = "XXXX5";
    private static final String updSequence = "CLOWN-1";

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectService projectService;
    @Autowired
    BacklogRepository backlogRepository;
    @Autowired
    ProjectTaskService projectTaskService;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
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

        //Persist clown test project
        projectService.saveOrUpdateProject(project);
        projectTaskService.addProjectTask(updProjectIdentifier, projectTask);
    }

    @Test
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
        mockMvc.perform(patch("/api/backlog/" + backlog.getProjectIdentifier() + "/" + sequence)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pt))
        ).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        //fetch and assert Project Task
        mockMvc.perform(get("/api/backlog/" + backlog.getProjectIdentifier() + "/" + sequence))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(newSummary)));
    }

    @Test
    public void deleteProjectTask() {
        //TODO
    }
}
