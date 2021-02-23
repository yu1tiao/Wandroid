package com.pretty.core.widget.prefs

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.pretty.core.R
import com.pretty.core.ext.dp

/**
 * 菜单分组
 */
class PrefsMenuGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var title: String

    init {
        orientation = VERTICAL

        val array = context.obtainStyledAttributes(attrs, R.styleable.PrefsMenuGroup)
        title = array.getString(R.styleable.PrefsMenuGroup_groupTitle).orEmpty()
        array.recycle()

        val titleView = if (TextUtils.isEmpty(title)) {
            View(context).apply {
                setBackgroundColor(Color.parseColor("#ececec"))
                layoutParams = LayoutParams(-1, 8.dp())
            }
        } else {
            TextView(context).apply {
                text = title
                layoutParams = LayoutParams(-1, -2)

                val paddingH = 10.dp()
                val paddingV = 5.dp()
                setPadding(paddingH, paddingV, paddingH, paddingV)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                setBackgroundColor(Color.parseColor("#ececec"))
                setTextColor(Color.parseColor("#666666"))
            }
        }

        addView(titleView)
    }
}