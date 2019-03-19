package appdevs.pharmtechq.models;

public class QuizScore {

    private String quizScoreId;
    private String quizScoreTopic;
    private int quizScoreCorrectAttempts;
    private int quizScoreTotalAttempts;

    public QuizScore() {}

    public QuizScore(String quizScoreId, String quizScoreTopic, int quizScoreCorrectAttempts, int quizScoreTotalAttempts) {
        this.quizScoreId = quizScoreId;
        this.quizScoreTopic = quizScoreTopic;
        this.quizScoreCorrectAttempts = quizScoreCorrectAttempts;
        this.quizScoreTotalAttempts = quizScoreTotalAttempts;
    }

    public String getQuizScoreId() {
        return quizScoreId;
    }

    public String getQuizScoreTopic() {
        return quizScoreTopic;
    }

    public int getQuizScoreCorrectAttempts() {
        return quizScoreCorrectAttempts;
    }

    public int getQuizScoreTotalAttempts() {
        return quizScoreTotalAttempts;
    }
}
