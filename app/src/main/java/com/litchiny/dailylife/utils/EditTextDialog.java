package com.litchiny.dailylife.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.litchiny.dailylife.R;


/**
 * author: LL .
 * date:   On 2018/4/11
 * caption:
 */

public class EditTextDialog extends BaseDialogFragment {
    public final static int TYPE_EDITTEXT = 0;
    public final static int TYPE_TEXTVIEW = 1;
    protected static EditTextDialog dialog;
    private onPositiveClickListener listener;
    private EditText et_dialog_content;
    private TextView tv_title_show;
    private String textDesc = "";

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    private int showType = TYPE_EDITTEXT;

    public void setTextDesc(String textDesc){
        this.textDesc = textDesc;
    }
    public EditTextDialog() {
        this.setStyle(0, R.style.CustomDialog);
    }

    public static EditTextDialog getInstance() {
        if (dialog == null) {
            synchronized (EditTextDialog.class) {
                if (dialog == null) {
                    dialog = new EditTextDialog();
                }
            }
        }
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.dialog_edittext_layout, null);
        //设计dialog布局
        et_dialog_content = (EditText) layout.findViewById(R.id.et_dialog_content);
        tv_title_show = (TextView) layout.findViewById(R.id.tv_title_show);
        et_dialog_content.setVisibility(showType == TYPE_EDITTEXT ? View.VISIBLE : View.GONE);
        tv_title_show.setVisibility(showType != TYPE_EDITTEXT ? View.VISIBLE : View.GONE);
        tv_title_show.setText(textDesc);

        //设置确定按钮
        layout.findViewById(R.id.bt_dialog_positive_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (null != listener) {
                    listener.onPositiveClick(et_dialog_content.getText().toString(), layout);
                    clearView();
                }
            }
        });

        //设置取消按钮
        layout.findViewById(R.id.bt_dialog_negative_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearView();
            }
        });
        return layout;
    }

    private void clearView() {
        et_dialog_content.setText("");
        dialog.dismiss();
    }


    public void setOnPositiveClickListener(onPositiveClickListener listener) {
        this.listener = listener;
    }

    public interface onPositiveClickListener {
        void onPositiveClick(String content, View view);

    }
}
