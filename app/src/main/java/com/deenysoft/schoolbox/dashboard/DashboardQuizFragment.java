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
import com.deenysoft.schoolbox.dashboard.adapter.QuizDashAdapter;
import com.deenysoft.schoolbox.dashboard.detail.QuizActivityDetail;
import com.deenysoft.schoolbox.dashboard.model.QuizBoxItem;
import com.deenysoft.schoolbox.helper.TransitionHelper;
import com.deenysoft.schoolbox.widget.OffsetDecoration;

/**
 * Created by shamsadam on 23/06/16.
 */
public class DashboardQuizFragment extends Fragment {

    // Adapter
    private QuizDashAdapter mQuizDashAdapter;
    private static final int REQUEST_CATEGORY = 0x2300;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard_quiz, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setUpDashboardGrid((RecyclerView) view.findViewById(R.id.dash_quiz_recycler)); // & merge recycler id to view object .. Called
        super.onViewCreated(view, savedInstanceState);
    }

    // Setting up recycler view with Quiz Item Adapter
    private void setUpDashboardGrid(RecyclerView mRecyclerGridView) {
        final int spacing = getActivity().getResources()
                .getDimensionPixelSize(R.dimen.spacing_nano);
        mRecyclerGridView.addItemDecoration(new OffsetDecoration(spacing));
        // Quiz Item Adapter
        mQuizDashAdapter = new QuizDashAdapter(getActivity());
        mQuizDashAdapter.setOnItemClickListener(new QuizDashAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                // Do Something
                Activity activity = getActivity();
                startQuizActivityWithTransition(activity,
                        view.findViewById(R.id.quizTitle),
                        mQuizDashAdapter.getItem(position));
            }
        });
        mRecyclerGridView.setAdapter(mQuizDashAdapter);
    }



    private void startQuizActivityWithTransition(Activity activity, View toolbar,
                                                 QuizBoxItem mQuizBoxItem) {

        final Pair[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
                new Pair<>(toolbar, activity.getString(R.string.transition_toolbar)));
        @SuppressWarnings("unchecked")
        ActivityOptionsCompat sceneTransitionAnimation = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, pairs);

        // Start the activity with the participants, animating from one to the other.
        final Bundle transitionBundle = sceneTransitionAnimation.toBundle();
        Intent startIntent = QuizActivityDetail.getStartIntent(activity, mQuizBoxItem);
        ActivityCompat.startActivityForResult(activity,
                startIntent,
                REQUEST_CATEGORY,
                transitionBundle);

    }


}
