package com.thangtruong19.mydictionary;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT){
                //pause playback
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }else if(focusChange==AudioManager.AUDIOFOCUS_GAIN){
                //resume playback
                mMediaPlayer.start();
            }else if(focusChange==AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<word> words=new ArrayList<word>();

        words.add(new word("Red","Đỏ",R.mipmap.color_red,R.raw.color_red));
        words.add(new word("Green","Lục",R.mipmap.color_green,R.raw.color_green));
        words.add(new word("Brown","Nâu",R.mipmap.color_brown,R.raw.color_brown));
        words.add(new word("Gray","Xám",R.mipmap.color_gray,R.raw.color_gray));
        words.add(new word("Black","Đen",R.mipmap.color_black,R.raw.color_black));
        words.add(new word("White","Trắng",R.mipmap.color_white,R.raw.color_white));
        words.add(new word("Dusty yellow","Vàng nhạt",R.mipmap.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new word("Mustard yellow","Vàng đậm",R.mipmap.color_mustard_yellow,R.raw.color_mustard_yellow));

        wordAdapter itemsAdapter = new wordAdapter(this,words,R.color.category_colors);

        ListView listView =  findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                word selectedWord=words.get(i);
                releaseMediaPlayer();
                mMediaPlayer= MediaPlayer.create(ColorsActivity.this,selectedWord.getSoundResourceId());
                mMediaPlayer.start();
                mMediaPlayer.setOnCompletionListener(mCompletionListener);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
        }
    }
}
