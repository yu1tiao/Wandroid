package com.pretty.core.widget.prefs


import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.pretty.core.R
import com.pretty.core.ext.dp

/**
 * 菜单子项，可结合 [PrefsMenuGroup] 使用。
 */
class PrefsMenuItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var tvSubtitle: TextView
    private var tvTitle: TextView
    private var tvRightText: TextView
    private var imageMenu: ImageView
    private var imageAction: ImageView
    private var switchAction: SwitchCompat

    var subTitle: CharSequence?
        get() = tvSubtitle.text
        set(value) {
            if (!TextUtils.isEmpty(value)) {
                tvSubtitle.visibility = View.VISIBLE
                tvSubtitle.text = value
            } else {
                tvSubtitle.visibility = View.GONE
            }
        }

    var title: CharSequence?
        get() = tvTitle.text
        set(value) {
            tvTitle.text = value
        }
    var rightText: CharSequence?
        get() = tvRightText.text
        set(value) {
            if (TextUtils.isEmpty(value)) {
                tvRightText.visibility = View.GONE
            } else {
                tvRightText.visibility = View.VISIBLE
                tvRightText.text = value
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

        tvSubtitle = findViewById(R.id.tv_subtitle)
        tvTitle = findViewById(R.id.tv_title)
        tvRightText = findViewById(R.id.tv_right_text)
        imageMenu = findViewById(R.id.image_menu_icon)
        imageAction = findViewById(R.id.image_action_indicator)
        switchAction = findViewById(R.id.switch_action)

        if (icon != null) {
            imageMenu.background = icon
        }
        this.title = title
        this.subTitle = subtitle
        this.rightText = rightText

        when (type) {
            0 -> {
                imageAction.visibility = View.GONE
                switchAction.visibility = View.GONE
            }
            1 -> {
                imageAction.visibility = View.VISIBLE
                switchAction.visibility = View.GONE
            }
            2 -> {
                imageAction.visibility = View.GONE
                switchAction.visibility = View.VISIBLE
            }
        }

        array.recycle()
    }

    fun setSwitchChangeListener(listener: (isChecked: Boolean) -> Unit) {
        switchAction.setOnCheckedChangeListener { _, isChecked ->
            listener.invoke(isChecked)
        }
    }

    fun setIcon(iconRes: Int) {
        imageMenu.setImageResource(iconRes)
    }

}