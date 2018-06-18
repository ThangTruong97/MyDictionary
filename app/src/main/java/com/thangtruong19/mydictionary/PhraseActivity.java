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

public class PhraseActivity extends AppCompatActivity {
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
        final ArrayList<word> words = new ArrayList<word>();

        words.add(new word("Where are you going?", "Bạn đang đi đâu vậy?", R.raw.phrase_where_are_you_going));
        words.add(new word("What is your name?", "Tên bạn là gì?", R.raw.phrase_what_is_your_name));
        words.add(new word("My name is...", "Tên tôi là...", R.raw.phrase_my_name_is));
        words.add(new word("How are you feeling?", "Bạn cảm thấy như thế nào?", R.raw.phrase_how_are_you_feeling));
        words.add(new word("I’m feeling good.", "Mình ổn.", R.raw.phrase_i_am_feeling_good));
        words.add(new word("Are you coming?", "Bạn đang đến phải không?", R.raw.phrase_are_you_coming));
        words.add(new word("Yes, I’m coming.", "Phải, mình đang đến", R.raw.phrase_i_am_coming));
        words.add(new word("Let’s go.", "Đi thôi.", R.raw.phrase_let_go));
        words.add(new word("Come here.", "Lại đây", R.raw.phrase_come_here));

        wordAdapter itemsAdapter = new wordAdapter(this, words, R.color.category_phrases);

        ListView listView = findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                word selectedWord = words.get(i);
                mMediaPlayer = MediaPlayer.create(PhraseActivity.this, selectedWord.getSoundResourceId());
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

