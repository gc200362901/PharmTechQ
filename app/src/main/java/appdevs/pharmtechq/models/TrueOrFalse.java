package appdevs.pharmtechq.models;

import java.util.ArrayList;

public class TrueOrFalse extends Question {

    private String answerTrue;
    private String answerFalse;

    public TrueOrFalse() {}

    public TrueOrFalse(int questionId, String questionType, String questionTopic, String question,
                       String explanation, ArrayList<String> references, String correctAnswer) {
        super(questionId, questionType, questionTopic, question, explanation, references, correctAnswer);
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
