package com.litchiny.dailylife.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.litchiny.dailylife.R;


/**
 * author: LL .
 * date:   On 2018/4/11
 * caption:
 */

public class CustomNewDialog extends BaseDialogFragment {
    protected static CustomNewDialog dialog;
    private String title;
    private String content;
    private String positiveButtonText;
    private String negativeButtonText;
    private String oneButtonText;
    private View.OnClickListener positiveButtonClickListener;
    private View.OnClickListener negativeButtonClickListener;

    public CustomNewDialog() {
        this.setStyle(0, R.style.CustomDialog);
    }

    public static CustomNewDialog getInstance() {
        if (dialog == null) {
            synchronized (CustomNewDialog.class) {
                if (dialog == null) {
                    dialog = new CustomNewDialog();
                }
            }
        }
        return dialog;
    }

    public CustomNewDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CustomNewDialog setContent(String content) {
        this.content = content;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.dialog_new_layout, null);
        //设计dialog布局
        //设置title
        boolean isShow = !TextUtils.isEmpty(title);
        layout.findViewById(R.id.tv_dialog_title).setVisibility(isShow ? View.VISIBLE : View.GONE);
        if (isShow)
            ((TextView) layout.findViewById(R.id.tv_dialog_title)).setText(isShow ? title : "");

        isShow = !TextUtils.isEmpty(content);
        layout.findViewById(R.id.tv_dialog_content).setVisibility(isShow ? View.VISIBLE : View.GONE);
        //设置内容
        if (isShow)
            ((TextView) layout.findViewById(R.id.tv_dialog_content)).setText(isShow ? content : "");

        isShow = !TextUtils.isEmpty(oneButtonText);
        layout.findViewById(R.id.ll_show_choice).setVisibility(isShow? View.GONE: View.VISIBLE);

        //设置确定按钮
        isShow = !TextUtils.isEmpty(positiveButtonText);
        layout.findViewById(R.id.bt_dialog_positive_button).setVisibility(isShow ? View.VISIBLE : View.GONE);
        ((TextView) layout.findViewById(R.id.bt_dialog_positive_button)).setText(isShow ? positiveButtonText : "");
        layout.findViewById(R.id.bt_dialog_positive_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (null != positiveButtonClickListener) {
                    positiveButtonClickListener.onClick(layout);
                    dialog.dismiss();
                }
            }
        });

        //设置取消按钮
        isShow = !TextUtils.isEmpty(negativeButtonText);
        layout.findViewById(R.id.bt_dialog_negative_button).setVisibility(isShow ? View.VISIBLE : View.GONE);
        ((TextView) layout.findViewById(R.id.bt_dialog_negative_button)).setText(isShow ? negativeButtonText : "");
        layout.findViewById(R.id.bt_dialog_negative_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (null != negativeButtonClickListener) {
                    negativeButtonClickListener.onClick(layout);
                    dialog.dismiss();
                }
            }
        });
        return layout;
    }

    public CustomNewDialog setPositiveButton(String positiveButtonText, View.OnClickListener listener) {
        this.positiveButtonText = positiveButtonText;
        this.positiveButtonClickListener = listener;
        return this;
    }

    public CustomNewDialog setNegativeButton(String negativeButtonText, View.OnClickListener listener) {
        this.negativeButtonText = negativeButtonText;
        this.negativeButtonClickListener = listener;
        return this;
    }

    public CustomNewDialog setOneButton(String oneButtonText) {
        this.oneButtonText = oneButtonText;
        return this;
    }
}
