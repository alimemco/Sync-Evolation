package com.ali.memco.evolaptop.view.activity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.support.graphics.drawable.PathInterpolatorCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.PathInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ali.memco.evolaptop.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class AnimationShow extends AppCompatActivity {

    private static final String TAG = "AnimationShow";

    private SwitchCompat switchCompat;
    private ImageView kouroshImage;
    private Button buttonStart;
    private FrameLayout frame;

    public static final String KEY_EXTRA_ANIM = "KeyAnim";
    public static int AnimType = 0;
    public static final int TYPE_ALPHA = 0;
    public static final int TYPE_TRANSLATE = 1;
    public static final int TYPE_SCALE = 2;
    public static final int TYPE_ROTATE = 3;
    public static final int TYPE_VALUE_ANIM = 4;
    public static final int TYPE_SET = 5;
    public static final int TYPE_YOYO = 6;

    private boolean mustLoadFromXML = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_show);

        initViews();

        AnimType = getIntent().getIntExtra(KEY_EXTRA_ANIM, TYPE_ALPHA);

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mustLoadFromXML = isChecked;
            }
        });

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnim();
            }
        });



    }

    private void showAnim() {

        switch (AnimType) {
            case TYPE_ALPHA:
                showAlpha();
                break;

            case TYPE_TRANSLATE:
                showTranslate();
                break;

            case TYPE_SCALE:
                ShowScale();
                break;


            case TYPE_ROTATE:
                showRotate();

                break;


            case TYPE_VALUE_ANIM:
                showValueAnim();
                break;

            case TYPE_SET:
                showSet();
                break;

            case TYPE_YOYO:
                YoYoAnim();
                break;


        }

    }

    private void YoYoAnim() {
        if(mustLoadFromXML){
            YoYo.with(Techniques.Wave)
                    .duration(1000)
                    .playOn(kouroshImage);
        }else {
            YoYo.with(Techniques.Shake)
                    .duration(1000)
                    .playOn(kouroshImage);
        }

    }

    private void showSet() {
        if (mustLoadFromXML){
            AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(this,R.anim.anim_set);
            animationSet.setDuration(2000);
            animationSet.setRepeatMode(Animation.REVERSE);
            animationSet.setRepeatCount(Animation.INFINITE);
            animationSet.setFillAfter(true);

            animationSet.setInterpolator( new OvershootInterpolator());
            kouroshImage.startAnimation(animationSet);
        }else {
            AnimationSet animationSet = new AnimationSet(true);
            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.ABSOLUTE,0,Animation.ABSOLUTE,0,Animation.ABSOLUTE,0,Animation.RELATIVE_TO_SELF,0.5f
            );
            translateAnimation.setDuration(1000);
            translateAnimation.setFillAfter(true);
            translateAnimation.setInterpolator(new AnticipateOvershootInterpolator());


            RotateAnimation rotateAnimation = new RotateAnimation(
                    0,45,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f
            );
            rotateAnimation.setDuration(1000);
            rotateAnimation.setFillAfter(true);
            rotateAnimation.setInterpolator(new BounceInterpolator());

            animationSet.addAnimation(rotateAnimation);
            animationSet.addAnimation(translateAnimation);
            animationSet.setFillAfter(true);
            kouroshImage.startAnimation(animationSet);
        }
    }

    private void showValueAnim() {
        final ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(),
                ContextCompat.getColor(this,R.color.colorPrimary),
                ContextCompat.getColor(this,R.color.colorPrimaryDark)
                );
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                frame.setBackgroundColor((int)valueAnimator.getAnimatedValue());
            }
        });

        valueAnimator.setDuration(3000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.start();
    }

    private void showRotate() {
        if(mustLoadFromXML){
            RotateAnimation rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(this,R.anim.anim_rotate);
            rotateAnimation.setDuration(1000);
            rotateAnimation.setRepeatMode(Animation.REVERSE);
            rotateAnimation.setRepeatCount(Animation.INFINITE);
            rotateAnimation.setInterpolator(new FastOutSlowInInterpolator());
            kouroshImage.startAnimation(rotateAnimation) ;

        }else {
            RotateAnimation rotateAnimation = new RotateAnimation(
                    0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f
            );
            rotateAnimation.setDuration(1000);
            rotateAnimation.setFillAfter(true);
            rotateAnimation.setInterpolator(new OvershootInterpolator());
            kouroshImage.startAnimation(rotateAnimation);
        }
    }

    private void ShowScale() {
        if(mustLoadFromXML){
            ScaleAnimation scaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(this,R.anim.anim_scale);
            scaleAnimation.setDuration(1000);
            scaleAnimation.setRepeatCount(Animation.INFINITE);
            scaleAnimation.setRepeatMode(Animation.REVERSE);
            scaleAnimation.setInterpolator(new LinearInterpolator());
            kouroshImage.startAnimation(scaleAnimation);

        }else {
            ScaleAnimation scaleAnimation = new ScaleAnimation(
                   1.0f,2.f,1.0f,2.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f
            );
            scaleAnimation.setDuration(1000);
            scaleAnimation.setFillAfter(true);
            scaleAnimation.setInterpolator(new AnticipateOvershootInterpolator());
            kouroshImage.startAnimation(scaleAnimation);
        }
    }

    private void showTranslate() {
        if (mustLoadFromXML){
            TranslateAnimation translateAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(this,R.anim.anim_translate);
            translateAnimation.setRepeatCount(Animation.INFINITE);
            translateAnimation.setRepeatMode(Animation.REVERSE);
            translateAnimation.setDuration(1000);
            translateAnimation.setInterpolator(new AnticipateOvershootInterpolator());
            kouroshImage.startAnimation(translateAnimation);

        }else {
            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.ABSOLUTE,0,Animation.ABSOLUTE,0,Animation.ABSOLUTE,0,Animation.RELATIVE_TO_SELF,1.0f
            );
            translateAnimation.setDuration(1000);
            translateAnimation.setFillAfter(true);
            translateAnimation.setInterpolator(new BounceInterpolator());
            kouroshImage.startAnimation(translateAnimation);
        }
    }

    private void showAlpha() {
        if (mustLoadFromXML) {
            AlphaAnimation alphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
            alphaAnimation.setDuration(1000);
            alphaAnimation.setRepeatCount(Animation.INFINITE);
            alphaAnimation.setRepeatMode(Animation.REVERSE);
            alphaAnimation.setInterpolator(new DecelerateInterpolator());
            kouroshImage.startAnimation(alphaAnimation);

        } else {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.5f);
            alphaAnimation.setDuration(1000);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            kouroshImage.startAnimation(alphaAnimation);
        }


    }

    private void initViews() {
        switchCompat = findViewById(R.id.anim_show_switch);
        kouroshImage = findViewById(R.id.anim_show_kourosh);
        buttonStart = findViewById(R.id.anim_show_btn_start);
        frame = findViewById(R.id.anim_show_frame);

    }
}
