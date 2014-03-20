package com.bignerdranch.android.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;
import static com.bignerdranch.android.hellomoon.AudioPlayer.PlayerState.*;

public class AudioPlayer {
    enum PlayerState {
        STOPPED, PLAYING, PAUSED
    }

    private MediaPlayer mPlayer;
    private PlayerState mPlayerState = STOPPED;

    public void stop() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }

        mPlayerState = STOPPED;
    }

    public void pause() {
        if (mPlayerState == PLAYING) {
            mPlayer.pause();
            mPlayerState = PAUSED;
        }
    }

    public void play(Context context) {

        if (mPlayerState == STOPPED) {
            mPlayer = MediaPlayer.create(context, R.raw.one_small_step);
            mPlayer.setOnCompletionListener(new OnCompletionListener());
        }

        mPlayer.start();
        mPlayerState = PLAYING;
    }

    class OnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            stop();
        };

    }
}
