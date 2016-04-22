package com.duzhou.zlibray.circleImage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.Toast;

import com.duzhou.zlibray.R;

/**
 * Created by zhou on 16-4-22.
 */
public class RoundImageView extends ImageView {
    private String TAG = "RoundImageView";

    /**
     * 图片的类型，圆形or圆角
     */
    private int type;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;
    /**
     * 圆角大小的默认值
     */
    private static final int BODER_RADIUS_DEFAULT = 10;

    /**
     * 圆角的大小
     */
    private int mBorderRadius;


    /**
     * 绘图的Paint
     */
    private Paint mBitmapPaint;
    /**
     * 圆角的半径
     */
    private int mRadius;
    /**
     * 3x3 矩阵，主要用于缩小放大
     */
    private Matrix mMatrix;
    /**
     * 渲染图像，使用图像为绘制图形着色
     */
    private BitmapShader mBitmapShader;
    /**
     * view的宽度
     */
    private int mWidth;
    private RectF mRoundRect;

    private static final int COLORDRAWABLE_DIMENSION = 2;

    public RoundImageView(Context context) {
        super(context,null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMatrix = new Matrix();
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        mBorderRadius = t.getDimensionPixelSize(R.styleable.RoundImageView_borderRadius, BODER_RADIUS_DEFAULT);
        type = t.getInt(R.styleable.RoundImageView_imgtype, TYPE_CIRCLE);
        t.recycle();
    }

    /**
     * 计算 sub view add to the present view
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (type == TYPE_CIRCLE) {
            mWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());
            mRadius = mWidth / 2;
            setMeasuredDimension(mWidth, mWidth);
        }
    }

    private void setUpShader() {
        Drawable drawable = getDrawable();
        if (drawable == null) return;
        Bitmap bitmap = drawableToBitamp(drawable);
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        if (TYPE_CIRCLE == type) {
            int bSize = Math.min(bitmap.getHeight(), bitmap.getWidth());
            scale = mWidth * 1.0f / bSize;
        } else if (TYPE_ROUND == type) {
            scale = Math.max(getWidth() * 1.0f / bitmap.getWidth()
                    , getHeight() * 1.0f / bitmap.getHeight());
        }
        mMatrix.setScale(scale, scale);
        mBitmapShader.setLocalMatrix(mMatrix);
        mBitmapPaint.setShader(mBitmapShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);  麻痹的，这个一定要注释掉，因为他在执行的时候，会先画一个图片在上面 后续在上面会圆形的部分，就显现不出来了。
        if (getDrawable() == null) {
            return;
        }
        setUpShader();

        Toast.makeText(getContext(), "mRadius:" + mRadius, Toast.LENGTH_SHORT).show();

        if (TYPE_ROUND == type) {
            canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius, mBitmapPaint);
        } else if (TYPE_CIRCLE == type) {
            canvas.drawCircle(mRadius, mRadius, mRadius, mBitmapPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (TYPE_ROUND == type) {
            mRoundRect = new RectF(0, 0, w, h);
        }
    }

    private static final String STATE_INSTANCE = "state_instance";
    private static final String STATE_TYPE = "state_type";
    private static final String STATE_BORDER_RADIUS = "state_border_radius";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
        bundle.putInt(STATE_TYPE, type);
        bundle.putInt(STATE_BORDER_RADIUS, mBorderRadius);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable(STATE_INSTANCE));
            this.type = bundle.getInt(STATE_TYPE);
            this.mBorderRadius = bundle.getInt(STATE_BORDER_RADIUS);
        } else {
            super.onRestoreInstanceState(state);
        }
        super.onRestoreInstanceState(state);

    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

}
