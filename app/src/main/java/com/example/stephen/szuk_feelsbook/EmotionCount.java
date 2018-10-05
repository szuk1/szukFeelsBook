package com.example.stephen.szuk_feelsbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class EmotionCount extends AppCompatActivity {
    private static final String FILENAME = "file7.sav";
    EmotionListController elc = new EmotionListController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_count);

    }

    @Override
    protected void onStart(){
        super.onStart();
        loadFromFile();
        ArrayList<Emotion> CountArray = EmotionListController.getEmotionList();
        TextView LoveCountTV = (TextView)findViewById(R.id.LoveCount);
        TextView JoyCountTV = (TextView)findViewById(R.id.JoyCount);
        TextView SurpriseCountTV = (TextView)findViewById(R.id.SurpriseCount);
        TextView AngerCountTV = (TextView)findViewById(R.id.AngerCount);
        TextView SadnessCountTV = (TextView)findViewById(R.id.SadnessCount);
        TextView FearCountTV = (TextView)findViewById(R.id.FearCount);
        TextView TotalCountTV = (TextView)findViewById(R.id.TotalEmotionCount);
        HashMap<String,Integer> map = new HashMap<String, Integer>();
        int totalcount = 0;



        for (Emotion emotion: CountArray) {
            String emotiontype = emotion.getType();
            map.put(emotiontype, 1 + map.getOrDefault(emotiontype,0));
            totalcount += 1;
        }

            LoveCountTV.setText(String.valueOf(map.getOrDefault("Love", 0)));
            JoyCountTV.setText(String.valueOf(map.getOrDefault("Joy", 0)));
            SurpriseCountTV.setText(String.valueOf(map.getOrDefault("Surprise", 0)));
            AngerCountTV.setText(String.valueOf(map.getOrDefault("Anger", 0)));
            SadnessCountTV.setText(String.valueOf(map.getOrDefault("Sadness", 0)));
            FearCountTV.setText(String.valueOf(map.getOrDefault("Fear", 0)));
            TotalCountTV.setText(String.valueOf(totalcount));

    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson(); // Library to save objects
            Type listType = new TypeToken<ArrayList<Emotion>>(){}.getType();
            ArrayList<Emotion> tempArray;
            tempArray = gson.fromJson(in, listType);
            elc.setEmotionList(tempArray);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
