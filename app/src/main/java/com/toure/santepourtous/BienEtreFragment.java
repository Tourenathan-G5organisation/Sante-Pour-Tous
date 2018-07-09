package com.toure.santepourtous;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.toure.santepourtous.adapter.BienetreAdapter;
import com.toure.santepourtous.data.AppDatabase;
import com.toure.santepourtous.data.AppExecutors;
import com.toure.santepourtous.data.SantePourTous;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BienEtreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BienEtreFragment extends Fragment implements ItemOnclickHandler {

    public static final String DATA_REFERENCE_KEY = "bienetre";
    private static final String LOG_TAC = BienEtreFragment.class.getSimpleName();
    private static final String ARG_FRAGMENT_POSITION = "fragment_position";

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mBienEtreDataReference;
    ChildEventListener mChildEventListener;
    // App Database reference
    AppDatabase mDb;
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
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mBienEtreDataReference = mFirebaseDatabase.getReference().child(DATA_REFERENCE_KEY);
        mDb = AppDatabase.getsInstance(getContext().getApplicationContext());
        mBienEtreDataReference.keepSynced(true);
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {
                final SantePourTous dbItem = dataSnapshot.getValue(SantePourTous.class);
                if (dbItem != null) {
                    dbItem.setFirebaseId(dataSnapshot.getKey());

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            final long id = mDb.santePourTousDao().insert(dbItem);
                            if (id <= -1) {
                                onChildChanged(dataSnapshot, s);
                            }
                            Log.d(LOG_TAC, "New Item inserted. id: " + id);
                        }
                    });
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final SantePourTous item = dataSnapshot.getValue(SantePourTous.class);
                final LiveData<SantePourTous> dbItem = mDb.santePourTousDao()
                        .getSantePourTousByFirebaseId(dataSnapshot.getKey());
                dbItem.observe(BienEtreFragment.this, new Observer<SantePourTous>() {
                    @Override
                    public void onChanged(@Nullable SantePourTous santePourTous) {
                        dbItem.removeObserver(this);
                        if (santePourTous != null && item != null) {
                            item.setId(santePourTous.getId());
                            item.setFirebaseId(santePourTous.getFirebaseId());
                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDb.santePourTousDao().update(item);
                                    Log.d(LOG_TAC, "Item updated");
                                }
                            });
                        } else {
                            Log.d(LOG_TAC, "Null object");
                        }

                    }
                });
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                final LiveData<SantePourTous> dbItem = mDb.santePourTousDao()
                        .getSantePourTousByFirebaseId(dataSnapshot.getKey());
                final SantePourTous item = dataSnapshot.getValue(SantePourTous.class);

                dbItem.observe(BienEtreFragment.this, new Observer<SantePourTous>() {
                    @Override
                    public void onChanged(@Nullable SantePourTous santePourTous) {
                        dbItem.removeObserver(this);
                        if (santePourTous != null && item != null) {
                            item.setId(santePourTous.getId());
                            item.setFirebaseId(santePourTous.getFirebaseId());
                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDb.santePourTousDao().delete(item);
                                    Log.d(LOG_TAC, "Item deleted");
                                }
                            });
                        } else {
                            Log.d(LOG_TAC, "Null object");
                        }

                    }
                });
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(LOG_TAC, "bienetre:onCancelled", databaseError.toException());
            }
        };
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
        mBienEtreDataReference.addChildEventListener(mChildEventListener);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBienEtreDataReference.removeEventListener(mChildEventListener);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LiveData<List<SantePourTous>> bienEtreItems = mDb.santePourTousDao().getAllBienEtreItems();
        bienEtreItems.observe(BienEtreFragment.this, new Observer<List<SantePourTous>>() {
            @Override
            public void onChanged(@Nullable List<SantePourTous> santePourTouses) {
                mBienetreAdapter.setData(santePourTouses);
            }
        });
    }

    @Override
    public void onClick(int itemId) {
        Context context = getContext();
        Intent intentToStartDetailActivity = new Intent(context, DetailActivity.class);
        intentToStartDetailActivity.putExtra(DetailActivityFragment.ITEM_ID_KEY, itemId);
        startActivity(intentToStartDetailActivity);
    }
}
