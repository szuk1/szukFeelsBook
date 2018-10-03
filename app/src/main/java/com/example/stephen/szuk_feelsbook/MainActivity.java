package com.example.stephen.szuk_feelsbook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file7.sav";
    private String comment;
    EmotionListController elc = new EmotionListController();

    public void toHistory(View view) {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
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

    private void saveInFile() {
        try {

            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(EmotionListController.getEmotionList(), out);

            out.flush();

            fos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void showAlertDialogue(String type){
        //alert dialog generates edittext and prompts user on if they would like to enter a
        //comment or not. Creates new emotion, stores into the arraylist in the emotionlistcontroller
        //and saves the arraylist afterwards.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText CommentEditText = new EditText(this);
        final String EmotionType = type;
        builder.setView(CommentEditText);
        builder.setMessage("Add A Comment?");
        builder.setPositiveButton("Add Comment", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //comment = CommentEditText.getText().toString();
                Emotion emotion = new Emotion(CommentEditText.getText().toString());
                emotion.setType(EmotionType);
                elc.getEmotionList().add(emotion);
                saveInFile();
            }
        });
        builder.setNegativeButton("Add Without Comment", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Emotion emotion = new Emotion();
                emotion.setType(EmotionType);
                elc.getEmotionList().add(emotion);
                saveInFile();
            }
        });
        builder.show();
    }

    public void addLoveEmotion(View view){
        showAlertDialogue("Love");
    }
    public void addJoyEmotion(View view){
        showAlertDialogue("Joy");

    }
    public void addSurpriseEmotion(View view){
        showAlertDialogue("Surprise");
    }
    public void addAngerEmotion(View view){
        showAlertDialogue("Anger");

    }
    public void addSadnessEmotion(View view){
        showAlertDialogue("Sadness");
    }
    public void addFearEmotion(View view){
        showAlertDialogue("Fear");
    }


}
