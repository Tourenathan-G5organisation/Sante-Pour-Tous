package com.toure.santepourtous.adapter;

import android.content.Context;
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
public class BienetreAdapter extends RecyclerView.Adapter<BienetreAdapter.ViewHolder> {

    private static final String LOG_TAC = BienetreAdapter.class.getSimpleName();

    final private ItemOnclickHandler mClickHandler;
    List<SantePourTous> mBienEtreItems;

    public BienetreAdapter(ItemOnclickHandler clickHandler) {
        mClickHandler = clickHandler;
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
        SantePourTous item = mBienEtreItems.get(position);
        holder.itemTitle.setText(item.getTitre());
        Log.d(LOG_TAC, "bien" + item.getId());
    }

    @Override
    public int getItemCount() {
        return (mBienEtreItems == null) ? 0 : mBienEtreItems.size();
    }

    public void setData(List<SantePourTous> bienEtreItems) {

        mBienEtreItems = bienEtreItems;
        Log.d(LOG_TAC, "New set of data passed to bienetre adapter.");
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
            mClickHandler.onClick(mBienEtreItems.get(adapterPosition).getId());
        }
    }
}
