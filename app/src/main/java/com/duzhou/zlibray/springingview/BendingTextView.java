package com.duzhou.zlibray.springingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhou on 16-4-26.
 */
public class BendingTextView extends View {

    private Context mContext;
    Path mPath;
    Paint textPaint;
    private int PAINT_TEXTSIZE = 50;
    private final int PAINT_TEXT_BASEIINE = PAINT_TEXTSIZE;//BASELINE的高度
    private String mResText = "我的家在东白";//画出来的文字
    private int mTextWidth;//文字的宽度
    private String mDefaultText;

    private int mRecfSpace = 0;//矩形RECF间距
    private final int STATUS_DOWN_CURVE = 0;//向下弯曲的状态
    private final int STATUS_UP_CURVE = 1;//向上恢复的状态
    private final int STATUS_FLAT_CURVE = 2;//平的状态
    private int mCurveStatus = STATUS_FLAT_CURVE;
    private final int DEFAULT_RECF_SPACE = 6;//默认的画弧形的时候的间距,值越大速度越快，不能超过最大值
    private final int MAX_RECF_SPACE = 36;//最大的画弧形的时候的间距
    private final int MIN_RECF_SPACE = -12;//最大的画弧形的时候的间距


    private final int MAX_SPRING_COUNT = 20;//来回弹动的时间
    private int mSringCount = MAX_SPRING_COUNT;//当前弹动的次数

    public BendingTextView(Context context) {
        super(context);
        mContext = context;
        initPaint();
    }

    public BendingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }


    private void initPaint() {
        textPaint = new Paint();
        textPaint.setColor(Color.parseColor("#666666"));
        textPaint.setTextSize(PAINT_TEXTSIZE);
        textPaint.setStyle(Paint.Style.STROKE); //空心的
        textPaint.setTextAlign(Paint.Align.LEFT);
        mTextWidth = (int) textPaint.measureText(mResText);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int baseLineSpace = 20;
        setMeasuredDimension(mTextWidth, PAINT_TEXTSIZE + baseLineSpace);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLinePathAndText(canvas);
    }

    public void startAnim() {
        mSringCount = 0;
        mCurveStatus = STATUS_DOWN_CURVE;
        invalidate();
    }

    private void drawLinePathAndText(Canvas canvas) {
        if (mPath == null) {
            mPath = new Path();
            drawLinePath();
        } else {
            drawArcPath();
            mRecfSpace = getRecfSpace();
            if (mRecfSpace >= MAX_RECF_SPACE) {
                mCurveStatus = STATUS_UP_CURVE;
            } else if (mRecfSpace <= MIN_RECF_SPACE) {
                mCurveStatus = STATUS_DOWN_CURVE;
            }
        }
        if (mSringCount < MAX_SPRING_COUNT) {
            mSringCount++;
            invalidate();
        } else {
            reset(canvas);
        }
        canvas.drawTextOnPath(mResText, mPath, 0, 0, textPaint);
    }

    void reset(Canvas canvas) {
        mRecfSpace = 0;
        drawArcPath();
        mCurveStatus = STATUS_FLAT_CURVE;
    }

    void drawLinePath() {
        mPath.moveTo(0, PAINT_TEXT_BASEIINE); //设定起始点
        mPath.lineTo(mTextWidth, PAINT_TEXT_BASEIINE);//第一条直线的终点，也是第二条直线的起点
        mPath.close();
    }

    void drawArcPath() {
        mPath.reset();
        mPath.moveTo(0, PAINT_TEXT_BASEIINE);
        mPath.quadTo(0, PAINT_TEXT_BASEIINE, 0, PAINT_TEXT_BASEIINE);
        mPath.quadTo(mTextWidth / 2, PAINT_TEXT_BASEIINE + mRecfSpace, mTextWidth - 5, PAINT_TEXT_BASEIINE);
        mPath.quadTo(mTextWidth * 5 / 6, PAINT_TEXT_BASEIINE, mTextWidth, PAINT_TEXT_BASEIINE);
        mPath.close();
    }

    int getRecfSpace() {
        if (mCurveStatus == STATUS_DOWN_CURVE) {
            return mRecfSpace + DEFAULT_RECF_SPACE;
        } else if (mCurveStatus == STATUS_UP_CURVE) {
            return mRecfSpace - DEFAULT_RECF_SPACE;
        } else {
            return 0;
        }
    }
}
