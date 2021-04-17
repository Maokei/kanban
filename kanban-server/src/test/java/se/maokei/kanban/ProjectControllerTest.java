package se.maokei.kanban;

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
import se.maokei.kanban.domain.Project;
import se.maokei.kanban.repositories.ProjectRepository;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ProjectControllerTest {
    private final String URL_GET_ALL_PROJECTS = "/api/project/all";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ProjectRepository projectRepository;

    @Before
    public void setup() {
        Project project1 = new Project();
        project1.setName("Test project");
        project1.setDescription("This is test project1");
        project1.setProjectIdentifier("MXMX4");
        project1.setProjectLeader("user1");

        projectRepository.save(project1);
    }

    @Test
    @WithMockUser(username = "user1")
    public void checkForTestProject() throws Exception {
        mockMvc.perform(get(URL_GET_ALL_PROJECTS)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("Test project")));
    }
}
