package com.litchiny.dailylife.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.litchiny.dailylife.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Litchiny on 2018/12/23.
 */

public class FlowLayout extends View {
    private static final String TAG = "FlowLayout";
    private Paint mPaint;
    private List<String> textList = new ArrayList<>();
    private Map<String, Integer> dataList = new HashMap<>();
    private int minPadding = 80;
    private int textPadding = 20;
    private float textSize = 30;
    private int textPace = 35;                                //两个item的间隔
    private int textHeight = 40;
    private int distanceW, distanceH;
    private List<int[]> pointList = new ArrayList<>();
    private int clickIndex = -1;
    private long touchDownTime;

    public boolean isLongClick() {
        return isLongClick;
    }

    private boolean isLongClick;
    private int textNormalColor;
    private int textClickColor;
    private boolean isShowEndAdd;

    private FlowClickListener listener;
    private float downY;
    private boolean paddingHasChanged;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        textNormalColor = a.getColor(R.styleable.FlowLayout_flow_text_normal_color, Color.BLUE);
        textClickColor = a.getColor(R.styleable.FlowLayout_flow_text_click_color, Color.WHITE);
        textSize = a.getDimension(R.styleable.FlowLayout_flow_text_size, context.getResources().getDimension(R.dimen.dimen_size_15));
        initPaint();
    }

    public void setData(List<String> textList) {
        setData(textList, false, -1);
    }

    public void setData(List<String> textList, boolean isShowEndAdd, int clickIndex) {
        this.isShowEndAdd = isShowEndAdd;
        this.clickIndex = clickIndex;
        this.textList.clear();
        this.dataList.clear();
        int i = 0;
        for (String str : textList) {
            if (TextUtils.isEmpty(str))
                continue;
            this.dataList.put(str, i);
            this.textList.add(str);
            i++;
        }
        postInvalidate();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setTextSize(textSize);
        Rect rect = new Rect();
        mPaint.getTextBounds("1", 0, "1".length(), rect);
        textHeight = rect.height();
        textPadding = textHeight;
        textPace = textHeight * 3 / 2;
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (textList.size() <= 0)
            return;
        int startTextTop = minPadding;
        int startTextLeft;
        int totalTextWidth = 0;
        pointList.clear();
        mPaint.setTextSize(textSize);
        for (int i = 0; i < textList.size(); i++) {
            mPaint.setTextAlign(Paint.Align.LEFT);
            float width = mPaint.measureText(textList.get(i));
            int lastWidth = totalTextWidth;
            totalTextWidth += (width + textPadding * 2 + textPace);
            if (totalTextWidth + textHeight * 2 > distanceW) {  //判断width 是否够
                totalTextWidth = (int) (width + textPadding * 2 + textPace);
                startTextLeft = textHeight * 2;
                startTextTop += textHeight + textPadding * 2 + textPace;
            } else {
                startTextLeft = lastWidth + textHeight * 2;
            }

            mPaint.setColor(textNormalColor);
            boolean isShowClickType = i == clickIndex && null != listener && (!isShowEndAdd || i != textList.size() - 1) && !paddingHasChanged;
            mPaint.setStyle(isShowClickType ? Paint.Style.FILL : Paint.Style.STROKE);
            int[] array = {startTextLeft - textPadding, startTextTop - textPadding - textHeight,
                    (int) (startTextLeft + width + textPadding), startTextTop + textPadding};
            canvas.drawRect(array[0], array[1], array[2], array[3], mPaint);
            mPaint.setColor(isShowClickType ? textClickColor : textNormalColor);
            canvas.drawText(textList.get(i), startTextLeft, startTextTop, mPaint);
            if (isLongClick && null != listener && (!isShowEndAdd || i != textList.size() - 1) && !paddingHasChanged) {
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                mPaint.setColor(textClickColor);
                canvas.drawCircle(startTextLeft + width + textPadding, startTextTop - textPadding * 2, textPadding, mPaint);
                mPaint.setColor(textNormalColor);
                mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(startTextLeft + width + textPadding, startTextTop - textPadding * 2, textPadding, mPaint);
                mPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("x", startTextLeft + width + textPadding, startTextTop - textPadding * 2 + textHeight / 2, mPaint);
                int part = textPadding + 5;
                array[1] -= part;
                array[2] += part;
                array[0] = array[2] - part * 2;
                array[3] = array[1] + part * 2;
            }
            pointList.add(array);
        }
    }

    public void clearDeleteType() {
        clickIndex = -1;
        isLongClick = false;
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int resultWidth = sizeWidth;
        int resultHeight = sizeHeight;
        // 考虑内边距对尺寸的影响
        resultWidth += getPaddingLeft() + getPaddingRight();
        resultHeight += getPaddingTop() + getPaddingBottom();
        // 考虑父容器对尺寸的影响
        distanceW = resultWidth = resolveMeasure(sizeWidth, resultWidth);
        distanceH = resultHeight = resolveMeasure(sizeHeight, resultHeight);
        Log.d(TAG, "onMeasure: sizeHeight: " + sizeHeight + ",resultHeight: " + resultHeight);
        setMeasuredDimension(resultWidth, resultHeight);
    }

    /**
     * 根据传入的值进行测量
     */
    public int resolveMeasure(int measureSpec, int defaultSize) {
        int result = 0;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                //设置warp_content时设置默认值
                result = Math.min(specSize, defaultSize);
                break;
            default:
                result = defaultSize;
        }
//        Log.d(TAG, "resolveMeasure: " + MeasureSpec.getMode(measureSpec)
//                + ",defaultSize: " + defaultSize+",specSize: "+ specSize);
        return result;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (paddingHasChanged) {
                    postInvalidate();
                    paddingHasChanged = false;
                }
                if (System.currentTimeMillis() - touchDownTime > 1000) {
                    if (null != listener) {
                        clickIndex = -1;
                        isLongClick = !isLongClick;
                        postInvalidate();
                    }
                } else {
                    int i = 0;
                    float x = event.getX();
                    float y = event.getY();
                    for (int[] arr : pointList) {
                        if (x >= arr[0] && x <= arr[2] && y >= arr[1] && y <= arr[3]) {
                            clickIndex = i;
                            break;
                        }
                        i++;
                    }

                    if (isLongClick && clickIndex >= 0) {
                        if (null != listener) {
                            listener.setOnClickLongItemListener(dataList.get(textList.get(clickIndex)));

                        }
                    }
                    if (clickIndex >= 0 && null != listener) {
                        listener.setOnClickItemListener(dataList.get(textList.get(clickIndex)));
                        postInvalidate();
                    }
                }

                break;

            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                paddingHasChanged = false;
                touchDownTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                minPadding += event.getY() - downY;
                if (Math.abs(minPadding) < 30)
                    return false;
                paddingHasChanged = true;

                minPadding = minPadding < 0 ? 0 : minPadding > distanceH / 2 ? distanceH / 2 : minPadding;
                postInvalidate();
//                Log.d(TAG, "onTouchEvent: event.getY(): " + event.getY() + ",downY: " + downY);
                Log.d(TAG, "onTouchEvent: minPadding: " + minPadding + ",distanceH: " + distanceH);
                break;
        }
        return true;
    }

    public void removeItem(int index){
        textList.remove(index);
        clickIndex = -1;
        postInvalidate();
    }

    public void setFlowClickListener(FlowClickListener clickListener) {
        this.listener = clickListener;
    }

    public interface FlowClickListener {
        void setOnClickItemListener(int index);

        void setOnClickLongItemListener(int index);
    }

}
