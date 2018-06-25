package com.toure.santepourtous.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toure.santepourtous.R;

/**
 * Created by Toure Nathan on 6/20/2018.
 */
public class BienetreAdapter extends RecyclerView.Adapter<BienetreAdapter.ViewHolder> {

    String[] listBienEtre = {
            "Nettoyage du colon",
            "Nettoyage du Froie",
            "Nettoyage du Rein",
            "Renforcement du system immunitaire",
            "Nettoyage du Coeur",
            "Soigner le mal de nerf et articulation",
            "Nottage general du corp",
            "Amelioration de la vue",
            "Prendre soin des cheveux",};

    public BienetreAdapter() {

    }

    void setData() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.maladie_item_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemTitle.setText(listBienEtre[position]);
    }

    @Override
    public int getItemCount() {
        //TODO set this to the number of item available for display
        return 9;
    }

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.item_title);
        }
    }
}
