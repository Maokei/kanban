package se.maokei.kanban;


import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import se.maokei.kanban.domain.Project;
import se.maokei.kanban.repositories.ProjectRepository;
import se.maokei.kanban.services.ProjectService;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
//@SpringBootTest(classes =  {KanbanApplication.class})
@ContextConfiguration(classes = {KanbanApplication.class})
public class ProjectRepositoryIntegrationTest {
    @Autowired
    EntityManager entityManager;
    @Autowired
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
        Assert.assertNotNull(projectRepository.findAll());
    }

    @Test
    public void findProjectByName() {
        String projectName = "testProject";
        Project newProject = new Project();
        newProject.setName(projectName);
        newProject.setProjectIdentifier("MXMX5");
        newProject.setDescription("Test project");

        projectService.saveOrUpdateProject(newProject);
        entityManager.flush();

        Project foundProject = projectRepository.findByName(projectName);
        System.out.println(foundProject);
        //Assert.assertTrue(foundProject.getName())

        //assertThat(foundProject.getName()).isEqualTo(projectName);
        assertEquals(foundProject.getName(), projectName);
    }
}
