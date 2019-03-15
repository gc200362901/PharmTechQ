package appdevs.pharmtechq.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import appdevs.pharmtechq.R;

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.ViewHolder>{

    private ArrayList<String> topics;
    private Context context;

    public TopicsAdapter(Context context, ArrayList<String> topics) {
        this.topics = topics;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_topics_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //gets called every time a new topic item is added to list
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.textViewTopicItem.setText(topics.get(i));

        viewHolder.rootTopicItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Intent
                Toast.makeText(context, "TODO: Intent", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    //holds individual topic items in memory
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTopicItem;
        ConstraintLayout rootTopicItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTopicItem = itemView.findViewById(R.id.textViewTopicItem);
            rootTopicItem = itemView.findViewById(R.id.rootTopicItem);
        }
    }

}
