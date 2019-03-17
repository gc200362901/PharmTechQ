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
        viewHolder.textViewRefInfo.setText(references.get(i));
    }

    @Override
    public int getItemCount() {
        return references.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewRefInfo;
        ConstraintLayout rootReferenceItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewRefInfo = itemView.findViewById(R.id.textViewRefInfo);
            rootReferenceItem = itemView.findViewById(R.id.rootReferenceItem);
        }
    }

}
