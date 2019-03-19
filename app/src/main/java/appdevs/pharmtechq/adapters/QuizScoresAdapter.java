package appdevs.pharmtechq.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Pattern;

import appdevs.pharmtechq.R;

public class QuizScoresAdapter extends RecyclerView.Adapter<QuizScoresAdapter.ViewHolder> {

    private static final String TAG = "QuizScoresAdapter";

    private ArrayList<String> quizTopics;
    private ArrayList<String> quizResults;
    private Context context;

    public QuizScoresAdapter(Context context, ArrayList<String> quizTopics, ArrayList<String> quizResults) {
        this.quizTopics = quizTopics;
        this.quizResults = quizResults;
        this.context = context;
    }

    @NonNull
    @Override
    public QuizScoresAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_quizscore_item, viewGroup, false);
        QuizScoresAdapter.ViewHolder viewHolder = new QuizScoresAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuizScoresAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textViewTopic.setText(quizTopics.get(i));

        String[] tmpResults = quizResults.get(i).split(Pattern.quote("."));
        if(Integer.parseInt(tmpResults[0]) < 49) {
            viewHolder.textViewResult.setTextColor(context.getResources().getColor(R.color.under_50_percent));
        }
        else if(Integer.parseInt(tmpResults[0]) >= 50 && Integer.parseInt(tmpResults[0]) < 79) {
            viewHolder.textViewResult.setTextColor(context.getResources().getColor(R.color.under_80_percent));
        }
        else {
            viewHolder.textViewResult.setTextColor(context.getResources().getColor(R.color.under_100_percent));
        }

        viewHolder.textViewResult.setText(quizResults.get(i));
    }

    @Override
    public int getItemCount() {
        return quizTopics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTopic;
        TextView textViewResult;
        ConstraintLayout rootQuizScoreItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTopic = itemView.findViewById(R.id.textViewTopic);
            textViewResult = itemView.findViewById(R.id.textViewResult);
            rootQuizScoreItem = itemView.findViewById(R.id.rootQuizScoreItem);
        }
    }
}
