package com.deenysoft.schoolbox.firebase.cloud;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.deenysoft.schoolbox.R;
import com.deenysoft.schoolbox.dashboard.DashboardActivity;
import com.deenysoft.schoolbox.dashboard.DashboardAssignmentFragment;
import com.deenysoft.schoolbox.dashboard.DashboardCourseFragment;
import com.deenysoft.schoolbox.dashboard.DashboardExamFragment;
import com.deenysoft.schoolbox.dashboard.DashboardNoteFragment;
import com.deenysoft.schoolbox.dashboard.DashboardPresentationFragment;
import com.deenysoft.schoolbox.dashboard.DashboardQuizFragment;
import com.deenysoft.schoolbox.dashboard.DashboardSchoolFragment;
import com.deenysoft.schoolbox.dashboard.DashboardTestFragment;
import com.deenysoft.schoolbox.firebase.cloud.fragment.LibraryFragment;
import com.deenysoft.schoolbox.firebase.cloud.fragment.MediaFragment;
import com.deenysoft.schoolbox.firebase.cloud.fragment.PhotoFragment;
import com.deenysoft.schoolbox.nest.ui.SettingsActivity;
import com.deenysoft.schoolbox.nest.ui.base.BaseActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by shamsadam on 19/06/16.
 */
public class UploadMainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private static final String TAG = "StorageMainActivity";

    private static final int RC_TAKE_PICTURE = 101;
    private static final int RC_STORAGE_PERMS = 102;
    private static final int RC_IMPORT_FILE = 103;

    private static final String KEY_FILE_URI = "key_file_uri";
    private static final String KEY_DOWNLOAD_URL = "key_download_url";

    // Pager Adapter & ViewPager
    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    private AdView mAdView;

    private BroadcastReceiver mDownloadReceiver;
    private FirebaseAuth mAuth;

    private Uri mDownloadUrl = null;
    private Uri mFileUri = null;
    private String mSelectedFile;

    private TextView txtDownloadURL;
    private TextView txtDownloadTitle;

    private TextView txtActivityTitle;

    private AppCompatButton mShareAppCompatButton;

    // [START declare_ref]
    private StorageReference mStorageRef;
    // [END declare_ref]


    private FloatingActionButton mFloatingActionButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_core_activity);
        setupToolbar();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Firebase Storage Reference
        // [START get_storage_ref]
        mStorageRef = FirebaseStorage.getInstance().getReference();
        // [END get_storage_ref]

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_new_upload);

        // Create the adapter that will return a fragment for each section
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[] {
                    new LibraryFragment(),
                    new PhotoFragment(),
                    new MediaFragment(),
            };
            private final String[] mFragmentNames = new String[] {
                    "LIBRARY",
                    "PHOTO",
                    "MEDIA"
            };
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }
            @Override
            public int getCount() {
                return mFragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };


        // Setting Up Banner Ads
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        // load and display ads
        mAdView.loadAd(adRequest);


