package com.deenysoft.schoolbox.firebase.stack.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.deenysoft.schoolbox.R;
import com.deenysoft.schoolbox.firebase.adapter.StackPostViewHolder;
import com.deenysoft.schoolbox.firebase.model.PostItem;
import com.deenysoft.schoolbox.firebase.model.UserItem;
import com.deenysoft.schoolbox.firebase.stack.NewPostActivity;
import com.deenysoft.schoolbox.firebase.stack.PostDetailActivity;
import com.deenysoft.schoolbox.widget.ThemeUtil;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shamsadam on 15/06/16.
 */
public abstract class PostListFragment extends Fragment {

    public static final String REQUIRED = "Required";
    private static final String TAG = "PostListFragment";
    private  FloatingActionButton mFloatingActionButton;

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<PostItem, StackPostViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    private TextInputEditText mEditTextTitle;
    private TextInputEditText mEditTextPost;

    public PostListFragment() {}

    @Override
    public View onCreateView (final LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ThemeUtil.onActivityCreateSetTheme(getActivity()); // Set current theme
        View rootView = inflater.inflate(R.layout.fragment_stack_allpost, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = (RecyclerView) rootView.findViewById(R.id.messages_list);
        mRecycler.setHasFixedSize(true);


        // Post Link Button Init
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab_new_post);
        // Button launches NewPostDialog
        rootView.findViewById(R.id.fab_new_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(), NewPostActivity.class));
                final View mView = inflater.inflate(R.layout.stack_post_dialog, null);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                mBuilder.setView(mView);
                mBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do Nothing
                    }
                });

                mBuilder.setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Edit Texts Init
                        mEditTextTitle = (TextInputEditText) mView.findViewById(R.id.input_stack_title);
                        mEditTextPost = (TextInputEditText) mView.findViewById(R.id.input_stack_script);
                        // Call method submitPost
                        submitPost();
                    }
                });

                AlertDialog mAlert = mBuilder.create();
                mAlert.show();
            }
        });


        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<PostItem, StackPostViewHolder>(PostItem.class, R.layout.stack_item_post,
                StackPostViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final StackPostViewHolder viewHolder, final PostItem model, final int position) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                final String postKey = postRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
                        Intent intent = new Intent(getActivity(), PostDetailActivity.class);
                        intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, postKey);
                        startActivity(intent);
                    }
                });

                // Determine if the current user has liked this post and set UI accordingly
                if (model.stars.containsKey(getUid())) {
                    viewHolder.starView.setImageResource(R.drawable.ic_favorite);
                } else {
                    viewHolder.starView.setImageResource(R.drawable.ic_favorite_border);
                }

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToPost(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Need to write to both places the post is stored
                        DatabaseReference globalPostRef = mDatabase.child("posts").child(postRef.getKey());
                        DatabaseReference userPostRef = mDatabase.child("user-posts").child(model.uid).child(postRef.getKey());

                        // Run two transactions
                        onStarClicked(globalPostRef);
                        onStarClicked(userPostRef);
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);


    }

    // Submit & push post to cloud function
    public void submitPost(){
        // Get data from user's input
        final String mStackTitle = mEditTextTitle.getText().toString().trim();
        final String mStackPost = mEditTextPost.getText().toString().trim();

        // Title is required
        if (TextUtils.isEmpty(mStackTitle)) {
            mEditTextTitle.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(mStackPost)) {
            mEditTextPost.setError(REQUIRED);
            return;
        }

        if (!mStackTitle.isEmpty() && !mStackPost.isEmpty()) {
            // [START single_value_read]
            final String userId = getUid();
            mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Get user value
                            UserItem user = dataSnapshot.getValue(UserItem.class);

                            // [START_EXCLUDE]
                            if (user == null) {
                                // User is null, error out
                                Log.e(TAG, "User " + userId + " is unexpectedly null");
                                Toast.makeText(getActivity(),
                                        "Error: could not fetch user.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                // Write new post
                                writeNewPost(userId, user.username, mStackTitle, mStackPost);
                            }

                            // Finish this Activity, back to the stream
                            //finish();
                            // [END_EXCLUDE]
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        }
                    });
            // [END single_value_read]
        }
        else {
            // Print error to the user
            Toast.makeText(getActivity(), "Error! Text fields should not be left blank", Toast.LENGTH_LONG).show();
        }
    }


    // [START write_fan_out]
    private void writeNewPost(String userId, String username, String title, String body) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("posts").push().getKey();
        PostItem post = new PostItem(userId, username, title, body);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
        Toast.makeText(getActivity(), "Posted", Toast.LENGTH_LONG).show(); // Inform user
    }
    // [END write_fan_out]


    // [START post_stars_transaction]
    private void onStarClicked(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                PostItem p = mutableData.getValue(PostItem.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                if (p.stars.containsKey(getUid())) {
                    // Unstar the post and remove self from stars
                    p.starCount = p.starCount - 1;
                    p.stars.remove(getUid());
                } else {
                    // Star the post and add self to stars
                    p.starCount = p.starCount + 1;
                    p.stars.put(getUid(), true);
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }
    // [END post_stars_transaction]



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public abstract Query getQuery(DatabaseReference databaseReference);
}
