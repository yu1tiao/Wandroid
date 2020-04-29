package com.pretty.core.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.pretty.core.R;


public class TitleBar extends ConstraintLayout {

    private ImageView ivLeft;
    private TextView tvTitle;
    private TextView tvRightTitle;
    private ImageView ivRight;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.l_default_title_bar, this, true);
        ivLeft = findViewById(R.id.ivLeft);
        tvTitle = findViewById(R.id.tvTitle);
        tvRightTitle = findViewById(R.id.tvRightText);
        ivRight = findViewById(R.id.ivRight);
    }

    /**
     * @param title 标题
     */
    public TitleBar setTitleText(String title) {
        tvTitle.setText(title);
        return this;
    }

    public TitleBar setTitleTextColor(@ColorInt int color) {
        tvTitle.setTextColor(color);
        return this;
    }

    public TitleBar setTitleTextSize(float textSize) {
        tvTitle.setTextSize(textSize);
        return this;
    }


    public TitleBar setLeftImageBtnListener(OnClickListener listener) {
        ivLeft.setOnClickListener(listener);
        return this;
    }

    public TitleBar setLeftImageBtn(@DrawableRes int resId, OnClickListener listener) {
        ivLeft.setImageResource(resId);
        ivLeft.setOnClickListener(listener);
        return this;
    }

    public TitleBar setLeftImageBtnVisibility(boolean visible) {
        ivLeft.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * @param other    右边btn文字
     * @param listener 监听器
     */
    public TitleBar setRightTextBtn(String other, OnClickListener listener) {
        if (!TextUtils.isEmpty(other)) {
            tvRightTitle.setText(other);
            tvRightTitle.setVisibility(View.VISIBLE);
            tvRightTitle.setOnClickListener(listener);
        }
        return this;
    }

    public TitleBar setRightTextColor(@ColorInt int color) {
        tvRightTitle.setTextColor(color);
        return this;
    }

    /**
     * 设置titleBar背景颜色
     */
    public TitleBar setTitleBarBgColor(@ColorInt int color) {
        setBackgroundColor(color);
        return this;
    }

    /**
     * @param iconId   右边图形按钮id
     * @param listener 监听器
     */
    public TitleBar setRightImageBtn(@DrawableRes int iconId, OnClickListener listener) {
        ivRight.setImageResource(iconId);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setOnClickListener(listener);
        return this;
    }
}
