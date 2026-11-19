package se.maokei.kanban.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class ProjectTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false, unique = true)
    private String projectSequence;
    @NotBlank(message = "Please include a task summary")
    private String summary;
    @Lob
    private String acceptanceCriteria;
    private String status;
    private Integer priority;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dueDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "backlog_id", updatable = false, nullable = false)
    @JsonIgnore
    private Backlog backlog;

    @Column(updatable = false)
    private String projectIdentifier;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date creadAt;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updateAt;

    @Enumerated(EnumType.STRING)
    private FactoryStage factoryStage = FactoryStage.IDEA;

    @Lob
    private String technicalSpec;

    @Lob
    private String clarificationQuestions;

    @Lob
    private String clarificationAnswers;

    @Lob
    private String verificationReport;

    @Lob
    private String reviewFeedback;

    @Lob
    private String lastError;

    private String branchName;

    private String worktreePath;

    private String headCommitSha;

    private String pullRequestUrl;

    private Integer retryCount = 0;

    private Integer maxRetries = 3;

    @PrePersist
    protected void onCreate() {
        this.creadAt = new Date();
        if (this.factoryStage == null) {
            this.factoryStage = FactoryStage.IDEA;
        }
        if (this.retryCount == null) {
            this.retryCount = 0;
        }
        if (this.maxRetries == null) {
            this.maxRetries = 3;
        }
        if (this.status == null || this.status.isBlank()) {
            this.status = this.factoryStage.toBoardStatus();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = new Date();
    }

    public ProjectTask() {
    }

    public void transitionToStage(FactoryStage stage) {
        this.factoryStage = stage;
        if (stage != null) {
            this.status = stage.toBoardStatus();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectSequence() {
        return projectSequence;
    }

    public void setProjectSequence(String projectSequence) {
        this.projectSequence = projectSequence;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAcceptanceCriteria() {
        return acceptanceCriteria;
    }

    public void setAcceptanceCriteria(String acceptanceCriteria) {
        this.acceptanceCriteria = acceptanceCriteria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Backlog getBacklog() {
        return backlog;
    }

    public void setBacklog(Backlog backlog) {
        this.backlog = backlog;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public Date getCreadAt() {
        return creadAt;
    }

    public void setCreadAt(Date creadAt) {
        this.creadAt = creadAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public FactoryStage getFactoryStage() {
        return factoryStage;
    }

    public void setFactoryStage(FactoryStage factoryStage) {
        this.factoryStage = factoryStage;
    }

    public String getTechnicalSpec() {
        return technicalSpec;
    }

    public void setTechnicalSpec(String technicalSpec) {
        this.technicalSpec = technicalSpec;
    }

    public String getClarificationQuestions() {
        return clarificationQuestions;
    }

    public void setClarificationQuestions(String clarificationQuestions) {
        this.clarificationQuestions = clarificationQuestions;
    }

    public String getClarificationAnswers() {
        return clarificationAnswers;
    }

    public void setClarificationAnswers(String clarificationAnswers) {
        this.clarificationAnswers = clarificationAnswers;
    }

    public String getVerificationReport() {
        return verificationReport;
    }

    public void setVerificationReport(String verificationReport) {
        this.verificationReport = verificationReport;
    }

    public String getReviewFeedback() {
        return reviewFeedback;
    }

    public void setReviewFeedback(String reviewFeedback) {
        this.reviewFeedback = reviewFeedback;
    }

    public String getLastError() {
        return lastError;
    }

    public void setLastError(String lastError) {
        this.lastError = lastError;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getWorktreePath() {
        return worktreePath;
    }

    public void setWorktreePath(String worktreePath) {
        this.worktreePath = worktreePath;
    }

    public String getHeadCommitSha() {
        return headCommitSha;
    }

    public void setHeadCommitSha(String headCommitSha) {
        this.headCommitSha = headCommitSha;
    }

    public String getPullRequestUrl() {
        return pullRequestUrl;
    }

    public void setPullRequestUrl(String pullRequestUrl) {
        this.pullRequestUrl = pullRequestUrl;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Integer getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(Integer maxRetries) {
        this.maxRetries = maxRetries;
    }

    @Override
    public String toString() {
        return "ProjectTask{" +
                "id=" + id +
                ", projectSequence='" + projectSequence + '\'' +
                ", summary='" + summary + '\'' +
                ", acceptanceCriteria='" + acceptanceCriteria + '\'' +
                ", status='" + status + '\'' +
                ", factoryStage=" + factoryStage +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                ", projectIdentifier='" + projectIdentifier + '\'' +
                ", creadAt=" + creadAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