/*
        //Inform user to wait a second
        //Toast.makeText(UploadMainActivity.this, "Fetching... Please Wait", Toast.LENGTH_SHORT).show();
        //Toast.makeText(UploadMainActivity.this, "Fetching... Please Wait", Toast.LENGTH_LONG).show();

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabTextColors(Color.parseColor("#EEEEEE"),Color.parseColor("#FFFFFF"));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
*/

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadMainActivity.this);
                mBuilder.setNegativeButton(R.string.camera, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        launchCamera();
                    }
                });

                mBuilder.setPositiveButton(R.string.library, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        launchImport();
                    }
                });

                AlertDialog mAlert = mBuilder.create();
                mAlert.show();
            }
        });

        /* ------------------- Dumped ----------------------------- */


        // Restore instance state
        if (savedInstanceState != null) {
            mFileUri = savedInstanceState.getParcelable(KEY_FILE_URI);
            mDownloadUrl = savedInstanceState.getParcelable(KEY_DOWNLOAD_URL);
        }

        // Download receiver
        mDownloadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "downloadReceiver:onReceive:" + intent);
                hideProgressDialog();

                if (MyDownloadService.ACTION_COMPLETED.equals(intent.getAction())) {
                    String path = intent.getStringExtra(MyDownloadService.EXTRA_DOWNLOAD_PATH);
                    long numBytes = intent.getLongExtra(MyDownloadService.EXTRA_BYTES_DOWNLOADED, 0);

                    // Alert success
                    showMessageDialog("Success", String.format(Locale.getDefault(),
                            "%d bytes downloaded from %s", numBytes, path));
                }

                if (MyDownloadService.ACTION_ERROR.equals(intent.getAction())) {
                    String path = intent.getStringExtra(MyDownloadService.EXTRA_DOWNLOAD_PATH);

                    // Alert failure
                    showMessageDialog("Error", String.format(Locale.getDefault(),
                            "Failed to download from %s", path));
                }
            }
        };

    }



    public void updateUI(){
        // Download URL and Download button
        if (mDownloadUrl != null) {
            txtDownloadURL = (TextView) findViewById(R.id.download_url);
            txtDownloadURL.setText(mDownloadUrl.toString());
            txtDownloadTitle = (TextView) findViewById(R.id.share_title);
            txtDownloadTitle.setText(R.string.share_title);
            mShareAppCompatButton = (AppCompatButton) findViewById(R.id.shareButton);
            mShareAppCompatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(android.content.Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    // Add data to the intent, the receiving app will decide what to do with it.
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Check This Out");
                    intent.putExtra(Intent.EXTRA_TEXT, mDownloadUrl.toString());

                    startActivity(Intent.createChooser(intent, "How do you want to share?"));
                    finish();
                }
            });
            findViewById(R.id.perform_activity).setVisibility(View.GONE);
            findViewById(R.id.layout_download_url).setVisibility(View.VISIBLE);
            findViewById(R.id.shareButton).setVisibility(View.VISIBLE);
        } else {
            txtDownloadURL = (TextView) findViewById(R.id.download_url);
            txtDownloadURL.setText(null);
            txtDownloadTitle = (TextView) findViewById(R.id.share_title);
            txtDownloadTitle.setText(null);
            findViewById(R.id.layout_download_url).setVisibility(View.GONE);
            findViewById(R.id.shareButton).setVisibility(View.GONE);
            findViewById(R.id.perform_activity).setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void onStart() {
        super.onStart();
        // Register download receiver
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mDownloadReceiver, MyDownloadService.getIntentFilter());
    }


    @Override
    public void onStop() {
        super.onStop();

        // Unregister download receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mDownloadReceiver);
    }

    // [START add_lifecycle_methods]
    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
    // [END add_lifecycle_methods]

    @Override
    public void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putParcelable(KEY_FILE_URI, mFileUri);
        out.putParcelable(KEY_DOWNLOAD_URL, mDownloadUrl);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);
        // Check if request code equals camera code
        if (requestCode == RC_TAKE_PICTURE) {
            if (resultCode == RESULT_OK) {
                if (mFileUri != null) {
                    uploadFromUri(mFileUri);
                } else {
                    Log.w(TAG, "File URI is null");
                }
            } else {
                Toast.makeText(this, "Taking picture failed.", Toast.LENGTH_SHORT).show();
            }

        }

        // Check if request code equals import file code
        if (requestCode == RC_IMPORT_FILE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    mFileUri = data.getData();
                    mSelectedFile = FilePath.getPath(this, mFileUri);
                    Log.w(TAG,"Selected File Path:" + mSelectedFile);
                    if(mSelectedFile != null && !mSelectedFile.equals("")){
                        ImportFromUri(mFileUri);
                    } else {
                        Toast.makeText(UploadMainActivity.this,"Cannot upload file",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else {
                    Toast.makeText(UploadMainActivity.this, "File URI unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // [START upload_from_uri]
    private void uploadFromUri(Uri fileUri) {
        Log.d(TAG, "uploadFromUri:src:" + fileUri.toString());

        // [START get_child_ref]
        // Get a reference to store file at photos/<FILENAME>.jpg
        final StorageReference photoRef = mStorageRef.child("photos")
                .child(fileUri.getLastPathSegment());
        // [END get_child_ref]

        // Upload file to Firebase Storage
        // [START_EXCLUDE]
        showProgressDialog();
        // [END_EXCLUDE]
        Log.d(TAG, "uploadFromUri:dst:" + photoRef.getPath());
        photoRef.putFile(fileUri)
                .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Upload succeeded
                        // Get the public download URL
                        mDownloadUrl = taskSnapshot.getMetadata().getDownloadUrl();


                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                        updateUI();
                        Toast.makeText(UploadMainActivity.this, "Uploaded Successfully",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Upload failed
                        Log.w(TAG, "uploadFromUri:onFailure", exception);

                        mDownloadUrl = null;

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        updateUI();
                        Toast.makeText(UploadMainActivity.this, "Error: File upload failed",
                                Toast.LENGTH_SHORT).show();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END upload_from_uri]


    // [START import_from_uri]
    private void ImportFromUri(Uri fileUri) {
        Log.d(TAG, "importFromUri:src:" + fileUri.toString());

        // [START get_child_ref]
        // Get a reference to store file at media
        final StorageReference mediaRef = mStorageRef.child("media")
                .child(fileUri.getLastPathSegment());
        // [END get_child_ref]

        // Upload file to Firebase Storage
        // [START_EXCLUDE]
        showProgressDialog();
        // [END_EXCLUDE]
        Log.d(TAG, "importFromUri:dst:" + mediaRef.getPath());
        mediaRef.putFile(fileUri)
                .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Upload succeeded
                        // Get the public download URL
                        mDownloadUrl = taskSnapshot.getMetadata().getDownloadUrl();


                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                        updateUI();
                        Toast.makeText(UploadMainActivity.this, "Uploaded Successfully",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Upload failed

                        mDownloadUrl = null;

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        updateUI();
                        Toast.makeText(UploadMainActivity.this, "Error: File upload failed",
                                Toast.LENGTH_SHORT).show();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END upload_from_uri]

    @AfterPermissionGranted(RC_STORAGE_PERMS)
    private void launchCamera() {
        Log.d(TAG, "launchCamera");

        // Check that we have permission to read images from external storage.
        String perm = android.Manifest.permission.READ_EXTERNAL_STORAGE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !EasyPermissions.hasPermissions(this, perm)) {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_storage),
                    RC_STORAGE_PERMS, perm);
            return;
        }

        // Create intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Choose file storage location
        File file = new File(Environment.getExternalStorageDirectory(), UUID.randomUUID().toString() + ".jpg");
        mFileUri = Uri.fromFile(file);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);

        // Launch intent
        startActivityForResult(takePictureIntent, RC_TAKE_PICTURE);

    }


    @AfterPermissionGranted(RC_STORAGE_PERMS)
    private void launchImport() {
        // Check that we have permission to read images from external storage.
        String perm = android.Manifest.permission.READ_EXTERNAL_STORAGE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !EasyPermissions.hasPermissions(this, perm)) {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_storage),
                    RC_STORAGE_PERMS, perm);
            return;
        }

        // Create Intent for Import
        Intent mImport = new Intent(Intent.ACTION_GET_CONTENT);
        mImport.setType("image/*|application/pdf/*|audio/*");
        mImport.setAction(Intent.ACTION_GET_CONTENT);
        mImport.addCategory(Intent.CATEGORY_OPENABLE);
        //File mFile = new File(Environment.getExternalStorageDirectory(), UUID.randomUUID().toString() + ".jpg");
        //mFileUri = Uri.fromFile(mFile);
        //mImport.putExtra(Intent.ACTION_GET_CONTENT, mFileUri);
        startActivityForResult(Intent.createChooser(mImport,"Choose File"), RC_IMPORT_FILE);

    }



//  Dumped - No longer needed
    private void signInAnonymously() {
        // Sign in anonymously. Authentication is required to read or write from Firebase Storage.
        showProgressDialog();
        mAuth.signInAnonymously()
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "signInAnonymously:SUCCESS");
                        hideProgressDialog();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e(TAG, "signInAnonymously:FAILURE", exception);
                        hideProgressDialog();
                    }
                });
    }


    // Dumped - No longer Needed
    private void beginDownload() {
        // Get path
        String path = "photos/" + mFileUri.getLastPathSegment();

        // Kick off download service
        Intent intent = new Intent(this, MyDownloadService.class);
        intent.setAction(MyDownloadService.ACTION_DOWNLOAD);
        intent.putExtra(MyDownloadService.EXTRA_DOWNLOAD_PATH, path);
        startService(intent);

        // Show loading spinner
        showProgressDialog();
    }



    private void showMessageDialog(String title, String message) {
        AlertDialog ad = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .create();
        ad.show();
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {}

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {}


    // Set up toolbar
    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_school:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_course:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_quiz:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_test:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_note:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_exam:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_assignment:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_presentation:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean providesActivityToolbar() {
        return false;
    }


}
