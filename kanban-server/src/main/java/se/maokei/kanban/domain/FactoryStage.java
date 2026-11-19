package se.maokei.kanban.domain;

public enum FactoryStage {
    IDEA,
    SPECIFYING,
    NEEDS_CLARIFICATION,
    SPEC_APPROVAL,
    READY_FOR_DEV,
    IMPLEMENTING,
    VERIFYING,
    AI_REVIEW,
    HUMAN_REVIEW,
    DONE,
    FAILED;

    public String toBoardStatus() {
        switch (this) {
            case IDEA:
            case SPECIFYING:
            case NEEDS_CLARIFICATION:
            case SPEC_APPROVAL:
            case READY_FOR_DEV:
                return "TO_DO";
            case IMPLEMENTING:
            case VERIFYING:
            case AI_REVIEW:
            case HUMAN_REVIEW:
            case FAILED:
                return "IN_PROGRESS";
            case DONE:
                return "DONE";
            default:
                return "TO_DO";
        }
    }

    public boolean isAutomatedStage() {
        return this == SPECIFYING
                || this == READY_FOR_DEV
                || this == IMPLEMENTING
                || this == VERIFYING
                || this == AI_REVIEW;
    }

    public boolean isHumanGate() {
        return this == NEEDS_CLARIFICATION
                || this == SPEC_APPROVAL
                || this == HUMAN_REVIEW;
    }
}
