package appdevs.pharmtechq.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import appdevs.pharmtechq.QuizActivity;
import appdevs.pharmtechq.R;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {

    private ArrayList<String> answers;
    private Context context;
    private TextView textViewAnswerItem;
    private boolean answerClickable = true;

    ConstraintLayout rootExplanationConstraint;
    ConstraintLayout rootReferenceConstraint;


    public AnswersAdapter(Context context, ArrayList<String> answers,
                          ConstraintLayout rootExplanationConstraint, ConstraintLayout rootReferenceConstraint) {
        this.answers = answers;
        this.context = context;
        this.rootExplanationConstraint = rootExplanationConstraint;
        this.rootReferenceConstraint = rootReferenceConstraint;
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

        viewHolder.rootAnswerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewAnswerItem = v.findViewById(R.id.textViewAnswerItem);
                String selectedAnswer = textViewAnswerItem.getText().toString();

                if(answerClickable) {
                    if (QuizActivity.quizQuestions.get(QuizActivity.quizQuestionCount).getCorrectAnswer().equals(selectedAnswer)) {
                        textViewAnswerItem.setBackgroundResource(R.color.correctAnswerButton);
                        answerClickable = false;

                        rootExplanationConstraint.setVisibility(View.VISIBLE);
                        rootReferenceConstraint.setVisibility(View.VISIBLE);
                    } else {
                        textViewAnswerItem.setBackgroundResource(R.color.wrongAnswerButton);
                        answerClickable = false;

                        rootExplanationConstraint.setVisibility(View.VISIBLE);
                        rootReferenceConstraint.setVisibility(View.VISIBLE);
                    }
                }
                //TODO: load/show explanation and references
                //TODO: display next button
                //TODO: update progressbar
                //TODO: call onClick for next button to change activity
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
