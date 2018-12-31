package com.litchiny.dailylife.mode;

import org.litepal.crud.LitePalSupport;

/**
 * Created by ll on 2018/8/12.
 */

public class BillTypeDB extends LitePalSupport {
    public int id;
    public String desc;
    public long time;
    public boolean isShow;             //如果标记为了删除,则改为false

    public BillTypeDB(String desc, long time) {
        this.desc = desc;
        this.time = time;
        this.isShow = true;
    }

    public BillTypeDB() {
    }

    @Override
    public String toString() {
        return "BillTypeDB{" +
                "desc='" + desc + '\'' +
                ", time=" + time +
                ", isShow=" + isShow +
                ", id=" + id +
                '}';
    }
}
