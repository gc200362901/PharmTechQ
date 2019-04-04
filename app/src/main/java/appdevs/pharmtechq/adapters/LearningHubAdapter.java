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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        viewHolder.textViewLearningHubReferenceLink.setVisibility(View.GONE);

        //displays the headings for the learning hub
        viewHolder.textViewLearningHubHeading.setText(learningHubEntries.get(i).getLearningHubHeading());

        //onClick listener for the learning hub headings
        viewHolder.textViewLearningHubHeading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(displayHeadingContents) {
                    viewHolder.textViewLearningHubContent.setVisibility(View.VISIBLE);
                    viewHolder.textViewLearningHubReference.setVisibility(View.VISIBLE);
                    viewHolder.textViewLearningHubReferenceLink.setVisibility(View.VISIBLE);

                    displayHeadingContents = false;
                }
                else {
                    viewHolder.textViewLearningHubContent.setVisibility(View.GONE);
                    viewHolder.textViewLearningHubReference.setVisibility(View.GONE);
                    viewHolder.textViewLearningHubReferenceLink.setVisibility(View.GONE);

                    displayHeadingContents = true;
                }
            }
        });

        //displays the content for the selected heading
        viewHolder.textViewLearningHubContent.setText(learningHubEntries.get(i).getLearningHubContents());

        // removes the link from the reference supplied with a regex
        // and displays it in its own view
        final String PATTERN = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern r = Pattern.compile(PATTERN);
        Matcher m = r.matcher(learningHubEntries.get(i).getLearningHubReferences());
        if(m.find()) {
            viewHolder.textViewLearningHubReferenceLink.setText(m.group(0));
        }
        else {
            final String error = "No link available";
            viewHolder.textViewLearningHubReferenceLink.setText(error);
        }

        // replaces the link from the reference supplied with blank space and then trims it off,
        // leaving the reference description and displays it in its own view
        String referenceLink = learningHubEntries.get(i).getLearningHubReferences().replace(m.group(0), "");
        viewHolder.textViewLearningHubReference.setText(referenceLink.trim());
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
        TextView textViewLearningHubReferenceLink;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewLearningHubHeading = itemView.findViewById(R.id.textViewLearningHubHeading);
            textViewLearningHubContent = itemView.findViewById(R.id.textViewLearningHubContent);
            textViewLearningHubReference = itemView.findViewById(R.id.textViewLearningHubReference);
            rootViewLearningHubItem = itemView.findViewById(R.id.rootLearningHubItem);
            textViewLearningHubReferenceLink = itemView.findViewById(R.id.textViewLearningHubReferenceLink);
        }
    }

}
