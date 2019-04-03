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
import appdevs.pharmtechq.models.LearningHubEntry;

public class LearningHubAdapter extends RecyclerView.Adapter<LearningHubAdapter.ViewHolder> {
    
    private Context context;
    private ArrayList<LearningHubEntry> learningHubEntries;
    private boolean displayHeadingContents = true;
    
    public LearningHubAdapter(Context context, ArrayList<LearningHubEntry> learningHubEntries) {
        this.context = context;
        this.learningHubEntries = learningHubEntries;
    }

    @NonNull
    @Override
    public LearningHubAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_learninghub_item, viewGroup, false);
        LearningHubAdapter.ViewHolder viewHolder = new LearningHubAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final LearningHubAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textViewLearningHubContent.setVisibility(View.GONE);
        viewHolder.textViewLearningHubReference.setVisibility(View.GONE);
        viewHolder.textViewLearningHubHeading.setText(learningHubEntries.get(i).getLearningHubHeading());
        viewHolder.textViewLearningHubContent.setText(learningHubEntries.get(i).getLearningHubContents());
        viewHolder.textViewLearningHubReference.setText(learningHubEntries.get(i).getLearningHubReferences());

        viewHolder.textViewLearningHubHeading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(displayHeadingContents) {
                    viewHolder.textViewLearningHubContent.setVisibility(View.VISIBLE);
                    viewHolder.textViewLearningHubReference.setVisibility(View.VISIBLE);
                    displayHeadingContents = false;
                }
                else {
                    viewHolder.textViewLearningHubContent.setVisibility(View.GONE);
                    viewHolder.textViewLearningHubReference.setVisibility(View.GONE);
                    displayHeadingContents = true;
                }
            }
        });

        viewHolder.textViewLearningHubContent.setText(learningHubEntries.get(i).getLearningHubContents());
        viewHolder.textViewLearningHubReference.setText(learningHubEntries.get(i).getLearningHubReferences());
    }

    @Override
    public int getItemCount() {
        return learningHubEntries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewLearningHubHeading;
        TextView textViewLearningHubContent;
        TextView textViewLearningHubReference;
        ConstraintLayout rootViewLearningHubItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewLearningHubHeading = itemView.findViewById(R.id.textViewLearningHubHeading);
            textViewLearningHubContent = itemView.findViewById(R.id.textViewLearningHubContent);
            textViewLearningHubReference = itemView.findViewById(R.id.textViewLearningHubReference);
            rootViewLearningHubItem = itemView.findViewById(R.id.rootLearningHubItem);
        }
    }

}
