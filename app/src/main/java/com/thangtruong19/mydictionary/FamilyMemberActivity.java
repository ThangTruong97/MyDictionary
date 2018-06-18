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

public class FamilyMemberActivity extends AppCompatActivity {
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

        words.add(new word("Father","Cha",R.mipmap.family_father,R.raw.family_father));
        words.add(new word("Mother","Mẹ",R.mipmap.family_mother,R.raw.family_mother));
        words.add(new word("Son","Con trai",R.mipmap.family_son,R.raw.family_son));
        words.add(new word("Daughter","Con gái",R.mipmap.family_daughter,R.raw.family_daughter));
        words.add(new word("Older brother","Anh trai",R.mipmap.family_older_brother,R.raw.family_older_brother));
        words.add(new word("Younger brother","Em trai",R.mipmap.family_younger_brother,R.raw.family_younger_brother));
        words.add(new word("Older sister","Chị gái",R.mipmap.family_older_sister,R.raw.family_older_sister));
        words.add(new word("Younger sister","Em gái",R.mipmap.family_younger_sister,R.raw.family_younger_sister));
        words.add(new word("Grandmother","Bà",R.mipmap.family_grandmother,R.raw.family_grandmother));
        words.add(new word("Grandfather","Ông",R.mipmap.family_grandfather,R.raw.family_grandfather));

        wordAdapter itemsAdapter = new wordAdapter(this,words,R.color.category_family);

        ListView listView =  findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                word selectedWord=words.get(i);
                mMediaPlayer= MediaPlayer.create(FamilyMemberActivity.this,selectedWord.getSoundResourceId());
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
