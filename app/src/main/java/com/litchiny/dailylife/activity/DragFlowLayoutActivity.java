package com.litchiny.dailylife.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.litchiny.dailylife.R;
import com.litchiny.dailylife.database.DatabaseUtil;
import com.litchiny.dailylife.mode.BillTypeDB;
import com.litchiny.dailylife.mode.DayBillDB;
import com.litchiny.dailylife.mode.TestBean;
import com.litchiny.dailylife.utils.EditTextDialog;
import com.litchiny.dailylife.utils.LogUtil;
import com.litchiny.dailylife.utils.TimeUtil;
import com.litchiny.dailylife.utils.ToolUtil;
import com.litchiny.dailylife.view.FlowLayout;
import com.lwyy.dayhome.PublicConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


/**
 */
public class DragFlowLayoutActivity extends BaseActivity {

    private static final String TAG = "DragFlowLayoutActivity";
    private EditText et_price, et_desc;
    private Context context;

    private Calendar calendar;
    private List<TestBean> testBeans = new ArrayList<>();
    private DayBillDB realBillDB;
    private int realIndex = -1;
    private FlowLayout fl_show_type;
    private List<String> datas = new ArrayList<>();
    ;

    @Override
    public void initData() {
        context = this;
        initList();
        calendar = Calendar.getInstance();
        initIntent();
    }

    private void initIntent() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            realBillDB = bundle.getParcelable(PublicConstant.Companion.getBUNDLE_DAY_Bill_BEAN());
            if (realBillDB != null) {
                LogUtil.i(TAG, "realBillDB: " + realBillDB.toString());
                for (int i = 0; i < testBeans.size(); i++) {
                    if (testBeans.get(i).text.equals(realBillDB.typeDesc)) {
                        realIndex = i;
                        LogUtil.i(TAG, "testBeans.get(i): " + testBeans.get(i).toString());
                        break;
                    }
                }
                if (realIndex >= 0) {
                    TestBean bean = testBeans.get(realIndex);
                    bean.isClick = true;
                    testBeans.set(realIndex, bean);
                }
            }
        }
    }


    public void initView() {
        fl_show_type = findViewById(R.id.fl_show_type);
        et_price = findViewById(R.id.et_price);
        et_desc = findViewById(R.id.et_desc);
        if (null != realBillDB) {
            et_price.setText(String.valueOf(realBillDB.money));
            et_desc.setText(realBillDB.addDesc);
        }

        setOnClickListener(findViewById(R.id.iv_save), findViewById(R.id.iv_back));
        fl_show_type.setData(datas, true, realIndex);
        fl_show_type.setFlowClickListener(new FlowLayout.FlowClickListener() {
            @Override
            public void setOnClickItemListener(int index) {
                if (index == testBeans.size() - 1) {
                    showDialog();
                    return;
                }
                testBeans.get(index).isClick = true;
            }

            @Override
            public void setOnClickLongItemListener(int index) {
                BillTypeDB billTypeDB = DatabaseUtil.queryBillType(datas.get(index));
//                List<DayBillDB> dayBillDBS = DatabaseUtil.queryTypeDayBillList(datas.get(index));
                if (null != billTypeDB) {   //删除之前增加一次校验检查   即是否依旧有type使用此text
                    billTypeDB.isShow = false;
                    DatabaseUtil.delBillType(billTypeDB.id);                                 //只更新不删除,删除修改是否显示的值
                    LogUtil.i(TAG, "107---billTypeDB: " + billTypeDB.toString()
//                            +",dayBillDBS: "+ dayBillDBS.size()
                    );
                }

                testBeans.remove(index);
                datas.remove(index);
                fl_show_type.removeItem(index);
                if (datas.size() == 1) {
                    fl_show_type.clearDeleteType();
                }
            }
        });
        LogUtil.i(TAG, "143---testBeans: " + Arrays.toString(testBeans.toArray()));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_add_bill;
    }

    private void showDialog() {
        dialog.setShowType(EditTextDialog.TYPE_EDITTEXT);
        dialog.onCreate(null);
        dialog.show(this);
    }

    public void dialogPositiveClick(String content, View view) {
        if (TextUtils.isEmpty(content))
            return;
        //添加到billTypeDB中
        BillTypeDB billTypeDB = DatabaseUtil.queryBillType(content);
        if (null != billTypeDB) {
            billTypeDB.isShow = true;
            DatabaseUtil.updateBillType(billTypeDB);
        } else {
            DatabaseUtil.addBillType(billTypeDB = new BillTypeDB(content, System.currentTimeMillis()));
            TestBean testBean = new TestBean(content);
            testBean.isClick = true;
            int size = testBeans.size() - 1;
            testBeans.set(size, testBean);
            datas.set(size, content);
            addStr();
            fl_show_type.setData(datas, true, size);
        }

        LogUtil.i(TAG, "billTypeDB: " + billTypeDB.toString());

    }

    private void initList() {
        List<BillTypeDB> billTypeDBS = DatabaseUtil.queryAllBillTypeList();
        if (null != billTypeDBS && billTypeDBS.size() > 0) {
            for (BillTypeDB billTypeDB : billTypeDBS) {
                if (billTypeDB.isShow) {
                    testBeans.add(new TestBean(billTypeDB.desc));
                    datas.add(billTypeDB.desc);
                }

            }
        }
        addStr();
    }

    private void addStr() {
        String addStr = "+";
        testBeans.add(new TestBean(addStr, false));
        datas.add(addStr);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_save:
                if (fl_show_type.isLongClick()) {
                    fl_show_type.clearDeleteType();
                    return;
                }

                if (testBeans.size() == 1) {
                    ToolUtil.showToast(context, "请增加一个类别");
                    return;
                }
                String money = et_price.getText().toString().trim();
                if (TextUtils.isEmpty(money)) {
                    ToolUtil.showToast(context, "价格不能为空");
                    return;
                }
                String desc = et_desc.getText().toString().trim();
                StringBuilder sb = new StringBuilder();

                for (TestBean bean : testBeans) {
                    if (bean.isClick)
                        sb.append(bean.text).append(",");
                }

                DayBillDB dayBillDB = null == realBillDB ? new DayBillDB() : realBillDB;
                if (!TextUtils.isEmpty(desc)) {
                    dayBillDB.addDesc = desc;
                }

                if (TextUtils.isEmpty(sb.toString())) {
                    ToolUtil.showToast(context, "请选择一个账单类别");
                    return;
                }

                dayBillDB.typeDesc = sb.toString().substring(0, sb.length() - 1);
                dayBillDB.money = Double.valueOf(money);
                if (null == realBillDB) {
                    dayBillDB.timeStamp = calendar.getTimeInMillis();
                    TimeUtil.resetDayStart(calendar);
                    List<DayBillDB> dayBillDBS = DatabaseUtil.queryDayBillList(calendar.getTimeInMillis(), dayBillDB.timeStamp);
                    dayBillDB.dayStart = dayBillDBS != null && dayBillDBS.size() > 0 ? 0 : 1;
                    DatabaseUtil.addDayBill(dayBillDB);
                } else {
                    DatabaseUtil.updateDayBill(dayBillDB);
                }
                LogUtil.i(TAG, "dayBillDB: " + dayBillDB.toString());
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }
}
