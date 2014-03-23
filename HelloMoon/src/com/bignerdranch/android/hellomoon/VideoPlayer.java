package com.bignerdranch.android.hellomoon;

import static com.bignerdranch.android.hellomoon.VideoPlayer.PlayerState.PAUSED;
import static com.bignerdranch.android.hellomoon.VideoPlayer.PlayerState.PLAYING;
import static com.bignerdranch.android.hellomoon.VideoPlayer.PlayerState.STOPPED;
import android.net.Uri;
import android.widget.VideoView;

public class VideoPlayer {

    private final Uri mResourceUri;
    
    enum PlayerState {
        STOPPED, PLAYING, PAUSED
    }

    private PlayerState mPlayerState = STOPPED;
    private int pausePosition = 0;

    public VideoPlayer(Uri resourceUri) {
        this.mResourceUri = resourceUri;
    }

    public void stop(VideoView mVideoView) {
        mVideoView.stopPlayback();
        mPlayerState = STOPPED;
    }
    
    public void pause(VideoView mVideoView) {
        if (mPlayerState == PLAYING) {
            mVideoView.pause();
            pausePosition = mVideoView.getCurrentPosition();
            mPlayerState = PAUSED;
        }
    }

    public void play(VideoView mVideoView) {
        if (mPlayerState == STOPPED) {
            mVideoView.setVideoURI(mResourceUri);
            mVideoView.start();
        } else if (mPlayerState == PAUSED) {
            mVideoView.setVideoURI(mResourceUri);
            mVideoView.seekTo(pausePosition);
            mVideoView.start();
        }

        mPlayerState = PLAYING;
    }

    public boolean isPaused() {
        return mPlayerState == PAUSED;
    }

    public boolean isPlaying() {
        return mPlayerState == PLAYING;
    }
}
