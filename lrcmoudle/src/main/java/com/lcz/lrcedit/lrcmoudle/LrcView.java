package com.lcz.lrcedit.lrcmoudle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.io.InputStream;
import java.util.List;


/**
 * 这个控件是基于 https://github.com/wangchenyan/LrcView
 * 原先的开源控件其实就播放功能而言已经够了
 * 但是考虑到编辑歌词这个功能，所以有一些地方还需要我自己改一下
 */

public class LrcView extends View {
    private Paint mPaint;
    private Paint centerPaint;
    private Paint linePaint;

    private int lineNum;
    private int centerLine;
    //存储歌词变量
    private String lrcString;

    public List<String> getLrcStrings() {
        return lrcStrings;
    }

    private List<String> lrcStrings;

    //构造函数
    public LrcView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
    }

    public LrcView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public LrcView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String[] striings = {"床前明月光，", "疑是地上霜。", "举头望明月，", "低头思故乡。"};
        Point point = new Point(canvas.getWidth() / 2, canvas.getHeight() / 2);
        textCenter(striings, mPaint, centerPaint, canvas, point, Paint.Align.CENTER, lineNum, centerLine);

    }

    //初始化函数
    private void init(AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(50);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextAlign(Paint.Align.CENTER);

        centerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerPaint.setTextSize(50);
        centerPaint.setColor(Color.BLUE);
        centerPaint.setTextAlign(Paint.Align.CENTER);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(1);
        linePaint.setColor(Color.RED);

        centerLine = 0;
        lineNum = 3;

    }

    /**
     * 加载歌词文件
     *
     * @param lrcFile 歌词文件名
     */
    public void loadLrc(final String lrcFile, Context context) {
        InputStream is = context.getClass().getClassLoader().getResourceAsStream("assets/" + lrcFile);
        try {
            int lenght = is.available();
            byte[] buffer = new byte[lenght];
            is.read(buffer);
            lrcString = new String(buffer, "utf8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void textCenter(String[] strings, Paint paint, Paint centerPaint, Canvas canvas, Point point, Paint.Align aligin, int lineNum, int centerLine) {
        paint.setTextAlign(aligin);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        int length = strings.length;
        canvas.drawText(strings[centerLine] + "", point.x, point.y, centerPaint);
        for (int i = 1; i < lineNum / 2 + 1; i++) {
            if (centerLine + i < length) {
                float yAxis = -i * (-top + bottom);
                canvas.drawText(strings[i + centerLine] + "", point.x, point.y - yAxis, paint);
            }
        }
        for (int i = 1; i < lineNum / 2 + 1; i++) {
            if (centerLine - i >= 0) {
                float yAxis = -i * (-top + bottom);
                canvas.drawText(strings[centerLine - i] + "", point.x, point.y - yAxis, paint);
            }
        }
        canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2, linePaint);
    }

    public void nextLine() {
        if (centerLine == 3) {
            centerLine = 0;
            invalidate();
            return;
        }
        centerLine++;
        invalidate();
    }

}
