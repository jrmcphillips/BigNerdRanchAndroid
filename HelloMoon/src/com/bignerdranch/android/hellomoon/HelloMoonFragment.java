package com.bignerdranch.android.hellomoon;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;
import android.media.MediaPlayer;

public class HelloMoonFragment extends Fragment {

    private final Uri resourceUri = Uri.parse(
            "android.resource://" 
            + this.getClass().getPackage().getName() + "/"
            + R.raw.fruit_bat);

    private VideoPlayer mPlayer= new VideoPlayer(resourceUri);
    private VideoView mVideoView;

    private Button mPlayButton;
    private Button mPauseButton;
    private Button mStopButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hello_moon, container, false);

        mPlayButton = (Button) view.findViewById(R.id.play_button);
        mPauseButton = (Button) view.findViewById(R.id.pause_button);
        mStopButton = (Button) view.findViewById(R.id.stop_button);
        mVideoView = (VideoView) view.findViewById(R.id.video_view);

        mPlayButton.setOnClickListener(new AudioPlayOnClickListener());
        mPauseButton.setOnClickListener(new AudioPauseOnClickListener());
        mStopButton.setOnClickListener(new AudioStopOnClickListener());

        mVideoView = (VideoView) view.findViewById(R.id.video_view);
        mVideoView.setOnCompletionListener(new OnCompletionListener());
        

        if (mPlayer.isPaused()) {
            mPlayer.play(mVideoView);
        }

        return view;
    }

    class AudioPlayOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mPlayer.play(mVideoView);
        }
    }

    class AudioPauseOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mPlayer.pause(mVideoView);
        }
    }

    class AudioStopOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mPlayer.stop(mVideoView);
        }
    }

    class OnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            mPlayer.stop(mVideoView);
        };

    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        if (mPlayer.isPlaying()) {
            mPlayer.pause(mVideoView);
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.stop(mVideoView);
    }

}
