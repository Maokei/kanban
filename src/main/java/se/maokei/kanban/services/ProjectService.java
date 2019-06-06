package se.maokei.kanban.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.maokei.kanban.domain.Project;
import se.maokei.kanban.repositories.ProjectRepository;

//import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {
        //Optional<Project> localProject = this.projectRepository.findById(project.getId());
        return projectRepository.save(project);
    }
}
