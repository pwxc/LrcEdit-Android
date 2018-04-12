package com.lcz.lrcedit.lrcmoudle;

import org.junit.Test;

import static org.junit.Assert.*;

public class LrcUtilTest {

    @Test
    public void getArtist() {
        assertEquals("中文", LrcUtil.getArtist("[ar:中文]"));
        assertEquals("1125", LrcUtil.getArtist("[ar:1125]"));
        assertEquals("[1125]", LrcUtil.getArtist("[ar:[1125]]"));
        assertEquals(null, LrcUtil.getArtist("[ar1235]"));
    }

    @Test
    public void getAlbum(){
        assertEquals("中文", LrcUtil.getAlbum("[al:中文]"));
        assertEquals("1125", LrcUtil.getAlbum("[al:1125]"));
        assertEquals("[1125]", LrcUtil.getAlbum("[al:[1125]]"));
        assertEquals(null, LrcUtil.getAlbum("[al1235]"));
    }

    @Test
    public void getEditer(){
        assertEquals("中文", LrcUtil.getEditer("[by:中文]"));
        assertEquals("1125", LrcUtil.getEditer("[by:1125]"));
        assertEquals("[1125]", LrcUtil.getEditer("[by:[1125]]"));
        assertEquals(null, LrcUtil.getEditer("[by1235]"));
    }

    @Test
    public void getTitle(){
        assertEquals("中文", LrcUtil.getTitle("[ti:中文]"));
        assertEquals("1125", LrcUtil.getTitle("[ti:1125]"));
        assertEquals("[1125]", LrcUtil.getTitle("[ti:[1125]]"));
        assertEquals(null, LrcUtil.getTitle("[ti1235]"));
    }

    @Test
    public void getOffset(){
        assertEquals("中文", LrcUtil.getOffset("[offset:中文]"));
        assertEquals("1125", LrcUtil.getOffset("[offset:1125]"));
        assertEquals("[1125]", LrcUtil.getOffset("[offset:[1125]]"));
        assertEquals(null, LrcUtil.getOffset("[offset1235]"));
    }
}