package com.example.coursequest;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class optionsBarHandler  {

    private Button close;
    private Button like;
    private Button dislike;
    private Button share;
    private View optionsBar;
    private View shareBar;
    private Context context;
    private Button currentCourse;
    private Button cancelShare;
    private LayoutInflater inflater;
    private LinearLayout layout;
    private boolean shareBarOpen = false;
    public static String longPressedButtonText = "";
    public static String longPressedButtonLink = "";
    public int optionsBarIndex = -1;
    public static String currentPage;


    public optionsBarHandler(LinearLayout l, Context c, Button course, String page){
        layout = l;
        context = c;
        currentCourse = course;
        currentPage = page;
        inflater = LayoutInflater.from(c);

        prepareOptionsBar();
    }

    private void prepareOptionsBar(){
        optionsBar = inflater.inflate(R.layout.course_options_bar, null);

        like = optionsBar.findViewById(R.id.likeButton);
        if(currentPage.equals("Home"))
            like.setVisibility(View.GONE);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeCourse();
            }
        });
        dislike = optionsBar.findViewById(R.id.dislikeButton);
        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dislikeCourse();
            }
        });
        close = optionsBar.findViewById(R.id.cancelButton);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeCourseOptionsBar();
            }
        });
        share = optionsBar.findViewById(R.id.shareButton);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shareBarOpen==false) {
                    openShareOptionsBar();
                }
            }
        });
    }

    public void closeCourseOptionsBar(){
        layout.removeViewAt(layout.indexOfChild(currentCourse)+1);
        optionsBarIndex = -1;
    }

    public void openCourseOptionsBar(){

        if(optionsBarIndex != -1) {
            layout.removeViewAt(layout.indexOfChild(currentCourse)+1);
        }

        longPressedButtonText = currentCourse.getText().toString();
        longPressedButtonLink = currentCourse.getTag().toString();

        View[] buttons = new View[]{close, share, dislike, like};

        layout.addView(optionsBar, layout.indexOfChild(currentCourse)+1);

        int incrementDuration = 0;
        for(View view: buttons) {
            playAnimation(view, context, R.layout.animation_slide_right, incrementDuration);
            incrementDuration += 95;
        }
    }

    private void openShareOptionsBar(){
        ConstraintLayout main = (ConstraintLayout) layout.getParent().getParent();
        shareBar = inflater.inflate(R.layout.course_share_bar, main, false);
        cancelShare = shareBar.findViewById(R.id.cancelButton);
        cancelShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeShareOptionsBar();
            }
        });

        ConstraintSet constraints = new ConstraintSet();
        main.addView(shareBar);
        constraints.clone(main);
        constraints.connect(shareBar.getId(), ConstraintSet.BOTTOM, R.id.Navigation, ConstraintSet.BOTTOM);
        constraints.applyTo(main);

        shareBarOpen = true;
    }

    private void closeShareOptionsBar(){
        ConstraintLayout main = (ConstraintLayout) layout.getParent().getParent();
        main.removeView(shareBar);

        shareBarOpen = false;
    }

    private void likeCourse(){
        displayToast("Course Saved:)");
        HomeActivity.addNewSavedCourse(longPressedButtonText, longPressedButtonLink);
    }

    private void dislikeCourse(){
        displayToast("Course Unsaved:(");
        HomeActivity.deleteSavedCourse(longPressedButtonText);
    }

    private Animation playAnimation(View v, Context context, int animationId, int durationDelay)
    {
        if(v != null)
        {
            Animation animation = AnimationUtils.loadAnimation(context, animationId);
            v.startAnimation(animation);
            animation.setDuration(animation.getDuration()+durationDelay);

            return animation;
        }
        return null;
    }

    private void displayToast(String toastText){

        View layout = inflater.inflate(R.layout.course_toast, null);

        TextView text = layout.findViewById(R.id.text);
        text.setText(toastText);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

}
