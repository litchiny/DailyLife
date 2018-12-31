/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.litchiny.dailylife.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.litchiny.dailylife.R;
import com.litchiny.dailylife.mode.ShowParent;


/**
 * author: LL .
 * date:   On 2018/4/13
 * caption:
 */
public class TeamViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "TeamViewHolder";
    private final TextView mTitleView;
    private final LinearLayout ll_title;

    public TeamViewHolder(View itemView) {
        super(itemView);
        mTitleView = itemView.findViewById(R.id.title);
        ll_title = itemView.findViewById(R.id.ll_title);
    }

    public void update(ShowParent team) {
//        ll_title.setVisibility(team.isShow ? View.VISIBLE : View.GONE);
////        LogUtil.i(TAG, "groupPosition--team: " + team.isShow + ",ll_title: " + ll_title.getVisibility());
//        if (team.isShow) mTitleView.setText(team.title);
    }
}
