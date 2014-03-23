package com.bignerdranch.android.hellomoon;

import static com.bignerdranch.android.hellomoon.VideoPlayer.PlayerState.PAUSED;
import static com.bignerdranch.android.hellomoon.VideoPlayer.PlayerState.PLAYING;
import static com.bignerdranch.android.hellomoon.VideoPlayer.PlayerState.STOPPED;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.VideoView;

public class VideoPlayer {

    private final VideoView mVideoView;
    private final Uri mResourceUri;
    
    enum PlayerState {
        STOPPED, PLAYING, PAUSED
    }

    private PlayerState mPlayerState = STOPPED;
    private int pausePosition = 0;

    public VideoPlayer(VideoView videoView, Uri resourceUri) {
        this.mVideoView = videoView;
        this.mResourceUri = resourceUri;
    }

    public void stop() {
        mVideoView.stopPlayback();
        mPlayerState = STOPPED;
    }
    
    public void pause() {
        if (mPlayerState == PLAYING) {
            mVideoView.pause();
            pausePosition = mVideoView.getCurrentPosition();
            mPlayerState = PAUSED;
        }
    }

    public void play() {
        if (mPlayerState == STOPPED) {
            mVideoView.setVideoURI(mResourceUri);
            mVideoView.start();
        } else if (mPlayerState == PAUSED) {
            mVideoView.seekTo(pausePosition);
            mVideoView.start();
        }

        mPlayerState = PLAYING;
    }

    class OnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            stop();
        };

    }
}
