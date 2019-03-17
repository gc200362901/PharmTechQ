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

import appdevs.pharmtechq.R;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {

    private ArrayList<String> answers;
    private Context context;

    public AnswersAdapter(Context context, ArrayList<String> answers) {
        this.answers = answers;
        this.context = context;
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
                //TODO: change answer color green or red
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
