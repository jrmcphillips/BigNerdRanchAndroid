package com.bignerdranch.android.hellomoon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HelloMoonFragment extends Fragment {
    
    private AudioPlayer mAudioPlayer = new AudioPlayer();
    private Button mPlayButton;
    private Button mPauseButton;
    private Button mStopButton;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hello_moon, container, false);

        mPlayButton = (Button) view.findViewById(R.id.play_button);
        mPauseButton = (Button) view.findViewById(R.id.pause_button);
        mStopButton = (Button) view.findViewById(R.id.stop_button);

        mPlayButton.setOnClickListener(new AudioPlayOnClickListener());
        mPauseButton.setOnClickListener(new AudioPauseOnClickListener());
        mStopButton.setOnClickListener(new AudioStopOnClickListener());
        
        return view;
    }
    
    class AudioPlayOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mAudioPlayer.play(getActivity());
        }
    }
    
    class AudioPauseOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mAudioPlayer.pause();
        }
    }
    
    class AudioStopOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mAudioPlayer.stop();
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        mAudioPlayer.stop();
    }
    
}
