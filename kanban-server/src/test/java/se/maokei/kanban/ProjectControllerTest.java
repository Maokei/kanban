package se.maokei.kanban;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
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
        projectRepository.save(project1);
    }

    @Test
    public void checkForTestProject() throws Exception {
        mockMvc.perform(get("/api/project/all")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("Test project")));
    }
}
