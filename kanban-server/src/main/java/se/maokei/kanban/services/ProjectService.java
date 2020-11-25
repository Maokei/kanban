package se.maokei.kanban.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.maokei.kanban.domain.Backlog;
import se.maokei.kanban.domain.Project;
import se.maokei.kanban.exceptions.ProjectIdException;
import se.maokei.kanban.repositories.BacklogRepository;
import se.maokei.kanban.repositories.ProjectRepository;

import java.util.Optional;

/**
 * ProjectService
 * */
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier());
            }

            if(project.getId() != null) {
                project.setBacklog(
                        backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase())
                );
            }

            return projectRepository.save(project);
        } catch(Exception e) {
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

    public Iterable<Project> findAllProjects() {
        return this.projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId) {
        try {
            Project project = findProjectByIdentifier(projectId);
            projectRepository.delete(project);
        }catch(ProjectIdException e) {
            throw new ProjectIdException("Unable to delete project with id '" + projectId + "' ,project does not exist");
        }

    }
}