package com.example.coursequest;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

class colorManager {

    private final ArrayList<String> colorValues = new ArrayList<>(Arrays.asList("#F1C40F", "#FF5348", "#2ECC71", "#23B91C", "#AE73FF", "#F760DE", "#FFAE0D", "#43B89E", "#2BADF8", "#2887FF"));
    private final ArrayList<String> colorNames = new ArrayList<>(Arrays.asList("Yellow", "Red", "Green", "Dark Green", "Purple", "Pink", "Orange", "Aquamarine", "Blue", "Dark Blue"));
    private ArrayList<String> colorStates;
    private final SharedPreferences chosenColors;

    colorManager(Context c){
         chosenColors = c.getSharedPreferences("color list", MODE_PRIVATE);
         loadData(c);
    }

    ArrayList<String> getColorNames(){
        return colorNames;
    }

    ArrayList<String> getColorValues(){
        return colorValues;
    }

    ArrayList<String> getColorStates(){
        return colorStates;
    }

    void removeColor(String colorName){
        int i = colorNames.indexOf(colorName);
        colorStates.set(i, "0");
        saveData();
    }

    void addColor(String colorName){
        int i = colorNames.indexOf(colorName);
        colorStates.set(i, "1");
        saveData();
    }

    ArrayList<String> getCurrentSavedColorValues(){
        ArrayList<String> currentColorValues = new ArrayList<>();
        for(int i = 0; i < colorValues.size(); i++){
            if(colorStates.get(i).equals("1"))
                currentColorValues.add(colorValues.get(i));
        }
        return currentColorValues;
    }

    private void saveData(){
        SharedPreferences.Editor editor = chosenColors.edit();
        Gson gson = new Gson();
        String colorJson = gson.toJson(colorStates);
        editor.putString("states", colorJson);
        editor.apply();
    }

    private void loadData(Context c){
        SharedPreferences sharedPreferences = c.getSharedPreferences("color list", MODE_PRIVATE);
        Gson gson = new Gson();
        String colorState = sharedPreferences.getString("states", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        colorStates = gson.fromJson(colorState, type);
        if (colorStates == null)
        {
            colorStates = new ArrayList<>();
            for(int i = 0; i < colorValues.size(); i++){
                colorStates.add("1");
            }
        }
    }
}
