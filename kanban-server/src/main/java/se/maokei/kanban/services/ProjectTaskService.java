package se.maokei.kanban.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.maokei.kanban.domain.Backlog;
import se.maokei.kanban.domain.Project;
import se.maokei.kanban.domain.ProjectTask;
import se.maokei.kanban.exceptions.ProjectNotFoundException;
import se.maokei.kanban.repositories.BacklogRepository;
import se.maokei.kanban.repositories.ProjectTaskRepository;

import java.util.Optional;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;
    @Autowired
    private ProjectTaskRepository projectTaskRepository;
    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
        Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
        projectTask.setBacklog(backlog);
        Integer backlogSequence = backlog.getPTSequence();
        backlogSequence++;
        backlog.setPTSequence(backlogSequence);
        //Add Sequence to Project Task
        projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);
        //INITIAL status when status is null
        String status = Optional.ofNullable(projectTask.getStatus()).orElse("");
        if (status.equals("")) {
            projectTask.setStatus("TO_DO");
        }
        //priority
        if (projectTask.getPriority() == null || projectTask.getPriority() == 0) { //form 0
            projectTask.setPriority(3);
        }

        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogById(String id, String username) {
        projectService.findProjectByIdentifier(id, username);
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findProjectTaskByProjectSequence(String projectIdentifier, String projectSequence, String username) {
        projectService.findProjectByIdentifier(projectIdentifier, username);

        ProjectTask projectTask = Optional.ofNullable(projectTaskRepository.findByProjectSequence(projectSequence))
                .orElseThrow(() -> new ProjectNotFoundException("Project Task '" + projectSequence + "' not found"));

        if(!projectTask.getProjectIdentifier().equals(projectIdentifier)) {
            throw new ProjectNotFoundException("Project Task sequence: '"+ projectSequence +"' does not exist in project: '"+ projectIdentifier);
        }
        return projectTask;
    }

    public ProjectTask updateProjectTask(ProjectTask updated, String projectIdentifier, String projectSequence, String username) {
        ProjectTask pt = findProjectTaskByProjectSequence(projectIdentifier, projectSequence, username);
        pt = updated;
        return projectTaskRepository.save(pt);
    }

    public void deleteProjectTaskByProjectSequence(String projectIdentifier, String sequence, String username) {
        ProjectTask projectTask = findProjectTaskByProjectSequence(projectIdentifier, sequence, username);
        projectTaskRepository.delete(projectTask);
    }
}
