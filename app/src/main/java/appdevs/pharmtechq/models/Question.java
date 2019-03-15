package appdevs.pharmtechq.models;

import java.util.ArrayList;

public class Question {

    private String questionId;
    private String questionTopic;
    private String question;
    private ArrayList<String> answers;
    private String explanation;
    private ArrayList<String> references;
    private String correctAnswer;

    public Question() {}

    public Question(String questionId, String questionTopic, String question,
                    ArrayList<String> answers, String explanation, ArrayList<String> references,
                    String correctAnswer) {
        this.questionId = questionId;
        this.questionTopic = questionTopic;
        this.question = question;
        this.answers = answers;
        this.explanation = explanation;
        this.references = references;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getQuestionTopic() {
        return questionTopic;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
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
