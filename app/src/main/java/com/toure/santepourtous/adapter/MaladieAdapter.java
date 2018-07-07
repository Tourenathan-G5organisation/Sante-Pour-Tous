package com.toure.santepourtous.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toure.santepourtous.ItemOnclickHandler;
import com.toure.santepourtous.R;
import com.toure.santepourtous.data.SantePourTous;

import java.util.List;

/**
 * Created by Toure Nathan on 6/20/2018.
 */
public class MaladieAdapter extends RecyclerView.Adapter<MaladieAdapter.ViewHolder> {

    private static final String LOG_TAC = MaladieAdapter.class.getSimpleName();

    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    final private ItemOnclickHandler mClickHandler;

    List<SantePourTous> mMaladieItems;

    public MaladieAdapter(ItemOnclickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.maladie_item_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SantePourTous item = mMaladieItems.get(position);
        holder.itemTitle.setText(item.getTitre());
        Log.d(LOG_TAC, "i: " + item.getId());
    }

    @Override
    public int getItemCount() {
        return (mMaladieItems == null) ? 0 : mMaladieItems.size();
    }


    public void setData(List<SantePourTous> maladieItems) {

        mMaladieItems = maladieItems;
        Log.d(LOG_TAC, "New set of data passed to adapter.");
        notifyDataSetChanged();

    }

    // Provide a reference to the views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView itemTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mMaladieItems.get(adapterPosition).getId());
        }
    }
}
