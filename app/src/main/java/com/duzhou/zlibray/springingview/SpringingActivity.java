package com.duzhou.zlibray.springingview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.duzhou.zlibray.BaseActivity;
import com.duzhou.zlibray.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhou on 16-4-25.
 */
public class SpringingActivity extends BaseActivity {
    @Bind(R.id.image)
    ImageView imageView;
    @Bind(R.id.bendingTextView)
    BendingTextView bendingTextView;

    private int currentResDrawable = 0;
    private final int[] mResDrawable = {R.mipmap.p1, R.mipmap.p3, R.mipmap.p5, R.mipmap.p7};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_springing);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAnim();
    }
    TranslateAnimation translateAnimation = null;
    TranslateAnimation backAnimation = null;
    AnimationSet animatorSetBack = null;
    AnimationSet animatorSet = null;
    long DURATION = 600;
    private void loadAnim() {
        translateAnimation = new TranslateAnimation(0, 0, 0, 90);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(DURATION);
        translateAnimation.setRepeatCount(0);
        translateAnimation.setAnimationListener(animationListener);

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f) ;
        rotateAnimation.setDuration(DURATION);
        rotateAnimation.setRepeatCount(0);
        animatorSet = new AnimationSet(true);
        animatorSet.addAnimation(rotateAnimation);
        animatorSet.addAnimation(translateAnimation);
        animatorSet.setInterpolator(this, android.R.anim.accelerate_interpolator); //加速
        imageView.setAnimation(animatorSet);

        backAnimation = new TranslateAnimation(0, 0, 100, 0);
        backAnimation.setFillAfter(true);
        backAnimation.setDuration(DURATION);
        backAnimation.setRepeatCount(0);
        backAnimation.setAnimationListener(animationListener);

        animatorSetBack = new AnimationSet(true);
        animatorSetBack.addAnimation(rotateAnimation);
        animatorSetBack.addAnimation(backAnimation);
        animatorSetBack.setInterpolator(this, android.R.anim.decelerate_interpolator); //减速

        animatorSet.start();
        bendingTextView.startAnim();
    }

    Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == backAnimation){
                imageView.startAnimation(animatorSet);
            }else {
                changeImg();
                bendingTextView.startAnim();
                imageView.startAnimation(animatorSetBack);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    } ;



    private void changeImg() {
        if (currentResDrawable >= (mResDrawable.length - 1)) {
            currentResDrawable = 0 ;
        }else {
            currentResDrawable ++ ;
        }
        imageView.setImageResource(mResDrawable[currentResDrawable]);
    }

}
