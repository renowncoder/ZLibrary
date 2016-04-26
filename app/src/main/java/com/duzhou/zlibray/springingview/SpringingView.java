package com.duzhou.zlibray.springingview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.duzhou.zlibray.R;

/**
 * Created by zhou on 16-4-25.
 */
public class SpringingView extends FrameLayout {
    private int currentResDrawable = 0;
    private final int[] mResDrawable = {R.mipmap.p1, R.mipmap.p3, R.mipmap.p5, R.mipmap.p7};
    RotateAnimation rotateAnimation;
    TranslateAnimation translateAnimation, backAnimation; //
    AnimationSet animationSet2, animationSet;
    private ImageView mImageView;
    private final int MAX_HEIGHT = 100;
    private final long DURATION = 1000;

    public SpringingView(Context mContext) {
        super(mContext);
        init(mContext);
    }

    public SpringingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SpringingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    View view1;
    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.cus_springing_view, this);
        mImageView = (ImageView) view.findViewById(R.id.img);
        mImageView.setImageResource(mResDrawable[currentResDrawable]);

        view1 = view.findViewById(R.id.v);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.
                WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,MAX_HEIGHT,0,0);
        view1.setLayoutParams(layoutParams);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
        loadAnim();
    }

    private void loadAnim() {
        translateAnimation = new TranslateAnimation(0, 0, 0, MAX_HEIGHT);
        translateAnimation.setDuration(DURATION);
        translateAnimation.setFillAfter(true);

        backAnimation = new TranslateAnimation(0, 0, MAX_HEIGHT, 0);
        backAnimation.setDuration(DURATION);
        backAnimation.setFillAfter(true);

        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setDuration(DURATION);

        animationSet = new AnimationSet(true);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(rotateAnimation);

        animationSet2 = new AnimationSet(true);
        animationSet2.addAnimation(backAnimation);
        animationSet2.addAnimation(rotateAnimation);

        translateAnimation.setAnimationListener(animationListener);

        animationSet.start();

    }

    Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            changeImg();
            if (animation == translateAnimation) {
                mImageView.clearAnimation();
                mImageView.setAnimation(animationSet2);
                animationSet2.start();
            } else {
                mImageView.clearAnimation();
                mImageView.setAnimation(animationSet);
                animationSet.start();
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private void changeImg() {
        if (currentResDrawable >= (mResDrawable.length - 1)) {
            currentResDrawable = 0 ;
        }else {
            currentResDrawable ++ ;
        }
        mImageView.setImageResource(mResDrawable[currentResDrawable]);
    }

}
