package com.jy.gxh.day2homework.beans;

/**
 * Created by GXH on 2019/9/19.
 */

public class MsgBean {
    private int type;
    private int progress;
    private long max;
    private String path;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MsgBean(int type, int progress, long max, String path) {

        this.type = type;
        this.progress = progress;
        this.max = max;
        this.path = path;
    }
}
