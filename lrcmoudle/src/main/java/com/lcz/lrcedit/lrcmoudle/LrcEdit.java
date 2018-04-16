package com.lcz.lrcedit.lrcmoudle;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LrcEdit extends View {



    private List<String> lrcStrings;
    private TextPaint textPaint;
    private String TAG = "view";
    private int lastY;
    private int viewHeight;
    private float lrcOffset;
    private int endLine;
    private int offsetCounter;
    private int currentLine;
    private GestureDetector gestureDetector;

    public LrcEdit(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
    }
    public LrcEdit(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }
    public LrcEdit(Context context) {
        super(context);
    }
    public List<String> getLrcStrings() {
        return lrcStrings;
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(endLine<=lrcStrings.size()&&endLine>0){
            textDraw(lrcStrings,canvas,textPaint,endLine);
            currentLine = endLine;
        }

    }
    private void reDraw(int endLine){
        this.endLine = endLine;
        invalidate();
    }
    private void textDraw(List<String> strings, Canvas canvas, Paint paint,int endLine){
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float top=fontMetrics.top;
        float bottom=fontMetrics.bottom;
        float offset=bottom - top;
        lrcOffset = offset;
        for(int i=0;i<viewHeight/lrcOffset;i++){
            if(endLine-1-i<0){
                break;
            }
            StaticLayout staticLayout = new StaticLayout(strings.get(endLine-1-i)+"", textPaint, (int) getWidth(),
                    Layout.Alignment.ALIGN_CENTER, 1f, 0f, false);
            drawText(canvas, staticLayout, canvas.getHeight()-offset*(i+1));
        }

    }

    private void drawText(Canvas canvas, StaticLayout staticLayout, float y) {
        canvas.save();
        canvas.translate(0, y - staticLayout.getHeight() / 2);
        staticLayout.draw(canvas);
        canvas.restore();
    }
    //添加歌词函数
    public void addLrcString(String string){
        lrcStrings.add(string);
        endLine = lrcStrings.size();
        invalidate();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        viewHeight = heightSize;
        Log.e(TAG, "onMeasure--widthMode-->" + widthMode);
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        Log.e(TAG, "onMeasure--widthSize-->" + widthSize);
        Log.e(TAG, "onMeasure--heightMode-->" + heightMode);
        Log.e(TAG, "onMeasure--heightSize-->" + heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    public boolean onTouchEvent(MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }
    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            endLine = currentLine;
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int y = (int)(e1.getY()-e2.getY());
            if (200<Math.abs(y)){
                    endLine += y/200;
                    if(endLine<=lrcStrings.size()&&endLine>=(int)viewHeight/lrcOffset-1){
                        invalidate();
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

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        SavedState savedState = new SavedState(superState);
        savedState.edline = currentLine;
        Log.e("onSaveInstanceState()",savedState.edline+"");
        return savedState;
    }

    static class SavedState extends BaseSavedState{

        private int edline;

        public SavedState(Parcel source) {
            super(source);
            edline = source.readInt();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(edline);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        Log.e("onRestoreInstanceState",ss.edline+"");
        super.onRestoreInstanceState(ss.getSuperState());
        //调用别的方法，把保存的数据重新赋值给当前的自定义View
        reDraw(ss.edline);
    }
}
