package com.example.quizpal.SpinWheel;

import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.example.quizpal.R;
import com.example.quizpal.SpinWheel.model.LuckyItem;

import java.util.List;

/**
 * Created by kiennguyen on 11/5/16.
 */

public class LuckyWheelView extends RelativeLayout implements PielView.PieRotateListener {
    private int mBackgroundColor;
    private int mTextColor;
    private int mTopTextSize;
    private int mSecondaryTextSize;
    private int mBorderColor;
    private int mTopTextPadding;
    private int mEdgeWidth;
    private Drawable mCenterImage;
    private Drawable mCursorImage;

    public PielView pielView;
    private ImageView ivCursorView;

    private LuckyRoundItemSelectedListener mLuckyRoundItemSelectedListener;

    @Override
    public void rotateDone(int index) {
        if (mLuckyRoundItemSelectedListener != null) {
            mLuckyRoundItemSelectedListener.LuckyRoundItemSelected(index);
        }
    }

    public interface LuckyRoundItemSelectedListener {
        void LuckyRoundItemSelected(int index);
    }

    public void setLuckyRoundItemSelectedListener(LuckyRoundItemSelectedListener listener) {
        this.mLuckyRoundItemSelectedListener = listener;
    }

    public LuckyWheelView(Context context) {
        super(context);
        init(context, null);
    }

    public LuckyWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * @param ctx
     * @param attrs
     */
    private void init(Context ctx, AttributeSet attrs) {
        if (attrs != null) {


//            TypedArray typedArray = ctx.obtainStyledAttributes(attrs, com.example.quizpal.R.styleable.LuckyWheelView)


            TypedArray typedArray = ctx.obtainStyledAttributes(attrs, com.example.quizpal.R.styleable.LuckyWheelView);
            mBackgroundColor = typedArray.getColor(com.example.quizpal.R.styleable.LuckyWheelView_lkwBackgroundColor, 0xffcc0000);
            mTopTextSize = typedArray.getDimensionPixelSize(com.example.quizpal.R.styleable.LuckyWheelView_lkwTopTextSize, (int) LuckyWheelUtils.convertDpToPixel(25f, getContext()));
            mSecondaryTextSize = typedArray.getDimensionPixelSize(com.example.quizpal.R.styleable.LuckyWheelView_lkwSecondaryTextSize, (int) LuckyWheelUtils.convertDpToPixel(10f, getContext()));
            mTextColor = typedArray.getColor(com.example.quizpal.R.styleable.LuckyWheelView_lkwTopTextColor, 0);
            mTopTextPadding = typedArray.getDimensionPixelSize(com.example.quizpal.R.styleable.LuckyWheelView_lkwTopTextPadding, (int) LuckyWheelUtils.convertDpToPixel(22f, getContext())) + (int) LuckyWheelUtils.convertDpToPixel(15f, getContext());
            mCursorImage = typedArray.getDrawable(com.example.quizpal.R.styleable.LuckyWheelView_lkwCursor);
            mCenterImage = typedArray.getDrawable(com.example.quizpal.R.styleable.LuckyWheelView_lkwCenterImage);
            mEdgeWidth = typedArray.getInt(com.example.quizpal.R.styleable.LuckyWheelView_lkwEdgeWidth, 10);
            mBorderColor = typedArray.getColor(com.example.quizpal.R.styleable.LuckyWheelView_lkwEdgeColor, 0);
            typedArray.recycle();
        }

        LayoutInflater inflater = LayoutInflater.from(getContext());
        FrameLayout frameLayout = (FrameLayout) inflater.inflate(com.example.quizpal.R.layout.lucky_wheel_layout, this, false);

        pielView = frameLayout.findViewById(com.example.quizpal.R.id.pieView);
        ivCursorView = frameLayout.findViewById(com.example.quizpal.R.id.cursorView);

        pielView.setPieRotateListener(this);
        pielView.setPieBackgroundColor(mBackgroundColor);
        pielView.setTopTextPadding(mTopTextPadding);
        pielView.setTopTextSize(mTopTextSize);
        pielView.setSecondaryTextSizeSize(mSecondaryTextSize);
        pielView.setPieCenterImage(mCenterImage);
        pielView.setBorderColor(mBorderColor);
        pielView.setBorderWidth(mEdgeWidth);


        if (mTextColor != 0)
            pielView.setPieTextColor(mTextColor);

        ivCursorView.setImageDrawable(mCursorImage);

        addView(frameLayout);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //This is to control that the touch events triggered are only going to the PieView
        for (int i = 0; i < getChildCount(); i++) {
            if (isPielView(getChildAt(i))) {
                return super.dispatchTouchEvent(ev);
            }
        }
        return false;
    }

    private boolean isPielView(View view) {
        if (view instanceof ViewGroup) {
            for (int i = 0; i < getChildCount(); i++) {
                if (isPielView(((ViewGroup) view).getChildAt(i))) {
                    return true;
                }
            }
        }
        return view instanceof PielView;
    }

    public void setLuckyWheelBackgrouldColor(int color) {
        pielView.setPieBackgroundColor(color);
    }

    public void setLuckyWheelCursorImage(int drawable) {
        ivCursorView.setBackgroundResource(drawable);
    }

    public void setLuckyWheelCenterImage(Drawable drawable) {
        pielView.setPieCenterImage(drawable);
    }

    public void setBorderColor(int color) {
        pielView.setBorderColor(color);
    }

    public void setLuckyWheelTextColor(int color) {
        pielView.setPieTextColor(color);
    }

    /**
     * @param data
     */
    public void setData(List<LuckyItem> data) {
        pielView.setData(data);
    }






    /**
     * @param numberOfRound
     */
    public void setRound(int numberOfRound) {
        pielView.setRound(numberOfRound);
    }

    /**
     * @param fixedNumber
     */
    public void setPredeterminedNumber(int fixedNumber) {
        pielView.setPredeterminedNumber(fixedNumber);
    }

    public void startLuckyWheelWithTargetIndex(int index) {
        pielView.rotateTo(index);
    }
}
