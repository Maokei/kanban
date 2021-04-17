package se.maokei.kanban.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.maokei.kanban.domain.Backlog;
import se.maokei.kanban.domain.Project;
import se.maokei.kanban.domain.User;
import se.maokei.kanban.exceptions.ProjectIdException;
import se.maokei.kanban.exceptions.ProjectNotFoundException;
import se.maokei.kanban.exceptions.UserNotFoundException;
import se.maokei.kanban.repositories.BacklogRepository;
import se.maokei.kanban.repositories.ProjectRepository;
import se.maokei.kanban.repositories.UserRepository;

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
    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username) {
        try {
            Optional<User> userOpt = userRepository.findByUsername(username);
            if(!userOpt.isPresent()) {
                throw new UserNotFoundException("User with username: " + username + " not found.");
            }
            User user = userOpt.get();
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
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


    public Project findProjectByIdentifier(String projectId, String username) {
        Optional<Project> oProject = Optional.ofNullable(
                projectRepository.findByProjectIdentifier(projectId.toUpperCase())
        );
        oProject.orElseThrow(
                () -> new ProjectIdException("No project found with identifier: " + projectId.toUpperCase())
        );

        if(!oProject.get().getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project not found in your account");
        }

        return oProject.get();
    }

    public Iterable<Project> findAllProjects(String username) {
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectId, String username) {
        try {
            projectRepository.delete(findProjectByIdentifier(projectId, username));
        }catch(ProjectIdException e) {
            throw new ProjectIdException("Unable to delete project with id '" + projectId + "' ,project does not exist");
        }

    }
}