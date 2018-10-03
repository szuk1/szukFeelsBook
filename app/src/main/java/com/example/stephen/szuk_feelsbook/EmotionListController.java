package com.example.stephen.szuk_feelsbook;

import java.util.ArrayList;

public class EmotionListController {
    private static ArrayList<Emotion> EmotionList = null;

    static public ArrayList<Emotion> getEmotionList() {
        if (EmotionList == null) {
            EmotionList = new ArrayList<Emotion>();

        }
        return EmotionList;
    }

    static public void setEmotionList(ArrayList<Emotion> newEmotionList) {
        EmotionList = newEmotionList;
    }
}


