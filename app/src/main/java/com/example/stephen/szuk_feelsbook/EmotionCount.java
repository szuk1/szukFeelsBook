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
        int LoveCount = 0;
        int JoyCount = 0;
        int SurpriseCount = 0;
        int AngerCount = 0;
        int SadnessCount = 0;
        int FearCount = 0;
        int TotalCount = 0;

        for (Emotion emotion: CountArray){
            if (emotion.getType().equals("Love")) {
                LoveCount +=1;
                TotalCount +=1;
            }
            else if (emotion.getType().equals("Joy")) {
                JoyCount +=1;
                TotalCount +=1;
            }
            else if (emotion.getType().equals("Surprise")) {
                SurpriseCount +=1;
                TotalCount +=1;
            }
            else if (emotion.getType().equals("Anger")) {
                AngerCount +=1;
                TotalCount +=1;
            }
            else if (emotion.getType().equals("Sadness")) {
                SadnessCount +=1;
                TotalCount +=1;
            }
            else {
                FearCount +=1;
                TotalCount +=1;
            }
            LoveCountTV.setText(String.valueOf(LoveCount));
            JoyCountTV.setText(String.valueOf(JoyCount));
            SurpriseCountTV.setText(String.valueOf(SurpriseCount));
            AngerCountTV.setText(String.valueOf(AngerCount));
            SadnessCountTV.setText(String.valueOf(SadnessCount));
            FearCountTV.setText(String.valueOf(FearCount));
            TotalCountTV.setText(String.valueOf(TotalCount));
        }
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
