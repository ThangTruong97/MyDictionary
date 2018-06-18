package com.thangtruong19.mydictionary;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 17/05/2018.
 */

public class wordAdapter extends ArrayAdapter<word>{

    private int mResourceColorId;
    public wordAdapter(Activity context, ArrayList<word> wordList,int resourceColorId){
              super(context,0,wordList);
              mResourceColorId=resourceColorId;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView=convertView;
        word currentWord=getItem(position);
        if(listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        TextView defaultWord=listItemView.findViewById(R.id.default_text_view);
        defaultWord.setText(currentWord.getDefaultWord());
        TextView vietnameseWord=listItemView.findViewById(R.id.vietnamese_text_view);
        vietnameseWord.setText(currentWord.getVietnameseWord());
        ImageView image=listItemView.findViewById(R.id.image_view);
        if(currentWord.hasImage()) {
            image.setImageResource(currentWord.getImageResourceId());
        }else{
            image.setVisibility(View.GONE);
        }
        View textContainer=listItemView.findViewById(R.id.text_container);
        int color= ContextCompat.getColor(getContext(),mResourceColorId);
        textContainer.setBackgroundColor(color);
        return listItemView;
    }
}
