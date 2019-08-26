package se.maokei.kanban.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.maokei.kanban.domain.Project;
import se.maokei.kanban.exceptions.ProjectIdException;
import se.maokei.kanban.repositories.ProjectRepository;

import java.util.Optional;

/**
 * ProjectService
 * */
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch(Exception e) {
            throw new ProjectIdException("Project ID '" +
                    project.getProjectIdentifier().toUpperCase() +
                    "' already exists");
        }
        //Optional<Project> localProject = this.projectRepository.findById(project.getId());
    }


    public Project findProjectByIdentifier(String projectId) {
        Optional<Project> oProject = Optional.ofNullable(
                projectRepository.findByProjectIdentifier(projectId.toUpperCase())
        );
        oProject.orElseThrow(
                () -> new ProjectIdException("No project found with identifier: " + projectId.toUpperCase())
        );
        return oProject.get();
    }
}