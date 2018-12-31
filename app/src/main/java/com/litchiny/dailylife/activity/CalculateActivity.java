package com.litchiny.dailylife.activity;

import android.graphics.Color;
import android.text.SpannableString;
import android.view.View;
import android.widget.AdapterView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.litchiny.dailylife.R;

import java.util.ArrayList;
import java.util.List;

public class CalculateActivity extends BaseActivity implements OnChartValueSelectedListener {
    private PieChart s;
    private PieData pieData;

    @Override
    public void initData() {
        List arrayList = new ArrayList(2);
        arrayList.add(Color.BLUE);
        arrayList.add(Color.BLACK);
        List<PieEntry> arrayList2 = new ArrayList(2);
        arrayList2.add(new PieEntry(10));
        arrayList2.add(new PieEntry(20));
        PieDataSet pieDataSet = new PieDataSet(arrayList2, "");
        pieDataSet.setSliceSpace(2.0f);
        pieDataSet.setColors(arrayList);
        pieDataSet.setDrawValues(false);
        pieData = new PieData(pieDataSet);


    }

    @Override
    public void initView() {
        this.s = findViewById(R.id.pie_chart);
        this.s.setUsePercentValues(true);
        this.s.setUsePercentValues(true);
        this.s.setDragDecelerationFrictionCoef(0.1f);
//        this.s.setCenterTextColor(this.skinResourceUtil.getNewColor1());
        this.s.setCenterTextColor(getResources().getColor(R.color.colorPrimaryDark));
        this.s.setOnChartValueSelectedListener(this);
        this.s.setHoleRadius(65.0f);
        this.s.setTransparentCircleRadius(50.0f);
        Description description = new Description();
        description.setText("");
        this.s.setDescription(description);
        this.s.setDrawEntryLabels(false);
        this.s.getLegend().setEnabled(false);
        SpannableString string = new SpannableString("测试数据");
        setPieData(pieData,string);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_calculate;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
//        if (entry != null) {
//            this.a.onValueSelect(entry);
//        }

    }

    @Override
    public void onNothingSelected() {
//        this.s.setCenterText(this.G);
        this.s.invalidate();
    }

    public void setPieData(PieData pieData, SpannableString spannableString) {
        if (pieData != null) {
            this.s.setData(pieData);
            this.s.highlightValues(null);
            this.s.setCenterText(spannableString);
            this.s.invalidate();
            this.s.animateY(1400, Easing.EasingOption.EaseInOutQuad);
            return;
        }
        this.s.setData(null);
        this.s.invalidate();
    }

    public void onValueSelect(SpannableString spannableString) {
        this.s.setCenterText(spannableString);
        this.s.invalidate();
    }


    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
//        if (this.I == 0) {
//            this.s.highlightValue((float) i, 0);
//        } else {
//            this.a.handlePieFloatList((LineChartNode) this.v.getItem(i));
//        }
    }


    public void dismissPieFloat() {
//        if (this.C != -1) {
//            this.C = -1;
//            this.B.rightOutAnimation();
//            this.s.highlightValue(null, false);
//            this.s.setCenterText(this.G);
//            this.s.invalidate();
//            if (this.I == 0) {
//                this.u.setStatus(0, -1);
//            } else {
//                this.v.setStatus(0, -1);
//            }
//        }
    }


    public void onDismiss() {
//        if (this.B.getVisibility() == 0) {
//            dismissPieFloat();
//        }
    }

}
