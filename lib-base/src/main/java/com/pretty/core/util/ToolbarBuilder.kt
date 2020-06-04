package com.pretty.core.util

import android.app.Activity
import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import com.pretty.core.R
import com.pretty.core.ext.safeCast
import com.pretty.core.ext.toResColor
import com.pretty.core.ext.toResString

class ToolbarBuilder(private val toolbar: Toolbar) {

    companion object {
        fun build(toolbar: View, action: ToolbarBuilder.(Toolbar) -> Unit) {
            toolbar.safeCast<Toolbar> {
                ToolbarBuilder(it).action(it)
            }
        }
    }

    fun setTitle(@StringRes title: Int, @ColorRes colorRes: Int? = -1) {
        setTitle(title.toResString(), colorRes)
    }

    fun setTitle(title: String?, @ColorRes colorRes: Int? = -1) {
        toolbar.title = title
        if (colorRes != -1) {
            toolbar.setTitleTextColor(colorRes!!.toResColor())
        }
    }


    /** 设置返回按钮 */
    fun setDefaultNavigationBtn(
        activity: Activity,
        iconRes: Int = R.drawable.ic_default_navigation_btn
    ) {
        setNavigationBtn(iconRes) {
            activity.onBackPressed()
        }
    }

    fun setCenterTitle(
        @StringRes textResId: Int,
        textSize: Float = -1F,
        @ColorRes colorRes: Int = -1,
        textStyle: Int = Typeface.NORMAL
    ) {
        setCenterTitle(textResId.toResString(), textSize, colorRes, textStyle)
    }

    fun setCenterTitle(
        title: String?,
        textSize: Float = -1F,
        @ColorRes colorRes: Int = -1,
        textStyle: Int = Typeface.NORMAL
    ) {
        toolbar.findViewById<TextView>(R.id.toolbar_title)?.apply {
            if (!title.isNullOrEmpty()) {
                setText(title)
            }
            if (textSize != -1F) {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
            }
            if (colorRes != -1) {
                setTextColor(colorRes.toResColor())
            }
            setTypeface(null, textStyle)
        }
    }

    fun setRightBtn(
        @StringRes textResId: Int,
        @ColorRes colorRes: Int = -1,
        textSize: Float = -1F,
        textStyle: Int = Typeface.NORMAL,
        onClickListener: ((View) -> Unit)? = null
    ) {
        setRightBtn(textResId.toResString(), colorRes, textSize, textStyle, onClickListener)
    }

    fun setRightBtn(
        text: String?,
        @ColorRes colorRes: Int = -1,
        textSize: Float = -1F,
        textStyle: Int = Typeface.NORMAL,
        onClickListener: ((View) -> Unit)? = null
    ) {
        toolbar.findViewById<TextView>(R.id.toolbar_right_tv)?.apply {
            if (!text.isNullOrEmpty()) {
                setText(text)
            }
            if (textSize != -1F) {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
            }
            if (colorRes != -1) {
                setTextColor(colorRes.toResColor())
            }
            setTypeface(null, textStyle)
            setOnClickListener {
                onClickListener?.invoke(it)
            }
        }
    }

    fun setRightImageBtn(@DrawableRes iconRes: Int, onClickListener: ((View) -> Unit)? = null) {
        toolbar.findViewById<ImageView>(R.id.toolbar_right_iv)?.apply {
            setImageResource(iconRes)
            setOnClickListener {
                onClickListener?.invoke(it)
            }
        }
    }

    fun setNavigationBtn(@DrawableRes iconRes: Int, onClickListener: (View) -> Unit) {
        toolbar.setNavigationIcon(iconRes)
        toolbar.setNavigationOnClickListener {
            onClickListener(it)
        }
    }
}