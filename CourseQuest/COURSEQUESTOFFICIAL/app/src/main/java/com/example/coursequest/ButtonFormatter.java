package com.example.coursequest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.Button;

import androidx.constraintlayout.widget.Constraints;

import java.util.Random;
import static android.util.TypedValue.COMPLEX_UNIT_SP;
import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

/* This class has a method which takes a button as a parameter and applies a set of standard button styles
 * to it.
 */
class ButtonFormatter  {

    private static final int textSize = 20;                   //yellow     green      teal        purple     orange   green-blue  red        blue
    private static final String[] buttonColors = new String[]{"#F1C40F", "#2ECC71", "#45B39D",  "#AE73FF", "#FFAE0D", "#43B89E", "#FF5348", "#2BADF8"};
    private static int color;

    static void formatCourseButton(Context c, Button b)
    {
        color = new Random().nextInt(buttonColors.length-1); //random color for card background color
        b.setTransformationMethod(null);
        Drawable card = getDrawable(c, R.drawable.results_card);
        card.setTint(Color.parseColor(buttonColors[color]));
        b.setBackground(card);
        b.setPadding(55, 55, 55, 55);
        b.setTextAppearance(c, R.style.TextAppearance_AppCompat_Display2);
        b.setTextSize(COMPLEX_UNIT_SP, textSize);
        b.setTextColor(Color.parseColor("#F9F9F9"));
        Constraints.LayoutParams params = new Constraints.LayoutParams(
                Constraints.LayoutParams.WRAP_CONTENT,
                Constraints.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 30);
        b.setLayoutParams(params);
    }

    static void formatSearchPageButton(Context c, Button b)
    {
        color = new Random().nextInt(buttonColors.length-1); //random color for card background color
        b.setTransformationMethod(null);
        Drawable card = getDrawable(c, R.drawable.results_card);
        card.setTint(Color.parseColor(buttonColors[color]));

        b.setBackground(card);
        b.setTextAppearance(c, R.style.TextAppearance_AppCompat_Display2);
        b.setTextSize(COMPLEX_UNIT_SP, 19);
        b.setTextColor(Color.parseColor("#F9F9F9"));
        b.setPadding(55, 55, 55, 55);
        Constraints.LayoutParams params = new Constraints.LayoutParams(
                Constraints.LayoutParams.WRAP_CONTENT,
                Constraints.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(20, 0, 0, 15);
        b.setLayoutParams(params);
    }
}
