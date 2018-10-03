package com.example.stephen.szuk_feelsbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;


public class EmotionListAdapter extends ArrayAdapter<Emotion> {
    //custom adapter  built to handle custom xml layouts for data
    //credit to CodingWithMitch for the guide on how to make this adapter
    //Channel: https://www.youtube.com/channel/UCoNZZLhPuuRteu02rh7bzsw
    //video: https://www.youtube.com/watch?v=E6vE8fqQPTE
    private Context adapterContext;
    private int adapterResource;
    public EmotionListAdapter(Context context, int resource, ArrayList<Emotion> emotions){
        super(context, resource, emotions);
        adapterContext = context;
        adapterResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup Parent){
        String emotionType = getItem(position).getType();
        String Comment = getItem(position).getComment();
        String date = getItem(position).getDate().toString();

        LayoutInflater inflater = LayoutInflater.from(adapterContext);
        convertView = inflater.inflate(adapterResource, Parent, false);

        TextView EmotionTypeTextView = (TextView) convertView.findViewById(R.id.EmotionType);
        TextView CommentTextView = (TextView) convertView.findViewById(R.id.EmotionComment);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.EmotionDate);

        EmotionTypeTextView.setText(emotionType);
        CommentTextView.setText(Comment);
        dateTextView.setText(date);

        return convertView;
    }
}
