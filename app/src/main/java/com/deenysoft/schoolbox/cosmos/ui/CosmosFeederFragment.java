package com.deenysoft.schoolbox.cosmos.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ListView;
import android.widget.Toast;

import com.deenysoft.schoolbox.R;
import com.deenysoft.schoolbox.cosmos.adapter.CosmosListAdapter;
import com.deenysoft.schoolbox.cosmos.loader.CosmosFeedsLoader;
import com.deenysoft.schoolbox.cosmos.model.CosmosFeedItem;

import java.io.IOException;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by shamsadam on 28/03/16.
 */
public class CosmosFeederFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<List<CosmosFeedItem>> {

    private CosmosListAdapter mAdapter;
    private static final int RC_STORAGE_PERMS = 102;

    // id specific to the ListFragment's LoaderManager
    private static final int LOADER_ID = 1;
    private  MediaPlayer mediaPlayer;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        getListView().setDivider(null);
        getListView().setSelector(new ColorDrawable(0x0));
        mAdapter = new CosmosListAdapter();
        setEmptyText(getResources().getString(R.string.no_feeds));
        setListAdapter(mAdapter);
        setListShown(false);

        //Initialize a Loader with id '1'. If the Loader with this id already
        // exists, then the LoaderManager will reuse the existing Loader.
        getLoaderManager().initLoader(LOADER_ID, null, this);


    }

    @AfterPermissionGranted(RC_STORAGE_PERMS)
    @Override
    public void onListItemClick(ListView l, View v, final int position, long id) {
        super.onListItemClick(l, v, position, id);

        LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
        final CosmosFeeder cFeeder = new CosmosFeeder();
        final Animation aAnim = new AlphaAnimation(1.0f, 0.8f);
        aAnim.setFillAfter(true);
        v.startAnimation(aAnim);

        //Creating a new feed item
        final CosmosFeedItem feedItem = (CosmosFeedItem) mAdapter.getItem(position);

        //Creating Alert Dialog Builder
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        mBuilder.setView(mLayoutInflater.inflate(R.layout.cos_dialog, null))
                .setPositiveButton(R.string.download, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Check that we have permission to read and write to external storage.
                        String perm = Manifest.permission.WRITE_EXTERNAL_STORAGE;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                                && !EasyPermissions.hasPermissions(getActivity(), perm)) {
                            EasyPermissions.requestPermissions(getActivity(), getString(R.string.rationale_storage),
                                    RC_STORAGE_PERMS, perm);
                            return;
                        }

                        //Proceed to Download Manager after getting permission
                        if (feedItem.getHyperlink() != null) {
                            if (isNetworkConnectedOnline()) {
                                // Create Download Manager to handle Download Service.
                                String DownloadURL = feedItem.getHyperlink();
                                DownloadManager.Request mRequest = new DownloadManager.Request(Uri.parse(DownloadURL));
                                mRequest.setTitle("Deenysoft");
                                mRequest.setDescription("Podcast is being download");
                                mRequest.allowScanningByMediaScanner();

                                mRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                String PodCastTitle = URLUtil.guessFileName(DownloadURL, null, MimeTypeMap.getFileExtensionFromUrl(DownloadURL));
                                mRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, PodCastTitle);

                                DownloadManager mManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                                mManager.enqueue(mRequest);

                                Toast.makeText(getActivity(), "Downloading Podcast", Toast.LENGTH_LONG).show();

                            } else {

                                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();

                            }

                        } else {

                            Toast.makeText(getActivity(), "Resource not found", Toast.LENGTH_LONG).show();

                        }

                    }

                })
                .setNegativeButton(R.string.stream, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // Check if feedItem hyperlink is not null in order to stream
                                if (feedItem.getHyperlink() != null) {
                                    if (isNetworkConnectedOnline()) {
                                        if (mediaPlayer != null) {
                                            mediaPlayer.release();
                                            mediaPlayer = null;
                                        }
                                        mediaPlayer = new MediaPlayer();
                                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                        try {
                                            String url = feedItem.getHyperlink();
                                            mediaPlayer.setDataSource(url);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        cFeeder.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity(), "Buffering... Please Wait", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                        try {
                                            mediaPlayer.prepare(); // Take some time to buffer
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        mediaPlayer.start();
                                        cFeeder.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                startActivity(new Intent(getActivity(), CosmosPlayer.class));
                                                Toast.makeText(getActivity(), "Enjoy Podcast", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    } else {
                                        cFeeder.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }


                                } else {
                                    cFeeder.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity(), "Resource not found", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                        }).start();

                    }
                });

        AlertDialog mAlert = mBuilder.create();
        mAlert.show();

    }



    // Checking Network Connection
    public boolean isNetworkConnectedOnline() {
        boolean status = false;
        try {
            ConnectivityManager mCManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetInfo = mCManager.getActiveNetworkInfo();
            if (mNetInfo != null && mNetInfo.isConnectedOrConnecting()) {
                status = mNetInfo.isConnectedOrConnecting();
                //Toast.makeText(this, "Connecting...", Toast.LENGTH_SHORT).show();
                return status;
            }
            else {
                //Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                return status;
            }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }




    @Override
    public Loader<List<CosmosFeedItem>> onCreateLoader(int i, Bundle bundle) {
        return new CosmosFeedsLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<CosmosFeedItem>> listLoader, List<CosmosFeedItem> rssFeedItems) {

        //Loading progress will be shown until at least one feed is obtained.
        if( rssFeedItems != null && rssFeedItems.size() > 0) {
            mAdapter.setFeedItems(rssFeedItems);
            setListShown(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<CosmosFeedItem>> listLoader) {
        mAdapter.setFeedItems(null);
    }


}
