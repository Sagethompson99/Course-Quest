package com.example.coursequest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.Button;

import androidx.constraintlayout.widget.Constraints;

import java.util.ArrayList;
import java.util.Random;
import static android.util.TypedValue.COMPLEX_UNIT_SP;
import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

/* This class has a method which takes a button as a parameter and applies a set of standard button styles
 * to it.
 */
class ButtonFormatter  {

    private static final int textSize = 20;
    private static int colorNum;
    private static final String textColor = "#F9F9F9";

    static void formatCourseButton(Context c, Button b)
    {
        Drawable logo;
        colorManager ColorManager = new colorManager(c);
        ArrayList<String> colors = ColorManager.getCurrentSavedColorValues();
        colorNum = new Random().nextInt(colors.size()); //random color for card background color
        b.setTransformationMethod(null);
        Drawable card = getDrawable(c, R.drawable.results_card);
        if (card != null)
        {
            card.setTint(Color.parseColor(colors.get(colorNum)));
        }
        b.setBackground(card);
        b.setGravity(Gravity.CENTER_HORIZONTAL);
        b.setElevation(70);
        b.setPadding(55, 55, 55, 55);
        b.setTextAppearance(R.style.TextAppearance_AppCompat_Display2);
        b.setTextSize(COMPLEX_UNIT_SP, textSize);
        b.setTextColor(Color.parseColor(textColor));
        Constraints.LayoutParams params = new Constraints.LayoutParams(
                Constraints.LayoutParams.WRAP_CONTENT,
                Constraints.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 30);
        b.setLayoutParams(params);
        b.setCompoundDrawablePadding(70);


        if(b.getTag().toString().contains("futurelearn")){
            logo = c.getDrawable(R.drawable.ic_future_learn);
        }
        else if(b.getTag().toString().contains("codecademy")){
            logo = c.getDrawable(R.drawable.ic_codecademy);
        }
        else if(b.getTag().toString().contains("skillshare")){
            logo = c.getDrawable(R.drawable.ic_skillshare);
        }
        else{
            logo = c.getDrawable(R.drawable.ic_coursera);
            b.setCompoundDrawablePadding(110);
        }

        if (logo != null) {
            logo.setTint(Color.parseColor(textColor));
            b.setCompoundDrawablesWithIntrinsicBounds(null, null, null, logo);
        }
    }

    static void formatSearchPageButton(Context c, Button b)
    {
        colorManager ColorManager = new colorManager(c);
        ArrayList<String> colors = ColorManager.getCurrentSavedColorValues();
        colorNum = new Random().nextInt(colors.size()); //random color for card background color
        b.setTransformationMethod(null);
        Drawable card = getDrawable(c, R.drawable.results_card);
        if (card != null)
        {
            card.setTint(Color.parseColor(colors.get(colorNum)));
        }
        b.setBackground(card);
        b.setTextAppearance(R.style.TextAppearance_AppCompat_Display2);
        b.setTextSize(COMPLEX_UNIT_SP, 19);
        b.setTextColor(Color.parseColor(textColor));
        b.setPadding(55, 55, 55, 55);
        Constraints.LayoutParams params = new Constraints.LayoutParams(
                Constraints.LayoutParams.WRAP_CONTENT,
                Constraints.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(20, 0, 0, 15);
        b.setLayoutParams(params);
    }
}
