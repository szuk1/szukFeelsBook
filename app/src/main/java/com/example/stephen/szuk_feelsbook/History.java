package com.example.stephen.szuk_feelsbook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

public class History extends AppCompatActivity {

    private static final String FILENAME = "file7.sav";
    EmotionListController elc = new EmotionListController();
    EmotionListAdapter adapter;
    ListView EmotionHistoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        EmotionHistoryList = (ListView) findViewById(R.id.EmotionHistoryList);

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        adapter = new EmotionListAdapter(this,
                R.layout.emotion_listview_layout, EmotionListController.getEmotionList());
        EmotionHistoryList.setAdapter(adapter);
        EmotionHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Emotion selectedEmotion = (Emotion) parent.getItemAtPosition(position);
                showEditDialog(selectedEmotion);

            }
        });
        EmotionHistoryList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Emotion selectedEmotion = (Emotion) parent.getItemAtPosition(position);
                showDeleteDialog(selectedEmotion);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.history_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this,EmotionCount.class);
        this.startActivity(intent);
        return true;
    }

    public void toMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void loadFromFile() {
        //loads from specified file and stores into an arraylist of emotions located in the Emotion
        //list controller using gson library
        //This method and saveFromFile borrowed from lonelyTwitter lab assignment.
        //See license file for licensing info
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson(); // Library to save objects
            Type listType = new TypeToken<ArrayList<Emotion>>(){}.getType();
            ArrayList<Emotion> tempArray;
            tempArray = gson.fromJson(in, listType);
            elc.setEmotionList(tempArray);
            Collections.sort(EmotionListController.getEmotionList());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void saveInFile() {
        try {
            //Saves the current emotionlist stored in the emotion list controller into the
            //specified file using Gson library
            //This method and loadFromFile borrowed from lonelyTwitter lab assignment.
            //See license file for licensing info

            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(elc.getEmotionList(), out);
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

    public void showEditCommentDialog(Emotion emotion){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText CommentEditText = new EditText(this);
        CommentEditText.setText(emotion.getComment());
        final Emotion emotionfinal = emotion;
        builder.setView(CommentEditText);
        builder.setMessage("Edit Comment?");
        builder.setPositiveButton("Change Comment", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                emotionfinal.setComment(CommentEditText.getText().toString());
                saveInFile();
            }
            });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


    public void showEditDateDialog(Emotion emotion){
        //alert dialog used to create new alert dialog, provide an edittext with the
        //current date listed, and save any changes made to the date.
        //uses ISO time formatting, any editing made should stay in this format
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText DateEditText = new EditText(this);
        DateEditText.setText(emotion.getDate().toString());
        final Emotion emotionfinal = emotion;
        builder.setView(DateEditText);
        builder.setMessage("Edit Date?");
        builder.setPositiveButton("Change Date", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newdateString = DateEditText.getText().toString();
                LocalDateTime newdate = LocalDateTime.parse(newdateString, ISO_LOCAL_DATE_TIME);
                emotionfinal.setDate(newdate);
                Collections.sort(EmotionListController.getEmotionList());
                saveInFile();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


    public void showEditDialog(Emotion emotion){
        //Alert dialog activated on press of list element, used to prompt user on whether
        //they would like to edit comment or date, and calls the corresponding alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Emotion emotionfinal = emotion;

        builder.setMessage("What to Edit?");
        builder.setPositiveButton("Edit Comment", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showEditCommentDialog(emotionfinal);
            }
        });
        builder.setNegativeButton("Edit Date", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showEditDateDialog(emotionfinal);

            }
        });
        builder.show();
    }

    public void showDeleteDialog(Emotion emotion) {
        //alert dialog used on long press of list element to prompt user on whether they would
        //like to delete this element, and does so when user accepts
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Emotion emotionfinal = emotion;

        builder.setMessage("Delete This Emotion");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EmotionListController.getEmotionList().remove(emotionfinal);
                saveInFile();
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


}

