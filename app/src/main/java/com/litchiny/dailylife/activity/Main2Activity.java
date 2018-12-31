package com.litchiny.dailylife.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.litchiny.dailylife.R;
import com.litchiny.dailylife.adapter.BillRecyclerViewAdapter;
import com.litchiny.dailylife.database.DatabaseUtil;
import com.litchiny.dailylife.listener.OnMemberItemClickListener;
import com.litchiny.dailylife.mode.DayBillDB;
import com.litchiny.dailylife.utils.EditTextDialog;
import com.litchiny.dailylife.utils.LogUtil;
import com.lwyy.dayhome.PublicConstant;


import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends BaseActivity {
    private static final String TAG = "Main2Activity";
    private RecyclerView rv_show_bill;
    private FloatingActionButton fab_add;
    private List<DayBillDB> dayBillDBS = new ArrayList<>();
    private BillRecyclerViewAdapter adapter;
    private Context mContext;
    private int longClickPosition;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
            }
            return false;
        }
    };

    @Override
    public void initData() {
        mContext = this;
    }


    @Override
    public void initView() {
        rv_show_bill = findViewById(R.id.rv_show_bill);
        fab_add = findViewById(R.id.fab_add);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        rv_show_bill.setLayoutManager(new LinearLayoutManager(this));
        rv_show_bill.setHasFixedSize(true);
        adapter = new BillRecyclerViewAdapter();
        rv_show_bill.setAdapter(adapter);
        setOnClickListener(fab_add, findViewById(R.id.iv_more));
        adapter.setOnMemberItemClickListener(new OnMemberItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                LogUtil.i(TAG, "dayBillDBS.get(position); " + dayBillDBS.get(position).toString());
                startAddCategory(dayBillDBS.get(position));
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "onItemLongClick: ");
                longClickPosition = position;
                dialog.setShowType(EditTextDialog.TYPE_TEXTVIEW);
                DayBillDB dayBillDB = dayBillDBS.get(position);
                dialog.setTextDesc("请是否确认删除"+ dayBillDB.typeDesc+" , "+ dayBillDB.money+"?" );
                dialog.onCreate(null);
                dialog.show((Activity) mContext);
            }
        });
    }

    private void startAddCategory(DayBillDB dayBillDB) {
        Intent intent = new Intent(Main2Activity.this, DragFlowLayoutActivity.class);
        if (null != dayBillDB) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(PublicConstant.Companion.getBUNDLE_DAY_Bill_BEAN(), dayBillDB);
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    private void initTestList() {
        dayBillDBS = DatabaseUtil.queryAllDayBillList();
        LogUtil.i(TAG, "dayBillDBS: " + dayBillDBS.size());
        adapter.setData(dayBillDBS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initTestList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add:
                startAddCategory(null);
                break;
            case R.id.iv_more:
                startActivity(new Intent(this, CalculateActivity.class));
                break;
        }
    }

    public void dialogPositiveClick(String content, View view) {
        DatabaseUtil.delDayBill(dayBillDBS.get(longClickPosition));
        dayBillDBS.remove(longClickPosition);
        adapter.setData(dayBillDBS);
    }
}
