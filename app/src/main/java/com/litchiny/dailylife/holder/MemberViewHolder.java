/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.litchiny.dailylife.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.litchiny.dailylife.R;
import com.litchiny.dailylife.mode.DayBillDB;


/**
 * author: LL .
 * date:   On 2018/4/13
 * caption:
 */
public class MemberViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout ll_parent;
    public ToggleButton tb_item_switch;
    private TextView tv_item_key;
    private TextView tv_item_value;

    public MemberViewHolder(View itemView) {
        super(itemView);
        ll_parent = itemView.findViewById(R.id.ll_parent);
        tv_item_value = itemView.findViewById(R.id.tv_item_value);
        tv_item_key = itemView.findViewById(R.id.tv_item_key);
    }

    public void update(Context context, DayBillDB item) {
        if (null != ll_parent) {
            tv_item_value.setText(item.money > 0 ? String.valueOf(item.money) : "0");
            tv_item_key.setText(item.typeDesc);
        }
    }

    public void setVisibility(boolean isVisible) {
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        if (isVisible) {
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            param.width = LinearLayout.LayoutParams.MATCH_PARENT;
            itemView.setVisibility(View.VISIBLE);
        } else {
            itemView.setVisibility(View.GONE);
            param.height = 0;
            param.width = 0;
        }
        itemView.setLayoutParams(param);
    }
}
