package com.pretty.core.widget.prefs


import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.pretty.core.R
import com.pretty.core.ext.dp
import kotlinx.android.synthetic.main.v_prefs_menu_item.view.*

/**
 * 菜单子项，可结合 [PrefsMenuGroup] 使用。
 */
class PrefsMenuItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var subTitle: CharSequence?
        get() = tv_subtitle?.text
        set(value) {
            if (!TextUtils.isEmpty(value)) {
                tv_subtitle.visibility = View.VISIBLE
                tv_subtitle?.text = value
            } else {
                tv_subtitle.visibility = View.GONE
            }
        }

    var title: CharSequence?
        get() = tv_title?.text
        set(value) {
            tv_title.text = value
        }
    var rightText: CharSequence?
        get() = tv_right_text?.text
        set(value) {
            if (TextUtils.isEmpty(value)) {
                tv_right_text.visibility = View.GONE
            } else {
                tv_right_text.visibility = View.VISIBLE
                tv_right_text.text = value
            }
        }

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        isClickable = true

        val padding = 14.dp().toInt()
        setPadding(padding, 0, padding, 0)

        LayoutInflater.from(context).inflate(R.layout.v_prefs_menu_item, this)
        val array = context.obtainStyledAttributes(attrs, R.styleable.PrefsMenuItem)
        val title = array.getString(R.styleable.PrefsMenuItem_itemTitle)
        val subtitle = array.getString(R.styleable.PrefsMenuItem_subTitle)
        val rightText = array.getString(R.styleable.PrefsMenuItem_rightText)
        val type = array.getInt(R.styleable.PrefsMenuItem_itemType, 0)
        val icon = array.getDrawable(R.styleable.PrefsMenuItem_icon)

        if (icon != null) {
            image_menu_icon.background = icon
        }
        this.title = title
        this.subTitle = subtitle
        this.rightText = rightText

        when (type) {
            0 -> {
                image_action_indicator.visibility = View.GONE
                switch_action.visibility = View.GONE
            }
            1 -> {
                image_action_indicator.visibility = View.VISIBLE
                switch_action.visibility = View.GONE
            }
            2 -> {
                image_action_indicator.visibility = View.GONE
                switch_action.visibility = View.VISIBLE
            }
        }

        array.recycle()
    }

    fun setSwitchChangeListener(listener: (isChecked: Boolean) -> Unit) {
        switch_action.setOnCheckedChangeListener { _, isChecked ->
            listener.invoke(isChecked)
        }
    }

    fun setIcon(iconRes: Int) {
        image_menu_icon.setImageResource(iconRes)
    }

}