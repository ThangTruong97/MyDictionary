package com.thangtruong19.mydictionary;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {
    private MediaPlayer mMediaPlayer;

    private MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
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

    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.word_list,container,false);

        mAudioManager=(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<word> words = new ArrayList<word>();

        words.add(new word("One", "Một", R.mipmap.number_one,R.raw.number_one));
        words.add(new word("Two", "Hai", R.mipmap.number_two,R.raw.number_two));
        words.add(new word("Three", "Ba", R.mipmap.number_three,R.raw.number_three));
        words.add(new word("Four", "Bốn", R.mipmap.number_four,R.raw.number_four));
        words.add(new word("Five", "Năm", R.mipmap.number_five,R.raw.number_five));
        words.add(new word("Six", "Sáu", R.mipmap.number_six,R.raw.number_six));
        words.add(new word("Seven", "Bảy", R.mipmap.number_seven,R.raw.number_seven));
        words.add(new word("Eight", "Tám", R.mipmap.number_eight,R.raw.number_eight));
        words.add(new word("Nine", "Chín ", R.mipmap.number_nine,R.raw.number_nine));
        words.add(new word("Ten", "Mười", R.mipmap.number_ten,R.raw.number_ten));

        wordAdapter itemsAdapter = new wordAdapter(getActivity(), words, R.color.category_numbers);

        ListView listView = rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                word selectedWord = words.get(i);
                // Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback.

                    mMediaPlayer = MediaPlayer.create(getActivity(), selectedWord.getSoundResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
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
