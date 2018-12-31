package com.litchiny.dailylife.database;

import android.database.Cursor;


import com.litchiny.dailylife.mode.BillTypeDB;
import com.litchiny.dailylife.mode.DayBillDB;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ll on 2018/8/4.
 */

public class DatabaseUtil {

    public static void addBillType(BillTypeDB dayBillDB) {
        if (null != dayBillDB)
            dayBillDB.save();
    }

    public static List<BillTypeDB> queryAllBillTypeList() {
        return LitePal.findAll(BillTypeDB.class);
    }

    public static BillTypeDB queryBillType(String desc) {
      return LitePal.where("desc = ?",desc).findFirst(BillTypeDB.class);
    }

    public static void updateBillType(BillTypeDB dayBillDB) {
        if (null != dayBillDB)
            dayBillDB.updateAsync(dayBillDB.id);
    }

    public static void delBillType(long id) {
        LitePal.delete(BillTypeDB.class, id);
    }

    public static void addDayBill(DayBillDB dayBillDB) {
        if (null != dayBillDB)
            dayBillDB.save();
    }

    public static void addDayBillList(List<DayBillDB> dayBillDBList) {
        if (null != dayBillDBList && dayBillDBList.size() > 0)
            LitePal.saveAll(dayBillDBList);
    }

    public static List<DayBillDB> queryAllDayBillList() {
        return LitePal.findAll(DayBillDB.class);
    }


    public static List<DayBillDB> queryTypeDayBillList(String type) {
        List<DayBillDB> dayBillDBList = new ArrayList<>();
        Cursor cursor = LitePal.findBySQL("SELECT * FROM daybilldb WHERE type = ?", String.valueOf(type));
        DayBillDB dayBillDB;
        try {
            if (cursor != null && cursor.getColumnCount() > 0)
                while (cursor.moveToNext()) {
                    dayBillDB = new DayBillDB();
                    dayBillDB.timeStamp = cursor.getLong(cursor.getColumnIndex("timestamp"));
                    dayBillDB.id = cursor.getInt(cursor.getColumnIndex("id"));
                    dayBillDB.type = cursor.getInt(cursor.getColumnIndex("type"));
                    dayBillDB.dayStart = cursor.getInt(cursor.getColumnIndex("daystart"));
                    dayBillDB.money = cursor.getDouble(cursor.getColumnIndex("money"));
                    dayBillDB.typeDesc = cursor.getString(cursor.getColumnIndex("typedesc"));
                    dayBillDB.addDesc = cursor.getString(cursor.getColumnIndex("adddesc"));
                    dayBillDBList.add(dayBillDB);
                }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor)
                cursor.close();
        }
        return dayBillDBList;
    }

    public static List<DayBillDB> queryDayBillList(long startTime, long endTime) {
        List<DayBillDB> dayBillDBList = new ArrayList<>();
        Cursor cursor = LitePal.findBySQL("SELECT * FROM daybilldb WHERE timestamp >= ? AND timestamp <= ?", String.valueOf(startTime), String.valueOf(endTime));
        DayBillDB dayBillDB;
        try {
            if (cursor != null && cursor.getColumnCount() > 0)
                while (cursor.moveToNext()) {
                    dayBillDB = new DayBillDB();
                    dayBillDB.timeStamp = cursor.getLong(cursor.getColumnIndex("timestamp"));
                    dayBillDB.id = cursor.getInt(cursor.getColumnIndex("id"));
                    dayBillDB.type = cursor.getInt(cursor.getColumnIndex("type"));
                    dayBillDB.dayStart = cursor.getInt(cursor.getColumnIndex("daystart"));
                    dayBillDB.money = cursor.getDouble(cursor.getColumnIndex("money"));
                    dayBillDB.typeDesc = cursor.getString(cursor.getColumnIndex("typedesc"));
                    dayBillDB.addDesc = cursor.getString(cursor.getColumnIndex("adddesc"));
                    dayBillDBList.add(dayBillDB);
                }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor)
                cursor.close();
        }
        return dayBillDBList;
    }

    public static void delDayBill(DayBillDB dayBillDB) {
        if (null != dayBillDB)
            dayBillDB.delete();
    }

    public static void delDayBillList(List<DayBillDB> dayBillDBList) {
        if (null != dayBillDBList && dayBillDBList.size() > 0) {
            for (DayBillDB dayBillDB : dayBillDBList)
                dayBillDB.delete();
        }
    }

    public static void delDayBillListFromTime(long startTime, long endTime) {
        LitePal.deleteAll(DayBillDB.class, "timeStamp >= ? AND timeStamp <= ?", String.valueOf(startTime), String.valueOf(endTime));
    }

    public static void updateDayBill(DayBillDB dayBillDB) {
        if (null != dayBillDB)
            dayBillDB.update(dayBillDB.id);
    }


}
