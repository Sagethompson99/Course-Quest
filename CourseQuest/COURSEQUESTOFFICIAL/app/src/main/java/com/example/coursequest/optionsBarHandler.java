package com.example.coursequest;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

class optionsBarHandler {

    private Button close;
    private Button like;
    private Button dislike;
    private Button share;
    private View[] buttons;
    private View optionsBar;
    private final Context context;
    private final Button currentCourse;
    private final LayoutInflater inflater;
    private final LinearLayout layout;
    private static String currentButtonText = "";
    private static String currentButtonLink = "";
    private static String currentPage;


    optionsBarHandler(LinearLayout givenList, Context givenContext, Button course, String page){
        layout = givenList;
        context = givenContext;
        currentCourse = course;
        currentPage = page;
        inflater = LayoutInflater.from(givenContext);

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
                shareBarHandler shareBar = new shareBarHandler(context, layout, currentCourse.getTag().toString());
            }
        });
    }

    void closeCourseOptionsBar(){
       // Animation a = playAnimation(optionsBar, context, R.layout.animation_slide_left, 0);
        layout.removeView(optionsBar);
    }

    void openCourseOptionsBar(){

        if(layout.indexOfChild(optionsBar) > 0) {
            closeCourseOptionsBar();
        }

        currentButtonText = currentCourse.getText().toString();
        currentButtonLink = currentCourse.getTag().toString();

        layout.addView(optionsBar, layout.indexOfChild(currentCourse)+1);

        buttons = new View[]{close, share, dislike, like};

        int incrementDuration = 0;
        for(View view: buttons) {
            playAnimation(view, context, R.layout.animation_slide_right, incrementDuration);
            incrementDuration += 95;
        }
    }

    private void likeCourse(){
        displayToast("Course Saved:)");
        HomeActivity.addNewSavedCourse(currentButtonText, currentButtonLink);
    }

    private void dislikeCourse(){
        displayToast("Course Unsaved:(");
        HomeActivity.deleteSavedCourse(currentButtonText);
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
