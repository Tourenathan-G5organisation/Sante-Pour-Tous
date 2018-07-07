package com.toure.santepourtous;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toure.santepourtous.data.AppDatabase;
import com.toure.santepourtous.data.SantePourTous;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public static final String LOG_TAC = DetailActivityFragment.class.getSimpleName();

    public static final String ITEM_ID_KEY = "item_id_key";
    final int DEFAULT_ITEM_ID = -1;
    int mItemDbId;

    // App Database reference
    AppDatabase mDb;
    LiveData<SantePourTous> mSantePourTousItem;

    TextView itemTitleTextview;
    TextView itemAstuceTitleTextview;
    TextView itemAstuceTextview;
    TextView itemConseilleTextview;
    TextView itemConseilleTitreTextview;

    public DetailActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        itemTitleTextview = view.findViewById(R.id.item_title);
        itemAstuceTextview = view.findViewById(R.id.item_astuce);
        itemAstuceTitleTextview = view.findViewById(R.id.item_astuce_titre);
        itemConseilleTextview = view.findViewById(R.id.item_conseille);
        itemConseilleTitreTextview = view.findViewById(R.id.item_conseille_titre);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ITEM_ID_KEY, mItemDbId);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDb = AppDatabase.getsInstance(getActivity().getApplicationContext());

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(ITEM_ID_KEY)) {
            mItemDbId = getActivity().getIntent().getIntExtra(ITEM_ID_KEY, DEFAULT_ITEM_ID);
            mSantePourTousItem = mDb.santePourTousDao().getMaladieItemById(mItemDbId);
            mSantePourTousItem.observe(this, new Observer<SantePourTous>() {
                @Override
                public void onChanged(@Nullable SantePourTous santePourTous) {
                    try {
                        populateView(santePourTous);
                    } catch (NullPointerException e) {
                        Log.d(LOG_TAC, e.getMessage());
                    }
                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * USe to populate the views depending on the data available
     *
     * @param santePourTous
     */
    void populateView(SantePourTous santePourTous) {
        itemTitleTextview.setText(santePourTous.getTitre());
        itemAstuceTextview.setText(santePourTous.getAstuce());
        if (null != santePourTous.getConseille())
            itemConseilleTitreTextview.setVisibility(View.VISIBLE);
        itemConseilleTextview.setVisibility(View.VISIBLE);
        itemConseilleTextview.setText(santePourTous.getConseille());
    }
}
