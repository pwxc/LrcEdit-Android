package com.lcz.lrcedit.lrcmoudle;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

public class LrcString {


    /**
     * 这个类的主要目的是为了取代String在LrcEdit类中的应用
     * 因为歌词所要记录的东西实在是太多了
     * 光有一个String是不够的
     **/

    private String text;
    private StaticLayout staticLayout;
    private float offset;

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public String getText() {
        return text;
    }
    public LrcString(String string){
        this.text = string;
    }

    void init(TextPaint paint, int width) {
        staticLayout = new StaticLayout(text, paint, width, Layout.Alignment.ALIGN_CENTER, 1f, 0f, false);
    }

    int getHeight() {
        if (staticLayout == null) {
            return 0;
        }
        return staticLayout.getHeight();
    }

    StaticLayout getStaticLayout() {
        return staticLayout;
    }
}
