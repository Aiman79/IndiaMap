package com.rkbuildcon.playerdigitallibrary.view.fragments;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.rkbuildcon.playerdigitallibrary.audio.ClsEntryClass;
import com.rkbuildcon.playerdigitallibrary.R;
import com.rkbuildcon.playerdigitallibrary.utils.Utils;

public class AudioPlayerFragment extends Fragment {
    private static final String TAG_DATA = "data";
    private AppCompatButton btnPlay;
    private AppCompatTextView tvCurrentTime, tvTotalTime;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private ClsEntryClass entryClass;
    private Activity activity;
    private Context context;
    private boolean isPlaying = false;
    private int totalTime, currentTime, remainingTime;
    private Handler mHandler;
    private Runnable mRunnable;

    //audio focus
    AudioAttributes playbackAttributes;

    //listeners
    private  AudioManager audioManager;
    private int audioFocusRequest;


    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = focusChange -> {
        if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
            playAudio();
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
            pauseAudio();
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
            stopAudio();
        }
    };

    public static AudioPlayerFragment newInstance(ClsEntryClass clsEntryClass) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG_DATA , clsEntryClass);
        AudioPlayerFragment audioPlayerFragment = new AudioPlayerFragment();
        audioPlayerFragment.setArguments(bundle);
        return audioPlayerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        activity = getActivity();
        context = getContext();
        if (getArguments() != null){
            entryClass = (ClsEntryClass) getArguments().getSerializable(TAG_DATA);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_audio_player, container, false);
        registerViews(view);
        addListeners();
        initMediaPlayer();
        return view;
    }

    public void registerViews(View view){
        btnPlay = view.findViewById(R.id.play);
        seekBar = view.findViewById(R.id.seekBar);
        tvCurrentTime = view.findViewById(R.id.tvCurrentTime);
        tvTotalTime = view.findViewById(R.id.tvTotalTime);
        audioManager = (AudioManager) requireContext().getSystemService(Context.AUDIO_SERVICE);
        setUpAudioFocus();
    }

    private void setUpAudioFocus(){
        playbackAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        // set the playback attributes for the focus requester
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            AudioFocusRequest focusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setAudioAttributes(playbackAttributes)
                    .setAcceptsDelayedFocusGain(true)
                    .setOnAudioFocusChangeListener(audioFocusChangeListener)
                    .build();

            audioFocusRequest = audioManager.requestAudioFocus(focusRequest);
        }
    }

    private void addListeners() {
        btnPlay.setOnClickListener(view -> {
            if (isPlaying){
                pauseAudio();
            } else {
                playAudio();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int x = (int) Math.ceil(progress / 1000f);

                if (x == 0 && mediaPlayer != null && !mediaPlayer.isPlaying()) {
//                    clearMediaPlayer();
//                    fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, android.R.drawable.ic_media_play));
                    AudioPlayerFragment.this.seekBar.setProgress(0);
                    isPlaying = false;
                    changePlayImageDrawables();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                    isPlaying = true;
                    changePlayImageDrawables();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearMediaPlayer();
    }


    private void clearMediaPlayer() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        if (mHandler != null && mRunnable != null){
            mHandler.removeCallbacks(mRunnable);
        }
    }

    private void initMediaPlayer(){
        if (entryClass.getFile() != null){
            mediaPlayer = MediaPlayer.create(context, Uri.fromFile(entryClass.getFile()));
            totalTime = mediaPlayer.getDuration();
            currentTime = 0;
            remainingTime = totalTime;
            seekBar.setMax(totalTime / 1000);
            tvTotalTime.setText(Utils.getTimeInFormat(remainingTime));
            tvCurrentTime.setText(Utils.getTimeInFormat(totalTime - currentTime));

           mHandler = new Handler();
           mRunnable = new Runnable() {

               @Override
               public void run() {
                   if(mediaPlayer != null){
                       currentTime = mediaPlayer.getCurrentPosition();
                       seekBar.setProgress(currentTime / 1000);
                       if (entryClass.getMediaPlayerListener() != null){
                           entryClass.getMediaPlayerListener().onSeekBarProgressUpdate(currentTime / 1000);
                       }
                       tvCurrentTime.setText(Utils.getTimeInFormat(currentTime));
                       tvTotalTime.setText(Utils.getTimeInFormat(totalTime - currentTime));
                   }
                   mHandler.postDelayed(this, 1000);
               }
           };

            requireActivity().runOnUiThread(mRunnable);
        }
    }

    private void playAudio(){
        if (audioFocusRequest == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            if (mediaPlayer != null) {
                try {
                    mediaPlayer.start();
                    isPlaying = true;
                    changePlayImageDrawables();
                    if (entryClass.getMediaPlayerListener() != null){
                        entryClass.getMediaPlayerListener().onPlay();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void pauseAudio(){
        if (mediaPlayer != null) {
            try {
                mediaPlayer.pause();
                isPlaying = false;
                changePlayImageDrawables();
                if (entryClass.getMediaPlayerListener() != null){
                    entryClass.getMediaPlayerListener().onPause();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void stopAudio(){
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                if (entryClass.getMediaPlayerListener() != null){
                    entryClass.getMediaPlayerListener().onStop();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void changePlayImageDrawables(){
        if (isPlaying){
            btnPlay.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_vector_pause));
        } else {
            btnPlay.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_vector_play));
        }
    }
}