package com.lcz.lrcedit.lrcmoudle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LrcEdit extends View {

    private List<String> lrcStrings;
    private Paint textPaint;

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

    private void init(){
        lrcStrings = new ArrayList<String>();
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(50);
        textPaint.setColor(Color.BLUE);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        textDraw(lrcStrings,canvas,textPaint);
    }

    private void textDraw(List<String> strings, Canvas canvas, Paint paint){
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float top=fontMetrics.top;
        float bottom=fontMetrics.bottom;
        int length=strings.size();
        float total=(length-1)*(-top+bottom)+(-fontMetrics.ascent+fontMetrics.descent);
        float offset=total/2-bottom;
        for(int i=0;i<length;i++){
            float yAxis=-(length-i-1)*(-top+bottom)+offset;
            canvas.drawText(strings.get(i)+"",canvas.getWidth()/2,canvas.getHeight()/2+yAxis,paint);
        }

    }
    //添加歌词函数
    public void addLrcString(String string){
        lrcStrings.add(string);
        postInvalidate();
        }



}
