package se.maokei.kanban;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.maokei.kanban.domain.Project;
import se.maokei.kanban.repositories.ProjectRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.util.AssertionErrors.assertTrue;


@DataJpaTest
public class ProjectRepositoryTest {
    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void saveProject() {
        String projectName = "umlProject";
        Project newProject = new Project();
        newProject.setName(projectName);
        newProject.setProjectIdentifier("MXMX4");
        newProject.setDescription("Fun project");

        projectRepository.save(newProject);
        Project project = projectRepository.findByName("umlProject");
        assertTrue("Unable to find project with name " + projectName, project.getName().equals(projectName));
        this.projectRepository.deleteAll();
    }

    @Test
    public void saveProjects() {
        String projectName = "testProject";
        Project newProject = new Project();
        newProject.setName(projectName);
        newProject.setProjectIdentifier("MXMX5");
        newProject.setDescription("Test project");
        Project newProject2 = new Project();
        newProject2.setName(projectName + "Two");
        newProject2.setProjectIdentifier("MXMX6");
        newProject2.setDescription("Test project2");

        projectRepository.save(newProject);
        projectRepository.save(newProject2);
        Iterable<Project> projects = projectRepository.findAll();
        List<Project> projectList = new ArrayList<>();
        projects.forEach(projectList::add);
        assertThat(projectList, hasSize(2));
        this.projectRepository.deleteAll();
    }
}
