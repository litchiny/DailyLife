package com.litchiny.dailylife.listener;

import android.view.View;

/**
 * author: LL .
 * date:   On 2018/4/16
 * caption:
 */


public interface OnChildClickListener {
    /**
     * Callback when the child item was clicked.
     *
     * @param itemView      the itemView of the child item.
     * @param groupPosition the position of the group that the child item was clicked.
     * @param childPosition the position of the child in group.
     */
    void onChildClick(View itemView, int groupPosition, int childPosition);

    void onToggleClick(View itemView, int groupPosition, int childPosition);
}