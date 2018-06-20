package com.lcz.lrcedit.lrcmoudle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LrcUtil {

    //获取歌手名
    public static String getArtist(String lrcString) {
        String pattern = "\\[ar:(.*)\\]";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(lrcString);
        if (m.find()) {
            return m.group(1);
        } else {
            return null;
        }
    }

    //获取曲名
    public static String getTitle(String lrcString) {
        String pattern = "\\[ti:(.*)\\]";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(lrcString);
        if (m.find()) {
            return m.group(1);
        } else {
            return null;
        }
    }

    //获取专辑名
    public static String getAlbum(String lrcString) {
        String pattern = "\\[al:(.*)\\]";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(lrcString);
        if (m.find()) {
            return m.group(1);
        } else {
            return null;
        }
    }

    //获取编辑者名
    public static String getEditor(String lrcString) {
        String pattern = "\\[by:(.*)\\]";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(lrcString);
        if (m.find()) {
            return m.group(1);
        } else {
            return null;
        }
    }

    //获取offset
    public static String getOffset(String lrcString) {
        String pattern = "\\[offset:(.*)\\]";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(lrcString);
        if (m.find()) {
            return m.group(1);
        } else {
            return null;
        }
    }
}
