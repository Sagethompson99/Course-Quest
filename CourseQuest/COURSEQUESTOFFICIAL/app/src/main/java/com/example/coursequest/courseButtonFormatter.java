package com.example.coursequest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.Button;

import androidx.constraintlayout.widget.Constraints;
import com.example.coursequest.R;
import java.util.Random;
import static android.util.TypedValue.COMPLEX_UNIT_SP;
import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

/* This class has a method which takes a button and applies the standard course button styles
 * to it.
 */
public class courseButtonFormatter  {

    public static void format(Context c, Button b)
    {
                                             //orange    green      purple     pink       red        blue
        String[] buttonColors = new String[]{"#FFC300", "#64EA66", "#AE73FF", "#E864AE", "#FF5959", "#2BADF8"};
        int color = new Random().nextInt(6); //randomizer for card background color
        Drawable card = getDrawable(c, R.drawable.results_card);
        card.setTint(Color.parseColor(buttonColors[color]));
        b.setBackground(card);
        b.setTextSize(COMPLEX_UNIT_SP, 18);
        b.setTextColor(Color.parseColor("#F9F9F9"));
        b.setPadding(35, 35, 35, 35);
        Constraints.LayoutParams params = new Constraints.LayoutParams(
                Constraints.LayoutParams.WRAP_CONTENT,
                Constraints.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 20);
        b.setLayoutParams(params);
    }
}
