package com.toure.santepourtous;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toure.santepourtous.adapter.BienetreAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BienEtreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BienEtreFragment extends Fragment implements ItemOnclickHandler {

    private static final String LOG = BienEtreFragment.class.getSimpleName();

    private static final String ARG_FRAGMENT_POSITION = "fragment_position";
    private int mFragPosition;

    private RecyclerView mRecyclerView;
    private BienetreAdapter mBienetreAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public BienEtreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position Position of the fragment in the viewpager.
     * @return A new instance of fragment BienEtreFragment.
     */
    public static BienEtreFragment newInstance(int position) {
        BienEtreFragment fragment = new BienEtreFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_FRAGMENT_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFragPosition = getArguments().getInt(ARG_FRAGMENT_POSITION);
        }
        mBienetreAdapter = new BienetreAdapter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bien_etre, container, false);

        mRecyclerView = view.findViewById(R.id.bien_etre_recycler_view);
         /*
         * A LinearLayoutManager is responsible for measuring and positioning item views within a
         * RecyclerView into a linear list. This means that it can produce either a horizontal or
         * vertical list depending on which parameter you pass in to the LinearLayoutManager
         * constructor. In our case, we want a vertical list, so we pass in the constant from the
         * LinearLayoutManager class for vertical lists, LinearLayoutManager.VERTICAL.
         *
         * There are other LayoutManagers available to display your data in uniform grids,
         * staggered grids, and more! See the developer documentation for more details.
         */
        int recyclerViewOrientation = LinearLayoutManager.VERTICAL;

        /*
         *  This value should be true if you want to reverse your layout. Generally, this is only
         *  true with horizontal lists that need to support a right-to-left layout.
         */
        boolean shouldReverseLayout = false;
        mLayoutManager = new LinearLayoutManager(getContext(), recyclerViewOrientation, shouldReverseLayout);
        mRecyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(divider);
          /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mBienetreAdapter);

        return view;
    }

    @Override
    public void onClick() {
        Context context = getContext();
        Intent intentToStartDetailActivity = new Intent(context, DetailActivity.class);
        startActivity(intentToStartDetailActivity);
    }
}
