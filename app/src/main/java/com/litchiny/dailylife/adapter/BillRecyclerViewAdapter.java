package com.litchiny.dailylife.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.litchiny.dailylife.R;
import com.litchiny.dailylife.listener.OnMemberItemClickListener;
import com.litchiny.dailylife.mode.DayBillDB;
import com.litchiny.dailylife.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ll on 2018/8/4.
 */

public class BillRecyclerViewAdapter extends RecyclerView.Adapter implements View.OnClickListener,View.OnLongClickListener {

    private List<DayBillDB> dayBillDBList = new ArrayList<>();

    public BillRecyclerViewAdapter() {

    }

    public void setData(List<DayBillDB> dayBillDBS) {
        if (dayBillDBList.size() > 0)
            dayBillDBList.clear();
        if (null != dayBillDBS && dayBillDBS.size() > 0)
            dayBillDBList.addAll(dayBillDBS);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BillViewHolder billViewHolder = new BillViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team_member, null));
        billViewHolder.ll_detail.setOnClickListener(this);
        billViewHolder.ll_detail.setOnLongClickListener(this);
        return billViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DayBillDB dayBillDB = dayBillDBList.get(position);
        BillViewHolder billViewHolder = (BillViewHolder) holder;
        boolean isStart = dayBillDB.dayStart > 0;
        billViewHolder.tv_title.setVisibility(isStart ? View.VISIBLE : View.GONE);
        if (isStart)
            billViewHolder.tv_title.setText(TimeUtil.timeStampToString(dayBillDB.timeStamp, TimeUtil.SDF_TYPE_CHINA));
        billViewHolder.tv_item_value.setText(dayBillDB.money > 0 ? dayBillDB.money + " ¥" : "0 ¥");
        billViewHolder.tv_item_key.setText(dayBillDB.typeDesc);
        billViewHolder.ll_detail.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dayBillDBList.size();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ll_detail){
            if(null != onMemberItemClickListener)
                onMemberItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    public void setOnMemberItemClickListener(OnMemberItemClickListener onMemberItemClickListener) {
        this.onMemberItemClickListener = onMemberItemClickListener;
    }

    OnMemberItemClickListener onMemberItemClickListener;

    @Override
    public boolean onLongClick(View v) {
        if(v.getId() == R.id.ll_detail){
            if(null != onMemberItemClickListener)
                onMemberItemClickListener.onItemLongClick(v, (Integer) v.getTag());
            return true;
        }
        return false;
    }

    public class BillViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title, tv_item_key, tv_item_value;
        private LinearLayout ll_detail;

        public BillViewHolder(View itemView) {
            super(itemView);
            ll_detail = itemView.findViewById(R.id.ll_detail);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_item_key = itemView.findViewById(R.id.tv_item_key);
            tv_item_value = itemView.findViewById(R.id.tv_item_value);
        }
    }
}
