package se.maokei.kanban;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.maokei.kanban.domain.Project;
import se.maokei.kanban.repositories.ProjectRepository;
import se.maokei.kanban.services.ProjectService;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProjectRepositoryIntegrationTest extends AbstractContainerBaseTest {
    @Autowired
    EntityManager entityManager;
    ProjectService projectService;
    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void saveProject() {
        Project project = new Project();
        project.setDescription("Project to be saved");
        project.setProjectIdentifier("TTTT");
        project.setName("A project");
        this.projectRepository.save(project);
        assertNotNull(projectRepository.findAll());
        projectRepository.deleteAll();
    }

    @Test
    public void findProjectByName() {
        String projectName = "testProject";
        Project newProject = new Project();
        newProject.setName(projectName);
        newProject.setProjectIdentifier("MXMG9");
        newProject.setDescription("Test project");
        projectRepository.save(newProject);

        assertEquals(projectRepository.findByName(projectName).getName(), projectName);
    }

    @BeforeEach
    public void setUp() throws Exception {
        this.projectService = new ProjectService();
    }
}
