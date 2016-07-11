package com.deenysoft.schoolbox.dashboard;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deenysoft.schoolbox.R;
import com.deenysoft.schoolbox.dashboard.adapter.SchoolDashAdapter;
import com.deenysoft.schoolbox.dashboard.detail.SchoolActivityDetail;
import com.deenysoft.schoolbox.dashboard.model.SchoolBoxItem;
import com.deenysoft.schoolbox.helper.TransitionHelper;
import com.deenysoft.schoolbox.model.JsonAttributes;
import com.deenysoft.schoolbox.widget.OffsetDecoration;


/**
 * Created by shamsadam on 10/06/16.
 */
public class DashboardSchoolFragment extends Fragment {

    // Adapter
    private SchoolDashAdapter mSchoolDashAdapter;
    private static final int REQUEST_CATEGORY = 0x2300;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard_school, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setUpDashboardGrid((RecyclerView) view.findViewById(R.id.dash_school_recycler)); // & merge recycler id to view object .. Called
        super.onViewCreated(view, savedInstanceState);
    }

    // Setting up recycler view with School Item Adapter
    private void setUpDashboardGrid(RecyclerView mRecyclerGridView) {
        final int spacing = getActivity().getResources()
                .getDimensionPixelSize(R.dimen.spacing_nano);
        mRecyclerGridView.addItemDecoration(new OffsetDecoration(spacing));
        // School Item Adapter
        mSchoolDashAdapter = new SchoolDashAdapter(getActivity());
        mSchoolDashAdapter.setOnItemClickListener(new SchoolDashAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Activity activity = getActivity();
                startQuizActivityWithTransition(activity,
                        view.findViewById(R.id.schoolTitle),
                        mSchoolDashAdapter.getItem(position));
            }
        });
        mRecyclerGridView.setAdapter(mSchoolDashAdapter);
    }



    private void startQuizActivityWithTransition(Activity activity, View toolbar,
                                                 SchoolBoxItem mSchoolBoxItem) {

        final Pair[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
                new Pair<>(toolbar, activity.getString(R.string.transition_toolbar)));
        @SuppressWarnings("unchecked")
        ActivityOptionsCompat sceneTransitionAnimation = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, pairs);

        // Start the activity with the participants, animating from one to the other.
        final Bundle transitionBundle = sceneTransitionAnimation.toBundle();
        Intent startIntent = SchoolActivityDetail.getStartIntent(activity, mSchoolBoxItem);
        ActivityCompat.startActivityForResult(activity,
                startIntent,
                REQUEST_CATEGORY,
                transitionBundle);
    }


}


