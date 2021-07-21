package com.pretty.core.widget

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.widget.AppCompatImageView

/**
 * Copyright (c) 2021 $ All rights reserved.
 *
 * @author matthew
 * @date 2021/7/21
 * @description 旋转的image view，转圈的loading
 */
class RotateImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val anim by lazy {
        RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 700
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        this.animation = anim
    }

    override fun onDetachedFromWindow() {
        this.clearAnimation()
        super.onDetachedFromWindow()
    }

}