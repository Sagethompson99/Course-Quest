package com.example.coursequest;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

public class colorManager {

    private ArrayList<String> colorValues = new ArrayList<>(Arrays.asList("#F1C40F", "#FF5348", "#2ECC71", "#23B91C", "#AE73FF", "#F760DE", "#FFAE0D", "#43B89E", "#2BADF8", "#2887FF"));
    private ArrayList<String> colorNames = new ArrayList<>(Arrays.asList("Yellow", "Red", "Green", "Dark Green", "Purple", "Pink", "Orange", "Aquamarine", "Blue", "Dark Blue"));
    private ArrayList<String> colorStates;
    private SharedPreferences chosenColors;

    public colorManager(Context c){
         chosenColors = c.getSharedPreferences("color list", MODE_PRIVATE);
         loadData(c);
    }

    public ArrayList<String> getColorNames(){
        return colorNames;
    }

    public ArrayList<String> getColorValues(){
        return colorValues;
    }

    public ArrayList<String> getColorStates(){
        return colorStates;
    }

    public boolean removeColor(String colorName){
        boolean removed;
        int i = colorNames.indexOf(colorName);

        int numStatesChecked = 0;
        for(String state: colorStates){
            if(state.equals("1"))
                numStatesChecked++;
        }

        if(numStatesChecked > 1) { //Ensures there is at least one color selected before removal
            colorStates.set(i, "0");
            removed = true;
        }
        else{
            removed = false;
        }

        saveData();
        return removed;
    }

    public void addColor(String colorName){
        int i = colorNames.indexOf(colorName);
        colorStates.set(i, "1");
        saveData();
    }

    public ArrayList<String> getCurrentSavedColorValues(){
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
