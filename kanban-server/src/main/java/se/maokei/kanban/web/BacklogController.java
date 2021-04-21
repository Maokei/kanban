package se.maokei.kanban.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import se.maokei.kanban.domain.ProjectTask;
import se.maokei.kanban.services.MapValidationErrorService;
import se.maokei.kanban.services.ProjectTaskService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
                                            BindingResult result, @PathVariable String backlog_id, Principal principal){

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationErrorService(result);
        if (errorMap != null) return errorMap;

        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName());

        return new ResponseEntity<>(projectTask1, HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, Principal principal) {
        return projectTaskService.findBacklogById(backlog_id, principal.getName());
    }

    @GetMapping("/{backlogId}/{projectTaskId}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlogId, @PathVariable String projectTaskId) {
        ProjectTask projectTask = projectTaskService.findProjectTaskByProjectSequence(backlogId, projectTaskId);
        return new ResponseEntity<>(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{backlogId}/{projectTaskId}")
    public ResponseEntity<?> updateProjectTask(
            @Valid @RequestBody ProjectTask updatedProjectTask,
            BindingResult bindingResult,
            @PathVariable String backlogId,
            @PathVariable String projectTaskId
    ) {
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationErrorService(bindingResult);
        if (errorMap != null) return errorMap;

        ProjectTask updatedTask = projectTaskService.updateProjectTask(
                updatedProjectTask,
                backlogId,
                projectTaskId
        );
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_pi}/{projectTask_seq}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_pi, @PathVariable String projectTask_seq) {
        projectTaskService.deleteProjectTaskByProjectSequence(backlog_pi, projectTask_seq);

        return new ResponseEntity<String>("Project Task '" + projectTask_seq + "' was deleted successfully", HttpStatus.OK);
    }
}