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

public class ReferencesAdapter extends RecyclerView.Adapter<ReferencesAdapter.ViewHolder> {

    private ArrayList<String> references;
    private Context context;

    public ReferencesAdapter(Context context, ArrayList<String> references) {
        this.references = references;
        this.context = context;
    }

    @NonNull
    @Override
    public ReferencesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_reference_item, viewGroup, false);
        ReferencesAdapter.ViewHolder viewHolder = new ReferencesAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReferencesAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textViewReference.setText(references.get(i));

        // removes the link from the reference supplied with a regex
        // and displays it in its own view
        final String PATTERN = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern r = Pattern.compile(PATTERN);
        Matcher m = r.matcher(references.get(i));
        if(m.find()) {
            viewHolder.textViewReferenceLink.setText(m.group(0));
        }
        else {
            final String error = "No link available";
            viewHolder.textViewReferenceLink.setText(error);
        }

        // replaces the link from the reference supplied with blank space and then trims it off,
        // leaving the reference description and displays it in its own view
        String referenceLink = references.get(i).replace(m.group(0), "");
        viewHolder.textViewReference.setText(referenceLink.trim());
    }

    @Override
    public int getItemCount() {
        return references.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewReference;
        TextView textViewReferenceLink;
        ConstraintLayout rootReferenceItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewReference = itemView.findViewById(R.id.textViewReference);
            textViewReferenceLink = itemView.findViewById(R.id.textViewReferenceLink);
            rootReferenceItem = itemView.findViewById(R.id.rootReferenceItem);
        }
    }

}
