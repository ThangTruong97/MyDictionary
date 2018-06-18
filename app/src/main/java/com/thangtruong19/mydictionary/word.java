package com.thangtruong19.mydictionary;

/**
 * Created by User on 16/05/2018.
 */

public class word {
    private String mDefaultWord;
    private String mVietnameseWord;
    private final int NO_IMAGE=-1;
    private int mImageResourceId=NO_IMAGE;
    private int mSoundResourceId;
    public word(String defaultWord,String vietnameseWord,int ImageResourceId,int soundResourceId){
        mDefaultWord=defaultWord;
        mVietnameseWord=vietnameseWord;
        mImageResourceId=ImageResourceId;
        mSoundResourceId=soundResourceId;
    }
    public word(String defaultWord,String vietnameseWord,int soundResourceId){
        mDefaultWord=defaultWord;
        mVietnameseWord=vietnameseWord;
        mSoundResourceId=soundResourceId;
    }
    public String getDefaultWord(){
        return mDefaultWord;
    }
    public String getVietnameseWord(){
        return mVietnameseWord;
    }
    public int getImageResourceId(){
        return mImageResourceId;
    }
    public int getSoundResourceId(){
        return mSoundResourceId;
    }

    public boolean hasImage(){
        return mImageResourceId!=NO_IMAGE;
    }
}
