package com.demo.demoplayer.functionOne;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.demo.demoplayer.R;
import com.demo.demoplayer.utils.DemoConstants;
import com.google.gson.JsonObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Brandon Lee Roets on 2016/06/21.
 */
public class FunctionOneTrackFragment extends DialogFragment {

    private MediaPlayer trackPlayer;
    private boolean trackPlaying = false;
    private Handler trackHandler = new Handler();;
    private float trackLeftVolume = 1.0f;
    private float trackRightVolume = 1.0f;
    private String trackTitle;
    private String track;
    private String trackDescription;
    private double startTime = 0;
    private double finalTime = 0;

    @BindView(R.id.fragment_function_one_track_progress)
    SeekBar sbTrackProgress;

    @BindView(R.id.fragment_function_one_track_title)
    TextView lblTrackTitle;

    @BindView(R.id.fragment_function_one_track_desc)
    TextView lblTrackDesc;

    @OnClick(R.id.fragment_function_one_track_play)
    public void playTrack() {

        AssetManager assetManager = getActivity().getAssets();
        try {
            AssetFileDescriptor afd = assetManager.openFd(track + ".mp3");

            if (!trackPlaying) {

                trackPlayer = new MediaPlayer();
                trackPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                trackPlayer.setVolume(trackLeftVolume, trackRightVolume);
                trackPlayer.setLooping(false);
                trackPlayer.prepare();
                trackPlayer.start();
                trackPlaying = true;
                finalTime = trackPlayer.getDuration();
                startTime = trackPlayer.getCurrentPosition();
                sbTrackProgress.setProgress((int) startTime);
                sbTrackProgress.setMax((int) finalTime);
                trackHandler.postDelayed(updateTrackTime, 100);
            } else {

                stopPlayer();
                playTrack();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopPlayer() {

        if (trackPlayer != null) {

            trackPlaying = false;
            trackPlayer.stop();
            trackPlayer.release();
            trackHandler.removeCallbacksAndMessages(updateTrackTime);
        }
    }

    @OnClick(R.id.fragment_function_one_track_close)
    public void closeDialog() {

        stopPlayer();
        dismissAllowingStateLoss();
    }

    private Runnable updateTrackTime = new Runnable() {
        public void run() {

            if (trackPlaying) {

                startTime = trackPlayer.getCurrentPosition();
                sbTrackProgress.setProgress((int) startTime);
                trackHandler.postDelayed(this, 100);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_function_one_track, container, false);
        ButterKnife.bind(this, view);

        lblTrackTitle.setText(trackTitle);
        lblTrackDesc.setText(trackDescription);
        sbTrackProgress.setClickable(false);
        this.getDialog().setCancelable(false);

        return view;
    }

    @Override
    public void onCancel(DialogInterface dialog) {

        stopPlayer();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        trackTitle = args.getString(DemoConstants.TRACK_TITLE);
        trackDescription = args.getString(DemoConstants.TRACK_DESCRIPTION);
        track = args.getString(DemoConstants.TRACK);
    }

    public static FunctionOneTrackFragment newInstance(JsonObject track) {

        FunctionOneTrackFragment featureOnePlayerFragment = new FunctionOneTrackFragment();
        Bundle args = new Bundle();
        args.putString(DemoConstants.TRACK_TITLE, track.get(DemoConstants.TRACK_TITLE).getAsString());
        args.putString(DemoConstants.TRACK_DESCRIPTION, track.get(DemoConstants.TRACK_DESCRIPTION).getAsString());
        args.putString(DemoConstants.TRACK, track.get(DemoConstants.TRACK).getAsString());
        featureOnePlayerFragment.setArguments(args);

        return featureOnePlayerFragment;
    }
}