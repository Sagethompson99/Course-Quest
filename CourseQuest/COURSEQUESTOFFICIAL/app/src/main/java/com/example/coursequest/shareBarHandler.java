package com.example.coursequest;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class shareBarHandler {

    private final String courseLink;
    private final float[] touchCoordinates = new float[2];
    private View shareBar;
    private ImageView blurEffect;
    private final Context context;
    private final LayoutInflater inflater;
    private final ConstraintLayout mainLayout;

    shareBarHandler(Context c, LinearLayout layout, String buttonLink){
        mainLayout = (ConstraintLayout) layout.getParent().getParent();
        context = c;
        inflater = LayoutInflater.from(context);
        courseLink = buttonLink;
        prepareShareBar();
        openShareBar();
    }

    private void prepareShareBar(){
        Button cancelShare;
        Button shareSMS;
        Button shareEmail;
        Button shareTwitter;
        Button copyLink;
        Button shareOther;
        shareBar = inflater.inflate(R.layout.course_share_bar, mainLayout, false);

        cancelShare = shareBar.findViewById(R.id.cancelButton);
        cancelShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeShareBar();
            }
        });

        shareSMS = shareBar.findViewById(R.id.messageShare);
        shareSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                sendIntent.putExtra("sms_body", "Check out this course I found on Course Quest! - " + courseLink);
                context.startActivity(sendIntent);
            }
        });

        shareEmail = shareBar.findViewById(R.id.emailShare);
        shareEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "I found a course you might like!");
                emailIntent.putExtra(Intent.EXTRA_TEXT, courseLink);
                context.startActivity(emailIntent);
            }
        });

        shareTwitter = shareBar.findViewById(R.id.twitterShare);
        shareTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent twitterIntent;
                try {
                    // get the Twitter app if possible
                    context.getPackageManager().getPackageInfo("com.twitter.android", 0);
                    twitterIntent = new Intent(Intent.ACTION_SEND);
                    twitterIntent.setType("text/plain");
                    twitterIntent.setClassName("com.twitter.android", "com.twitter.composer.SelfThreadComposerActivity");
                    twitterIntent.putExtra(Intent.EXTRA_TEXT, courseLink);
                    context.startActivity(twitterIntent);
                } catch (Exception e) {
                    //displays toast if twitter is not installed
                    Toast.makeText(context, "Twitter not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        copyLink = shareBar.findViewById(R.id.copyLinkShare);
        copyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Course Link", courseLink);
                if (clipboard != null)
                {
                    clipboard.setPrimaryClip(clip);
                }
            }
        });

        shareOther = shareBar.findViewById(R.id.otherShare);
        shareOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent otherIntent = new Intent(Intent.ACTION_SEND);
                otherIntent.setType("text/plain");
                otherIntent.putExtra(Intent.EXTRA_TEXT, courseLink);

                Intent shareIntent = Intent.createChooser(otherIntent, null);
                context.startActivity(shareIntent);
            }
        });

        blurEffect = mainLayout.findViewById(R.id.blurEffect);

    }

    private void openShareBar(){
        blurEffect.setVisibility(View.VISIBLE);

        ConstraintSet constraints = new ConstraintSet();
        mainLayout.addView(shareBar);
        constraints.clone(mainLayout);
        constraints.connect(shareBar.getId(), ConstraintSet.BOTTOM, R.id.Navigation, ConstraintSet.BOTTOM);
        constraints.applyTo(mainLayout);
        playAnimation(shareBar, context, R.layout.animation_slide_up);

        blurEffect.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                touchCoordinates[0] = motionEvent.getX();
                touchCoordinates[1] = motionEvent.getY();

                if(touchCoordinates[1] > shareBar.getHeight())
                    closeShareBar();

                return true;
            }
        });
    }

    private void playAnimation(View v, Context context, int animationId)
    {
        if(v != null)
        {
            Animation animation = AnimationUtils.loadAnimation(context, animationId);
            v.startAnimation(animation);
            animation.setDuration(animation.getDuration());

        }
    }

    private void closeShareBar(){
        playAnimation(shareBar, context, R.layout.animation_slide_down);
        mainLayout.removeView(shareBar);
        blurEffect.setVisibility(View.GONE);
    }
}
