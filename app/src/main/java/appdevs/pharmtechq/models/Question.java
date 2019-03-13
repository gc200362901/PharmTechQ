package appdevs.pharmtechq.models;

import java.util.ArrayList;

public class Question {

    private int questionId;
    private String questionType;
    private String questionTopic;
    private String question;
    private String explanation;
    private ArrayList<String> references;
    private String correctAnswer;

    public Question() {}

    public Question(int questionId, String questionType, String questionTopic, String question,
                    String explanation, ArrayList<String> references, String correctAnswer) {
        this.questionId = questionId;
        this.questionType = questionType;
        this.questionTopic = questionTopic;
        this.question = question;
        this.explanation = explanation;
        this.references = references;
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

    public String getExplanation() {
        return explanation;
    }

    public ArrayList<String> getReferences() {
        return references;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
