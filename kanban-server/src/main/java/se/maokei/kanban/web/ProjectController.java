package se.maokei.kanban.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import se.maokei.kanban.domain.Project;
import se.maokei.kanban.services.MapValidationErrorService;
import se.maokei.kanban.services.ProjectService;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(
            @Valid @RequestBody Project project, BindingResult result, Principal principal
    ) {
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationErrorService(result);
        if(errorMap != null) return errorMap;

        Project newProject = this.projectService.saveOrUpdateProject(project, principal.getName());
        return new ResponseEntity<Project>(newProject, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateProject(@Valid @RequestBody Project project, BindingResult result) {

        return null;
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal) {
        Project project = null;
        project = projectService.findProjectByIdentifier(projectId, principal.getName());
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(Principal principal) {
        System.out.println(principal);
        return this.projectService.findAllProjects(principal.getName());
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteById(@PathVariable String projectId, Principal principal) {
        projectService.deleteProjectByIdentifier(projectId, principal.getName());
        return new ResponseEntity<String>("Project with Id '" + projectId + "' was deleted", HttpStatus.OK);
    }
}
