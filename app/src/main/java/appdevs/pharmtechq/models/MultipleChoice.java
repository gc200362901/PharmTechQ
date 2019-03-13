package appdevs.pharmtechq.models;

public class MultipleChoice extends Question {

    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;

    public MultipleChoice() {}

    public MultipleChoice(int questionId, String questionType, String questionTopic, String question, String correctAnswer,
                          String answerA, String answerB, String answerC, String answerD) {
        super(questionId, questionType, questionTopic, question, correctAnswer);
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

}
