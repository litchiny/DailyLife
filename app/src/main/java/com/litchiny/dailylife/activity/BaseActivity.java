package com.litchiny.dailylife.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.litchiny.dailylife.utils.EditTextDialog;


/**
 * Created by ll on 2018/8/5.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected EditTextDialog dialog;
    public void setOnClickListener(View... views) {
        if (views != null && views.length > 0)
            for (View view : views)
                view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onPreSetContentView();
        setContentView(getLayoutResId());
        initData();
        initDialog();
        initView();

    }

    private void initDialog() {
        dialog = EditTextDialog.getInstance();
        dialog.setOnPositiveClickListener(new EditTextDialog.onPositiveClickListener() {
            @Override
            public void onPositiveClick(String content, View view) {
                dialogPositiveClick(content,view);
            }
        });
    }

    public void dialogPositiveClick(String content, View view) {

    }

    public abstract void initData();
    public abstract void initView();
    public void onPreSetContentView(){

    }

    public abstract int getLayoutResId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != dialog && dialog.isVisible())
            dialog.dismiss();
    }
}
