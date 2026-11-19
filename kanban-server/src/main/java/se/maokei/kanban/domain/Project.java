package se.maokei.kanban.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Project name is required")
    private String name;
    @NotBlank(message = "Project identifier is required")
    @Size(min = 3, max = 5, message="project identifier is 3 - 5 characters")
    @Column(unique = true, updatable = false)
    private String projectIdentifier;
    @NotBlank(message = "Description is required")
    private String description;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date endDate;
    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(updatable = false)
    private Date created;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updated;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project")
    @JsonIgnore
    private Backlog backlog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    private String projectLeader;

    private String repositoryPath;
    private String repositoryUrl;
    private String defaultBranch = "main";
    @Column(length = 1024)
    private String testCommand;
    @Column(length = 1024)
    private String specAgentCommand;
    @Column(length = 1024)
    private String coderAgentCommand;
    @Column(length = 1024)
    private String reviewerAgentCommand;
    private Integer maxAgentRetries = 3;

    public Project() {
    }

    @PrePersist
    protected void onCreate() {
        this.created = new Date();
        if (this.defaultBranch == null || this.defaultBranch.isBlank()) {
            this.defaultBranch = "main";
        }
        if (this.maxAgentRetries == null) {
            this.maxAgentRetries = 3;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Backlog getBacklog() {
        return backlog;
    }

    public void setBacklog(Backlog backlog) {
        this.backlog = backlog;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }

    public String getRepositoryPath() {
        return repositoryPath;
    }

    public void setRepositoryPath(String repositoryPath) {
        this.repositoryPath = repositoryPath;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }

    public void setDefaultBranch(String defaultBranch) {
        this.defaultBranch = defaultBranch;
    }

    public String getTestCommand() {
        return testCommand;
    }

    public void setTestCommand(String testCommand) {
        this.testCommand = testCommand;
    }

    public String getSpecAgentCommand() {
        return specAgentCommand;
    }

    public void setSpecAgentCommand(String specAgentCommand) {
        this.specAgentCommand = specAgentCommand;
    }

    public String getCoderAgentCommand() {
        return coderAgentCommand;
    }

    public void setCoderAgentCommand(String coderAgentCommand) {
        this.coderAgentCommand = coderAgentCommand;
    }

    public String getReviewerAgentCommand() {
        return reviewerAgentCommand;
    }

    public void setReviewerAgentCommand(String reviewerAgentCommand) {
        this.reviewerAgentCommand = reviewerAgentCommand;
    }

    public Integer getMaxAgentRetries() {
        return maxAgentRetries;
    }

    public void setMaxAgentRetries(Integer maxAgentRetries) {
        this.maxAgentRetries = maxAgentRetries;
    }
}
