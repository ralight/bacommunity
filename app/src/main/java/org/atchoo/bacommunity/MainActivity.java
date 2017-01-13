package org.atchoo.bacommunity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.media.MediaPlayer;
import android.media.AudioManager;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.atchoo.bacommunity.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    private static MediaPlayer mp = null;
    private static Random mRand;
    private List<Integer> mClipList;
    private int mClipPos;
    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mBaconView;
    private boolean mVisible;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;
    private int mLastVolume;

    private final int clips[][] = {
            {R.raw.community1, R.drawable.community1},
            {R.raw.community1, R.drawable.community1},
            {R.raw.community1, R.drawable.community1},
            {R.raw.community1, R.drawable.community1},
            {R.raw.community1, R.drawable.community1},
            {R.raw.art_of_community, R.drawable.art_of_community},
            {R.raw.community_bad_voltage, R.drawable.community_bad_voltage},
            {R.raw.community_community, R.drawable.community_community},
            {R.raw.community_leadership_summit, R.drawable.community_leadership_summit},
            {R.raw.community_manager, R.drawable.community_manager},
            {R.raw.erm, R.drawable.erm},
            {R.raw.growing_community, R.drawable.growing_community},
            {R.raw.laugh, R.drawable.laugh},
            {R.raw.my_name, R.drawable.my_name},
    };

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mBaconView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    };


    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };


    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    private void setupAudio() {
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        mLastVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);

        mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
                        am.setStreamVolume(AudioManager.STREAM_MUSIC, mLastVolume, 0);
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        mLastVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                        break;
                }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mClipPos = 0;
        mClipList = new ArrayList<Integer>();
        for(int i=0; i<clips.length; i++){
            mClipList.add(i);
        }
        Collections.shuffle(mClipList);

        setupAudio();

        setContentView(R.layout.activity_main);

        mVisible = true;

        mBaconView = findViewById(R.id.bacon);
        mBaconView.setBackgroundResource(R.drawable.jb1);
        View bgView = findViewById(R.id.frame_bg);

        // Set up the user interaction to manually show or hide the system UI.

        mBaconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play_alert();
            }
        });
        bgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle_controls();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        mBaconView.setOnTouchListener(mDelayHideTouchListener);
        bgView.setOnTouchListener(mDelayHideTouchListener);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(500);
    }


    private void play_alert() {
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        int result = am.requestAudioFocus(mOnAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
            return;
        }

        if(mp != null){
            return;
        }


        int i = mClipList.get(mClipPos);

        mp = MediaPlayer.create(this, clips[i][0]);
        mBaconView.setBackgroundResource(clips[i][1]);

        mClipPos++;
        if(mClipPos == clips.length){
            mClipPos = 0;
            Collections.shuffle(mClipList);
        }

        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer lmp) {
                mBaconView.setBackgroundResource(R.drawable.jb1);
                mp.release();
                mp = null;
            }
        });

        AnimationDrawable baconAnim;
        baconAnim = (AnimationDrawable) mBaconView.getBackground();
        baconAnim.start();

        mp.start();
    }


    private void toggle_controls() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }


    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }


    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mBaconView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }


    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }


    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onStop() {
        super.onStop();

        if(mp != null) {
            mp.release();
            mp = null;
        }
    }


    @Override
    public void onPause() {
        super.onPause();

        if(mp != null) {
            mp.release();
            mp = null;
        }
        mBaconView.setBackgroundResource(R.drawable.jb1);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mp != null){
            mp.release();
            mp = null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Don't remove system UI if menu open.
        mHideHandler.removeCallbacks(mHideRunnable);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option, menu);
        return true;
    }


    public void OnClickQuit(MenuItem m) {
        finish();
    }


    public void onClickAbout(MenuItem m) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}
