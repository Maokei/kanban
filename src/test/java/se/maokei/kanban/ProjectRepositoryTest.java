package se.maokei.kanban;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import se.maokei.kanban.domain.Project;
import se.maokei.kanban.repositories.ProjectRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectRepositoryTest {
    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void saveProject() {
        String projectName = "testProject";
        Project newProject = new Project();
        newProject.setName(projectName);
        newProject.setProjectIdentifier("MXMX5");
        newProject.setDescription("Test project");

        projectRepository.save(newProject);
        Iterable<Project> projects = projectRepository.findAll();
        List<Project> projectList = new ArrayList<>();
        projects.forEach(projectList::add);
        assertThat(projectList, hasSize(1));
        this.projectRepository.deleteAll();
    }
}
