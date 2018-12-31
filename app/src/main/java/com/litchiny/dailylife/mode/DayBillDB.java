package com.litchiny.dailylife.mode;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.LitePalSupport;

/**
 * Created by ll on 2018/8/4.
 */

public class DayBillDB extends LitePalSupport implements Parcelable {
    public int id;
    public long timeStamp;                     //时间
    public int type;                            //类型
    public String typeDesc;
    public double money;
    public String addDesc;                     //备注
    public int dayStart = 0;

    public DayBillDB(long timeStamp, int type, String typeDesc, double money, int dayStart) {
        this.timeStamp = timeStamp;
        this.type = type;
        this.typeDesc = typeDesc;
        this.money = money;
        this.dayStart = dayStart;
    }

    public DayBillDB() {
    }

    protected DayBillDB(Parcel in) {
        id = in.readInt();
        timeStamp = in.readLong();
        type = in.readInt();
        typeDesc = in.readString();
        money = in.readDouble();
        addDesc = in.readString();
        dayStart = in.readInt();
    }

    public static final Creator<DayBillDB> CREATOR = new Creator<DayBillDB>() {
        @Override
        public DayBillDB createFromParcel(Parcel in) {
            return new DayBillDB(in);
        }

        @Override
        public DayBillDB[] newArray(int size) {
            return new DayBillDB[size];
        }
    };

    @Override
    public String toString() {
        return "DayBillDB{" +
                "id=" + id +
                ", timeStamp=" + timeStamp +
                ", type=" + type +
                ", typeDesc='" + typeDesc + '\'' +
                ", money=" + money +
                ", addDesc='" + addDesc + '\'' +
                ", dayStart=" + dayStart +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(timeStamp);
        dest.writeInt(type);
        dest.writeString(typeDesc);
        dest.writeDouble(money);
        dest.writeString(addDesc);
        dest.writeInt(dayStart);
    }
}
