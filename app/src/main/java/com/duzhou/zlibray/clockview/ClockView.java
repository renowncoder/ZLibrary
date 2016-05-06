package com.duzhou.zlibray.clockview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

/**
 * Created by zhou on 16-5-6.
 */
public class ClockView extends View {

    Paint circlePaint; //表盘的画笔
    Paint numPaint; //表盘上数字的画笔
    Paint dotPaint; //表盘上圆心的画笔
    Paint hourPaint; //时针的画笔
    Paint minutePaint; //分针的画笔
    Paint secondPaint; //秒针的画笔

    int width = 500;
    int height = 500;

    Calendar calendar;

    int hourColor; //时针的颜色
    int minuteColor;
    int secondColor;

    int hourWidth; //时针的宽度
    int minuteWidth;
    int secondWidth;
    int radius = 0 ;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    //控件，属性变量初始化
    private void initView() {
        radius = width / 2 - 10;

        hourColor = Color.RED;
        minuteColor = Color.BLUE;
        secondColor = Color.GREEN;

        hourWidth = 5;
        minuteWidth = 3;
        secondWidth = 2;

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.BLACK);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(6);

        dotPaint = new Paint();
        dotPaint.setAntiAlias(true);
        dotPaint.setColor(Color.BLACK);
        dotPaint.setStyle(Paint.Style.FILL);

        numPaint = new Paint();
        numPaint.setAntiAlias(true);
        numPaint.setColor(Color.GRAY);
        numPaint.setTextSize(20);
        numPaint.setTextAlign(Paint.Align.CENTER);

        hourPaint = new Paint();
        hourPaint.setAntiAlias(true);
        hourPaint.setColor(hourColor);
        hourPaint.setStyle(Paint.Style.FILL);
        hourPaint.setStrokeWidth(hourWidth);

        minutePaint = new Paint();
        minutePaint.setColor(minuteColor);
        minutePaint.setStyle(Paint.Style.FILL);
        minutePaint.setStrokeWidth(minuteWidth);

        secondPaint = new Paint();
        secondPaint.setColor(secondColor);
        secondPaint.setStyle(Paint.Style.FILL);
        secondPaint.setStrokeWidth(secondWidth);

    }

    boolean isNeedCircle = true;

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
            //表盘
            canvas.drawCircle(width / 2, height / 2, radius, circlePaint);
            //表心
            canvas.drawCircle(width / 2, height / 2, 10, dotPaint);

            //下面是通过旋转画布，每次30度，每次都在12点位置画一个粗线和数字；
            // 这里for循环里面的i是从1开始的，是因为表盘上没有数字0,他的位置是12
            for (int i = 1; i < 13; i++) {
                canvas.rotate(30, width / 2, height / 2);
                canvas.drawLine(width / 2, height / 2 - radius, width / 2, height / 2 - radius + 10, circlePaint);
                canvas.drawText(i + "", width / 2, height / 2 - radius + 30, numPaint);
            }


        //获取当前时间的实例
        calendar = Calendar.getInstance();

        //时针
        int hour = calendar.get(Calendar.HOUR);//获得当前小时
        int minute = calendar.get(Calendar.MINUTE);
        canvas.save();
        canvas.rotate(hour * 30 + minute *30 / 60 , width / 2, height / 2);
        canvas.drawLine(width / 2, height / 2 + 20 , width / 2, height / 2 -radius + 80, hourPaint);
        canvas.restore();

        //分针
        canvas.save();
        canvas.rotate(minute * 6, width / 2, height / 2);
        canvas.drawLine(width / 2, height / 2 + 20 , width / 2, height / 2 -radius + 60, minutePaint);
        canvas.restore();

        //秒针
        int second = calendar.get(Calendar.SECOND);
        canvas.save();
        canvas.rotate(second * 6, width / 2, height / 2);
        canvas.drawLine(width / 2, height / 2 + 20 , width / 2, height / 2 -radius + 30, secondPaint);
        canvas.restore();

        //每隔1秒重绘View,重绘会调用onDraw()方法
        postInvalidateDelayed(1000);
    }
}
