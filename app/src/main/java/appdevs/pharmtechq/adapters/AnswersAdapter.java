package appdevs.pharmtechq.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import appdevs.pharmtechq.QuizActivity;
import appdevs.pharmtechq.R;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {

    private ArrayList<String> answers;
    private Context context;
    private TextView textViewAnswerItem;
    private boolean answerClickable = true;
    private boolean explanationShow = true;
    private boolean referencesShow = true;
    private ConstraintLayout rootExplanationConstraint;
    private ConstraintLayout rootReferenceConstraint;
    private TextView textViewExplanation;
    private RecyclerView recyclerViewReferences;
    private ProgressBar progressBarQuiz;
    private Button buttonNext;
    final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / QuizActivity.quizQuestions.size());

    public AnswersAdapter(Context context, ArrayList<String> answers,
                          ConstraintLayout rootExplanationConstraint,
                          ConstraintLayout rootReferenceConstraint,
                          TextView textViewExplanation, RecyclerView recyclerViewReferences,
                          ProgressBar progressBarQuiz, Button buttonNext) {
        this.answers = answers;
        this.context = context;
        this.rootExplanationConstraint = rootExplanationConstraint;
        this.rootReferenceConstraint = rootReferenceConstraint;
        this.textViewExplanation = textViewExplanation;
        this.recyclerViewReferences = recyclerViewReferences;
        this.progressBarQuiz = progressBarQuiz;
        this.buttonNext = buttonNext;
    }

    @NonNull
    @Override
    public AnswersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_answers_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textViewAnswerItem.setText(answers.get(i));
        buttonNext.setEnabled(false);
        //onclick listener for answer buttons
        viewHolder.rootAnswerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewAnswerItem = v.findViewById(R.id.textViewAnswerItem);
                String selectedAnswer = textViewAnswerItem.getText().toString();

                //checks if an answer has been clicked already to avoid multiple attempts
                if(answerClickable) {
                    //checks to see if the answer selected is corrected and
                    // if so, changed background to green
                    //increases totalAttempts and correctAttempts counter by 1
                    //enables next button
                    if (QuizActivity.quizQuestions.get(QuizActivity.quizQuestionCount)
                            .getCorrectAnswer().equals(selectedAnswer)) {
                        textViewAnswerItem.setBackgroundResource(R.color.correctAnswerButton);
                        QuizActivity.correctAttempts =+ QuizActivity.correctAttempts + 1;
                        QuizActivity.totalAttempts =+ QuizActivity.totalAttempts +1;
                        answerClickable = false;
                        progressBarQuiz.incrementProgressBy(PROGRESS_BAR_INCREMENT);
                        buttonNext.setEnabled(true);

                        //makes the explanation and reference labels visible
                        rootExplanationConstraint.setVisibility(View.VISIBLE);
                        rootReferenceConstraint.setVisibility(View.VISIBLE);
                    //answer selected was wrong and changes background to red
                    //increases totalAttempts counter by 1
                    //enables next button
                    } else {
                        textViewAnswerItem.setBackgroundResource(R.color.wrongAnswerButton);
                        QuizActivity.totalAttempts =+ QuizActivity.totalAttempts +1;
                        answerClickable = false;
                        progressBarQuiz.incrementProgressBy(PROGRESS_BAR_INCREMENT);
                        buttonNext.setEnabled(true);

                        //makes the explanation and reference labels visible
                        rootExplanationConstraint.setVisibility(View.VISIBLE);
                        rootReferenceConstraint.setVisibility(View.VISIBLE);

                        //onclick listener for the explanation label
                        //displays explanation for question if label is clicked
                        rootExplanationConstraint.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String currentExplanation = QuizActivity.quizQuestions
                                        .get(QuizActivity.quizQuestionCount).getExplanation();
                                textViewExplanation.setText(currentExplanation);

                                if(explanationShow) {
                                    textViewExplanation.setVisibility(View.VISIBLE);
                                    explanationShow = false;
                                }
                                else {
                                    textViewExplanation.setVisibility(View.GONE);
                                    explanationShow = true;
                                }
                            }
                        });

                        //onclick listener for the resources label
                        //displays references for question if the label is clicked
                        rootReferenceConstraint.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(referencesShow) {
                                    recyclerViewReferences.setVisibility(View.VISIBLE);
                                    referencesShow = false;
                                }
                                else {
                                    recyclerViewReferences.setVisibility(View.GONE);
                                    referencesShow = true;
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAnswerItem;
        ConstraintLayout rootAnswerItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewAnswerItem = itemView.findViewById(R.id.textViewAnswerItem);
            rootAnswerItem = itemView.findViewById(R.id.rootAnswerItem);
        }
    }

}
