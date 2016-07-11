package com.deenysoft.schoolbox.youtube;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.deenysoft.schoolbox.R;
import com.deenysoft.schoolbox.nest.ui.base.BaseActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by shamsadam on 04/07/16.
 */
@TargetApi(13)
public class YoutubeVideoListActivity extends BaseActivity implements OnFullscreenListener {

    /** The duration of the animation sliding up the video in portrait. */
    private static final int ANIMATION_DURATION_MILLIS = 300;
    /** The padding between the video list and the video in landscape orientation. */
    private static final int LANDSCAPE_VIDEO_PADDING_DP = 5;

    /** The request code when calling startActivityForResult to recover from an API service error. */
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    private VideoListFragment listFragment;
    private VideoFragment videoFragment;

    private View videoBox;
    private View closeButton;

    private boolean isFullscreen;
    private AdView mAdView;

    public YoutubeVideoListActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_video_list_activity);

        listFragment = (VideoListFragment) getFragmentManager().findFragmentById(R.id.list_fragment);
        videoFragment = (VideoFragment) getFragmentManager().findFragmentById(R.id.video_fragment_container);

        videoBox = findViewById(R.id.video_box);
        closeButton = findViewById(R.id.close_button);

        videoBox.setVisibility(View.INVISIBLE);
        layout();
        checkYouTubeApi();

        // Setting Up Banner Ads
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        // load and display ads
        mAdView.loadAd(adRequest);

    }

    private void checkYouTubeApi() {
        YouTubeInitializationResult errorReason =
                YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this);
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else if (errorReason != YouTubeInitializationResult.SUCCESS) {
            String errorMessage =
                    String.format(getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Recreate the activity if user performed a recovery action
            recreate();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        layout();
    }

    @Override
    public void onFullscreen(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        layout();
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

    /**
     * Sets up the layout programatically for the three different states. Portrait, landscape or
     * fullscreen+landscape. This has to be done programmatically because we handle the orientation
     * changes ourselves in order to get fluent fullscreen transitions, so the xml layout resources
     * do not get reloaded.
     */
    private void layout() {
        boolean isPortrait =
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        listFragment.getView().setVisibility(isFullscreen ? View.GONE : View.VISIBLE);
        listFragment.setLabelVisibility(isPortrait);
        closeButton.setVisibility(isPortrait ? View.VISIBLE : View.GONE);

        if (isFullscreen) {
            videoBox.setTranslationY(0); // Reset any translation that was applied in portrait.
            setLayoutSize(videoFragment.getView(), MATCH_PARENT, MATCH_PARENT);
            setLayoutSizeAndGravity(videoBox, MATCH_PARENT, MATCH_PARENT, Gravity.TOP | Gravity.LEFT);
        } else if (isPortrait) {
            setLayoutSize(listFragment.getView(), MATCH_PARENT, MATCH_PARENT);
            setLayoutSize(videoFragment.getView(), MATCH_PARENT, WRAP_CONTENT);
            setLayoutSizeAndGravity(videoBox, MATCH_PARENT, WRAP_CONTENT, Gravity.BOTTOM);
        } else {
            videoBox.setTranslationY(0); // Reset any translation that was applied in portrait.
            int screenWidth = dpToPx(getResources().getConfiguration().screenWidthDp);
            setLayoutSize(listFragment.getView(), screenWidth / 4, MATCH_PARENT);
            int videoWidth = screenWidth - screenWidth / 4 - dpToPx(LANDSCAPE_VIDEO_PADDING_DP);
            setLayoutSize(videoFragment.getView(), videoWidth, WRAP_CONTENT);
            setLayoutSizeAndGravity(videoBox, videoWidth, WRAP_CONTENT,
                    Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        }
    }

    public void onClickClose(@SuppressWarnings("unused") View view) {
        listFragment.getListView().clearChoices();
        listFragment.getListView().requestLayout();
        videoFragment.pause();
        ViewPropertyAnimator animator = videoBox.animate()
                .translationYBy(videoBox.getHeight())
                .setDuration(ANIMATION_DURATION_MILLIS);
        runOnAnimationEnd(animator, new Runnable() {
            @Override
            public void run() {
                videoBox.setVisibility(View.INVISIBLE);
            }
        });
    }

    @TargetApi(16)
    private void runOnAnimationEnd(ViewPropertyAnimator animator, final Runnable runnable) {
        if (Build.VERSION.SDK_INT >= 16) {
            animator.withEndAction(runnable);
        } else {
            animator.setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    runnable.run();
                }
            });
        }
    }

    @Override
    public boolean providesActivityToolbar() {
        return false;
    }

    /**
     * A fragment that shows a static list of videos.
     */
    public static final class VideoListFragment extends ListFragment {

        private static final List<VideoEntry> VIDEO_LIST;

        static {
            List<VideoEntry> list = new ArrayList<VideoEntry>();
            list.add(new VideoEntry("Hydrogen - Element #1", "6rdmpx39PRk"));
            list.add(new VideoEntry("Helium - Element #2", "a8FJEiI5e6Q"));
            list.add(new VideoEntry("Lithium - Element #3", "LfS10ArXTBA"));
            list.add(new VideoEntry("Beryllium - Element #4", "qy8JyQShZRA"));
            list.add(new VideoEntry("Boron - Element #5", "JzqdHkpXuy4"));
            list.add(new VideoEntry("Carbon - Element #6", "QuW4_bRHbUk"));
            list.add(new VideoEntry("Nitrogen - Element #7", "zmvJ54kRpjg"));
            list.add(new VideoEntry("Oxygen - Element #8", "WuG5WTId-IY"));
            list.add(new VideoEntry("Fluorine - Element #9", "vtWp45Eewtw"));
            list.add(new VideoEntry("Neon - Element #10", "wzv0pb7mzaw"));
            list.add(new VideoEntry("Sodium - Element #11", "7IT2I3LtlNE"));
            list.add(new VideoEntry("Magnesium - Element #12", "FKkWdizutxI"));
            list.add(new VideoEntry("Aluminium - Element #13", "4AhZ8503WPs"));
            list.add(new VideoEntry("Silicon - Element #14", "a2aWO5cL410"));
            list.add(new VideoEntry("Phosphorus - Element #15", "LSYLUat03A4"));
            list.add(new VideoEntry("Sulfur - Element #16", "mGMR72X8V-U"));
            list.add(new VideoEntry("Chlorine - Element #17", "BXCfBl4rmh0"));
            list.add(new VideoEntry("Argon - Element #18", "nrHVOFG2V-c"));
            list.add(new VideoEntry("Potassium - Element #19", "pPdevJTGAYY"));
            list.add(new VideoEntry("Calcium - Element #20", "V9fuY8_ffFg"));

            list.add(new VideoEntry("Scandium - Element #21", "KkKv5ilmRjY"));
            list.add(new VideoEntry("Titanium - Element #22", "MpFTQYynrc4"));
            list.add(new VideoEntry("Vanadium - Element #23", "MbCmaQzrZoc"));
            list.add(new VideoEntry("Chromium - Element #24", "lzRb4zmGvNU"));
            list.add(new VideoEntry("Manganese - Element #25", "uTVtBuY9Q-0"));
            list.add(new VideoEntry("Iron - Element #26", "euQUgp5AY-Y"));
            list.add(new VideoEntry("Cobalt - Element #27", "V6ljxByu9ng"));
            list.add(new VideoEntry("Nickel - Element #28", "t4kRHoj0W1Y"));
            list.add(new VideoEntry("Copper - Element #29", "kop1sWzTK-I"));
            list.add(new VideoEntry("Zinc - Element #30", "99wPiMb-k0o"));
            list.add(new VideoEntry("Gallium - Element #31", "N6ccRvKKwZQ"));
            list.add(new VideoEntry("Germanium - Element #32", "osrKWVknkgs"));
            list.add(new VideoEntry("Arsenic - Element #33", "a2AbKwAvyos"));
            list.add(new VideoEntry("Selenium - Element #34", "IHrUtKjcAFE"));
            list.add(new VideoEntry("Bromine - Element #35", "Slt3_5upuSs"));
            list.add(new VideoEntry("Krypton - Element #36", "il4OOY7Zseg"));
            list.add(new VideoEntry("Rubidium - Element #37", "0XLGopBovoI"));
            list.add(new VideoEntry("Strontium - Element #38", "d5ztPGrsgNQ"));
            list.add(new VideoEntry("Yttrium - Element #39", "tTXjnQlAHAQ"));
            list.add(new VideoEntry("Zirconium - Element #40", "gNJE2MPktvg"));

            list.add(new VideoEntry("Niobium - Element #41", "2ciPAsVTq6c"));
            list.add(new VideoEntry("Molybdenum - Element #42", "ZRQ3vBGskds"));
            list.add(new VideoEntry("Technetium - Element #43", "0QBlrGva5YQ"));
            list.add(new VideoEntry("Ruthenium - Element #44", "wl5ZYb0hDTc"));
            list.add(new VideoEntry("Rhodium - Element #45", "PPSO5798k2I"));
            list.add(new VideoEntry("Palladium - Element #46", "4ALTGeqmNFM"));
            list.add(new VideoEntry("Silver - Element #47", "pPd5qAb4J50"));
            list.add(new VideoEntry("Cadmium - Element #48", "boRius1DYdQ"));
            list.add(new VideoEntry("Indium - Element #49", "4opHafNmgCw"));
            list.add(new VideoEntry("Tin - Element #50", "qEwCPJOP0Mg"));
            list.add(new VideoEntry("Antimony - Element #51", "kcc6qNT3BoU"));
            list.add(new VideoEntry("Tellurium - Element #52", "5ChFbVu4Mpk"));
            list.add(new VideoEntry("Iodine - Element #53", "JUBsJLRSM64"));
            list.add(new VideoEntry("Xenon - Element #54", "Ejoct_6pQ74"));
            list.add(new VideoEntry("Cesium - Element #55", "5aD6HwUE2c0"));
            list.add(new VideoEntry("Barium - Element #56", "9srJdQU3NOo"));
            list.add(new VideoEntry("Lanthanum - Element #57", "Q21clW0s0B8"));
            list.add(new VideoEntry("Cerium - Element #58", "frD3126ry8o"));
            list.add(new VideoEntry("Praseodymium - Element #59", "IL06CzXF3ns"));
            list.add(new VideoEntry("Neodymium - Element #60", "PBbl-3_R3mk"));

            list.add(new VideoEntry("Promethium - Element #61", "px52fxITTrw"));
            list.add(new VideoEntry("Samarium - Element #62", "LpTkBg8HpvY"));
            list.add(new VideoEntry("Europium - Element #63", "88YOmg_FUVo"));
            list.add(new VideoEntry("Gadolinium - Element #64", "uUo7pY38fGY"));
            list.add(new VideoEntry("Terbium - Element #65", "On5LjH9TQxY"));
            list.add(new VideoEntry("Dysprosium - Element #66", "hFfR_qOSa-8"));
            list.add(new VideoEntry("Holmium - Element #67", "HQahtzCU0BU"));
            list.add(new VideoEntry("Erbium - Element #68", "E-DY_RT4fJ4"));
            list.add(new VideoEntry("Thulium - Element #69", "CQFDIZfMPVQ"));
            list.add(new VideoEntry("Ytterbium - Element #70", "Rp7-yNvOV4I"));
            list.add(new VideoEntry("Lutetium - Element #71", "a5T1xXoR8XI"));
            list.add(new VideoEntry("Hafnium - Element #72", "egw79BA_0PA"));
            list.add(new VideoEntry("Tantalum - Element #73", "51xFP1Yn3g0"));
            list.add(new VideoEntry("Tungsten - Element #74", "_PccntqMOoI"));
            list.add(new VideoEntry("Rhenium - Element #75", "VRccwkEmOYg"));
            list.add(new VideoEntry("Osmium - Element #76", "AdX-T2Vv68Y"));
            list.add(new VideoEntry("Iridium - Element #77", "cuovE4OQi2g"));
            list.add(new VideoEntry("Platinum - Element #78", "byzaoji_9kk"));
            list.add(new VideoEntry("Gold - Element #79", "7dF0QTzcuac"));
            list.add(new VideoEntry("Mercury - Element #80", "oL0M_6bfzkU"));

            list.add(new VideoEntry("Thallium - Element #81", "bK6nnyibhdk"));
            list.add(new VideoEntry("Lead - Element #82", "2ERfPN5JLX8"));
            list.add(new VideoEntry("Bismuth - Element #83", "vyIo-c7VmIM"));
            list.add(new VideoEntry("Polonium - Element #84", "bbr5yWwsI1o"));
            list.add(new VideoEntry("Astatine - Element #85", "sSL7EwdlLlE"));
            list.add(new VideoEntry("Radon - Element #86", "mTuC_LrEfbU"));
            list.add(new VideoEntry("Francium - Element #87", "PyFLvSg6ZDw"));
            list.add(new VideoEntry("Radium - Element #88", "5_I6vj-lXNM"));
            list.add(new VideoEntry("Actinium - Element #89", "0BlpjWBYW0k"));
            list.add(new VideoEntry("Thorium - Element #90", "2yZGcr0mpw0"));
            list.add(new VideoEntry("Protactinium - Element #91", "bsIMMa7iEKU"));
            list.add(new VideoEntry("Uranium - Element #92", "B8vVZTvJNGk"));
            list.add(new VideoEntry("Neptunium - Element #93", "1D75B0_URbE"));
            list.add(new VideoEntry("Plutonium - Element #94", "89UNPdNtOoE"));
            list.add(new VideoEntry("Americium - Element #95", "CC-L-CITg3k"));
            list.add(new VideoEntry("Curium - Element #96", "sZobqPFNcwg"));
            list.add(new VideoEntry("Berkelium - Element #97", "imXT9QwRzks"));
            list.add(new VideoEntry("Californium - Element #98", "eG6HyPrTccI"));
            list.add(new VideoEntry("Einsteinium - Element #99", "-G_4nJLNXA0"));
            list.add(new VideoEntry("Fermium - Element #100", "SQhI52sqanA"));

            list.add(new VideoEntry("Mendelevium - Element #101", "0JlshAo8DuE"));
            list.add(new VideoEntry("Nobelium - Element #102", "t_ZpauMxapY"));
            list.add(new VideoEntry("Lawrencium - Element #103", "_zBsnnJOkyA"));
            list.add(new VideoEntry("Rutherfordium - Element #104", "dOj9ZjKnJcY"));
            list.add(new VideoEntry("Dubnium - Element #105", "eeczRrDoL_M"));
            list.add(new VideoEntry("Seaborgium - Element #106", "UWq0djr790E"));
            list.add(new VideoEntry("Bohrium - Element #107", "okJnQIjELY4"));
            list.add(new VideoEntry("Hassium - Element #108", "u4GEVxbLego"));
            list.add(new VideoEntry("Meitnerium - Element #109", "N8VR7Qscq4k"));
            list.add(new VideoEntry("Darmstadtium - Element #110", "lhvMqva3-7M"));
            list.add(new VideoEntry("Roentgenium - Element #111", "MTq1hzhCF0g"));
            list.add(new VideoEntry("Copernicium - Element #112", "QHcbQfcwegY"));
            list.add(new VideoEntry("Ununtrium - Element #113", "Ac7iFepG2CY"));
            list.add(new VideoEntry("Flerovium - Element #114", "5L-NNFPiRog"));
            list.add(new VideoEntry("Ununpentium - Element #115", "gCt5WK2OVp0"));
            list.add(new VideoEntry("Livermorium - Element #116", "WdbF4L_ruyM"));
            list.add(new VideoEntry("Ununseptium - Element #117", "BKEEi8JThps"));
            list.add(new VideoEntry("Ununoctium - Element #118", "Stxor9L00BU"));

            VIDEO_LIST = Collections.unmodifiableList(list);
        }

        private PageAdapter adapter;
        private View videoBox;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            adapter = new PageAdapter(getActivity(), VIDEO_LIST);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            videoBox = getActivity().findViewById(R.id.video_box);
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            setListAdapter(adapter);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            String videoId = VIDEO_LIST.get(position).videoId;

            VideoFragment videoFragment =
                    (VideoFragment) getFragmentManager().findFragmentById(R.id.video_fragment_container);
            videoFragment.setVideoId(videoId);

            // The videoBox is INVISIBLE if no video was previously selected, so we need to show it now.
            if (videoBox.getVisibility() != View.VISIBLE) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    // Initially translate off the screen so that it can be animated in from below.
                    videoBox.setTranslationY(videoBox.getHeight());
                }
                videoBox.setVisibility(View.VISIBLE);
            }

            // If the fragment is off the screen, we animate it in.
            if (videoBox.getTranslationY() > 0) {
                videoBox.animate().translationY(0).setDuration(ANIMATION_DURATION_MILLIS);
            }
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();

            adapter.releaseLoaders();
        }

        public void setLabelVisibility(boolean visible) {
            adapter.setLabelVisibility(visible);
        }

    }

    /**
     * Adapter for the video list. Manages a set of YouTubeThumbnailViews, including initializing each
     * of them only once and keeping track of the loader of each one. When the ListFragment gets
     * destroyed it releases all the loaders.
     */
    private static final class PageAdapter extends BaseAdapter {

        private final List<VideoEntry> entries;
        private final List<View> entryViews;
        private final Map<YouTubeThumbnailView, YouTubeThumbnailLoader> thumbnailViewToLoaderMap;
        private final LayoutInflater inflater;
        private final ThumbnailListener thumbnailListener;

        private boolean labelsVisible;

        public PageAdapter(Context context, List<VideoEntry> entries) {
            this.entries = entries;

            entryViews = new ArrayList<View>();
            thumbnailViewToLoaderMap = new HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>();
            inflater = LayoutInflater.from(context);
            thumbnailListener = new ThumbnailListener();

            labelsVisible = true;
        }

        public void releaseLoaders() {
            for (YouTubeThumbnailLoader loader : thumbnailViewToLoaderMap.values()) {
                loader.release();
            }
        }

        public void setLabelVisibility(boolean visible) {
            labelsVisible = visible;
            for (View view : entryViews) {
                view.findViewById(R.id.text).setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        }

        @Override
        public int getCount() {
            return entries.size();
        }

        @Override
        public VideoEntry getItem(int position) {
            return entries.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            VideoEntry entry = entries.get(position);

            // There are three cases here
            if (view == null) {
                // 1) The view has not yet been created - we need to initialize the YouTubeThumbnailView.
                view = inflater.inflate(R.layout.youtube_video_list_item, parent, false);
                YouTubeThumbnailView thumbnail = (YouTubeThumbnailView) view.findViewById(R.id.thumbnail);
                thumbnail.setTag(entry.videoId);
                thumbnail.initialize(DeveloperKey.DEVELOPER_KEY, thumbnailListener);
            } else {
                YouTubeThumbnailView thumbnail = (YouTubeThumbnailView) view.findViewById(R.id.thumbnail);
                YouTubeThumbnailLoader loader = thumbnailViewToLoaderMap.get(thumbnail);
                if (loader == null) {
                    // 2) The view is already created, and is currently being initialized. We store the
                    //    current videoId in the tag.
                    thumbnail.setTag(entry.videoId);
                } else {
                    // 3) The view is already created and already initialized. Simply set the right videoId
                    //    on the loader.
                    thumbnail.setImageResource(R.drawable.loading_thumbnail);
                    loader.setVideo(entry.videoId);
                }
            }
            TextView label = ((TextView) view.findViewById(R.id.text));
            label.setText(entry.text);
            label.setVisibility(labelsVisible ? View.VISIBLE : View.GONE);
            return view;
        }

        private final class ThumbnailListener implements
                YouTubeThumbnailView.OnInitializedListener,
                YouTubeThumbnailLoader.OnThumbnailLoadedListener {

            @Override
            public void onInitializationSuccess(
                    YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
                loader.setOnThumbnailLoadedListener(this);
                thumbnailViewToLoaderMap.put(view, loader);
                view.setImageResource(R.drawable.loading_thumbnail);
                String videoId = (String) view.getTag();
                loader.setVideo(videoId);
            }

            @Override
            public void onInitializationFailure(
                    YouTubeThumbnailView view, YouTubeInitializationResult loader) {
                view.setImageResource(R.drawable.no_thumbnail);
            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView view, String videoId) {
            }

            @Override
            public void onThumbnailError(YouTubeThumbnailView view, YouTubeThumbnailLoader.ErrorReason errorReason) {
                view.setImageResource(R.drawable.no_thumbnail);
            }
        }

    }

    public static final class VideoFragment extends YouTubePlayerFragment
            implements YouTubePlayer.OnInitializedListener {

        private YouTubePlayer player;
        private String videoId;

        public static VideoFragment newInstance() {
            return new VideoFragment();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            initialize(DeveloperKey.DEVELOPER_KEY, this);
        }

        @Override
        public void onDestroy() {
            if (player != null) {
                player.release();
            }
            super.onDestroy();
        }

        public void setVideoId(String videoId) {
            if (videoId != null && !videoId.equals(this.videoId)) {
                this.videoId = videoId;
                if (player != null) {
                    player.cueVideo(videoId);
                }
            }
        }

        public void pause() {
            if (player != null) {
                player.pause();
            }
        }

        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean restored) {
            this.player = player;
            //player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
            player.setOnFullscreenListener((YoutubeVideoListActivity) getActivity());
            if (!restored && videoId != null) {
                player.cueVideo(videoId);
            }
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
            this.player = null;
        }

    }

    private static final class VideoEntry {
        private final String text;
        private final String videoId;

        public VideoEntry(String text, String videoId) {
            this.text = text;
            this.videoId = videoId;
        }
    }

    // Utility methods for layouting.

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }

    private static void setLayoutSize(View view, int width, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    private static void setLayoutSizeAndGravity(View view, int width, int height, int gravity) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        params.gravity = gravity;
        view.setLayoutParams(params);
    }


}
