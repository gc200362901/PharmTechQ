package appdevs.pharmtechq.models;

public class LearningHubEntry {

    private String learningHubEntryId;
    private String learningHubHeading;
    private String learningHubContents;
    private String learningHubReferences;

    public LearningHubEntry() {}

    public LearningHubEntry(String learningHubEntryId, String learningHubHeading,
                            String learningHubContents, String learningHubReferences) {
        this.learningHubEntryId = learningHubEntryId;
        this.learningHubHeading = learningHubHeading;
        this.learningHubContents = learningHubContents;
        this.learningHubReferences = learningHubReferences;
    }

    public String getLearningHubEntryId() {
        return learningHubEntryId;
    }

    public String getLearningHubHeading() {
        return learningHubHeading;
    }

    public String getLearningHubContents() {
        return learningHubContents;
    }

    public String getLearningHubReferences() {
        return learningHubReferences;
    }
}
