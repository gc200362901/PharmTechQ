package appdevs.pharmtechq.models;

public class Question {

    private int questionId;
    private String questionType;
    private String questionTopic;
    private String question;
    private String correctAnswer;

    public Question() {}

    public Question(int questionId, String questionType, String questionTopic, String question, String correctAnswer) {
        this.questionId = questionId;
        this.questionType = questionType;
        this.questionTopic = questionTopic;
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public String getQuestionTopic() {
        return questionTopic;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
