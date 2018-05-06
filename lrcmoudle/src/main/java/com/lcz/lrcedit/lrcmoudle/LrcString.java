package com.lcz.lrcedit.lrcmoudle;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;


public class LrcString{


    /**
     * 这个类的主要目的是为了取代String在LrcEdit类中的应用
     * 因为歌词所要记录的东西实在是太多了
     * 光有一个String是不够的
     **/

    private String text;
    private long startTime = -1;
    private StaticLayout staticLayout;
    private float offset;

    public LrcString(String string){
        this.text = string;
    }

    void init(TextPaint paint, int width) {
        staticLayout = new StaticLayout(text, paint, width, Layout.Alignment.ALIGN_CENTER, 1f, 0f, false);
    }

    float getHeight() {
        if (staticLayout == null) {
            return 0;
        }
        return staticLayout.getHeight();
    }

    public String getSaveString(){
        if (startTime == -1){
            return null;
        }else {
            int min = (int)startTime / 60000;
            float sec = (float)(startTime%60000) / 1000;
            String saveString = String.format("[%02d:%05.2f]%s",min,sec,text);
            return saveString;
        }
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public String getText() {
        return text;
    }

    public StaticLayout getStaticLayout() {
        return staticLayout;
    }
}
