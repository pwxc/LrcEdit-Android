package com.lcz.lrcedit.lrcmoudle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class LrcTimeEdit extends View {

    private int editType;
    private final int NORMAL = 0;
    private final int TIMEADD = 1;
    private TextPaint textPaint;
    private int currentLine = 0;
    private long startTime = 0;
    private List<LrcString> lrcStrings;
    private float offset = 20;
    private boolean isNewAdd = false;

    private GestureDetector gestureDetector;

    public LrcTimeEdit(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        init();
    }
    public LrcTimeEdit(Context context, AttributeSet attributeSet) {
        this(context, attributeSet,0);
    }
    public LrcTimeEdit(Context context) {
        this(context,null);
    }

    private void init(){
        lrcStrings = new ArrayList<>();
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(50);
        textPaint.setTextAlign(Paint.Align.LEFT);
        gestureDetector = new GestureDetector(getContext(), simpleOnGestureListener);
        gestureDetector.setIsLongpressEnabled(false);
    }

    private void timeAddInit(){
        startTime=System.currentTimeMillis();
        editType = TIMEADD;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!lrcStrings.isEmpty()){
            textDraw(lrcStrings,canvas,textPaint);
            if(isNewAdd){
                scrollTo(0,(int)lrcStrings.get(0).getOffset() - getHeight()/2);
                isNewAdd = false;
            }
        }


    }

    private void textDraw(List<LrcString> strings, Canvas canvas, TextPaint paint){
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        offset = bottom - top;
        for(int i=0;i<strings.size();i++){
            if(i==currentLine){
                paint.setColor(Color.RED);
            }else {
                paint.setColor(Color.BLUE);
            }
            strings.get(i).init(paint,getWidth());
            if(i == 0){
                strings.get(i).setOffset(offset);
            }else {
                strings.get(i).setOffset(strings.get(i-1).getOffset()+strings.get(i-1).getHeight()+offset);
            }
            drawText(canvas,strings.get(i).getStaticLayout(),strings.get(i).getOffset());
        }

    }

    private void drawText(Canvas canvas, StaticLayout staticLayout, float y) {
        canvas.save();
        canvas.translate(0, y);
        staticLayout.draw(canvas);
        canvas.restore();
    }

    public void initStrings(ArrayList<String> lrcEdits){
        for (String string:lrcEdits){
            addLrcString(string);
        }
        isNewAdd = true;

    }

    public void addLrcString(String string){
        LrcString lrcString= new LrcString(string);
        lrcStrings.add(lrcString);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }
    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int y = (int)distanceY;
            if(!lrcStrings.isEmpty()){
                scrollBy(0,y);
                //这条式子真是反人类，重构的时候优先改善
                float top = lrcStrings.get(0).getOffset() - getHeight()/2;
                float bottom = lrcStrings.get(lrcStrings.size()-1).getOffset() - getHeight()/2;
                if(getScrollY()<top){
                    scrollTo(0, (int)top);
                }
                if(getScrollY()>bottom){
                    scrollTo(0, (int)(bottom));
                }
            }

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }
    };

}
