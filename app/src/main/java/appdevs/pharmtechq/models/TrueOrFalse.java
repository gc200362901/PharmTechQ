package appdevs.pharmtechq.models;

public class TrueOrFalse extends Question {

    private String answerTrue;
    private String answerFalse;

    public TrueOrFalse() {}

    public TrueOrFalse(int questionId, String questionType, String questionTopic, String question, String correctAnswer) {
        super(questionId, questionType, questionTopic, question, correctAnswer);
        this.answerTrue = "True";
        this.answerFalse = "False";
    }

    public String getAnswerTrue() {
        return answerTrue;
    }

    public String getAnswerFalse() {
        return answerFalse;
    }
}
