package se.maokei.kanban;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.maokei.kanban.domain.Project;
import se.maokei.kanban.repositories.ProjectRepository;
import se.maokei.kanban.services.ProjectService;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProjectRepositoryIntegrationTest {
    @Autowired
    EntityManager entityManager;
    ProjectService projectService;
    @Autowired
    ProjectRepository projectRepository;

    @BeforeAll
    public void setup() throws Exception {
        this.projectService = new ProjectService();
    }
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
}
