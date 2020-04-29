package com.pretty.core.arch.commonpage

import android.R
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * 管理页面loading、success、error、empty状态
 */
class CommonPageManager private constructor(private var mAdapter: CommonPageFactory) {

    fun wrap(activity: Activity): CommonPage {
        val contentView = activity.findViewById<ViewGroup>(R.id.content)
        return wrap(contentView)
    }

    /**
     * 包装普通的view，切换状态时自动添加adapter中的loading、error等布局
     */
    fun wrap(view: View): CommonPage {
        var wrapper: ViewGroup? = null

        // 如果当前view的parent是ConstraintLayout、FrameLayout或RelativeLayout，并且有且只有自己一个孩子，
        // 自己宽高还是MATCH_PARENT的，就可以把loading、error、empty布局加在parent中，减少布局层级
        if (view.parent != null) {
            val parent = view.parent as ViewGroup
            if (parent.childCount == 1) {
                val lp = view.layoutParams
                if (lp != null && lp.width == -1 && lp.height == -1) {
                    when (parent) {
                        is ConstraintLayout,
                        is FrameLayout,
                        is RelativeLayout -> wrapper = parent
                    }
                }
            }
        }

        if (wrapper == null) {
            // 当前的view作为content，用FrameLayout包裹后返回
            wrapper = FrameLayout(view.context)

            val lp = view.layoutParams
            if (lp != null) {
                wrapper.layoutParams = lp
            }

            if (view.parent != null) {
                val parent = view.parent as ViewGroup
                val index = parent.indexOfChild(view)
                parent.removeView(view)
                parent.addView(wrapper, index)
            }

            val newLp = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            wrapper.addView(view, newLp)
        }

        return CommonPage(mAdapter, view.context, wrapper)
    }

    companion object {

        private var defaultAdapter: CommonPageFactory? = null

        private val defaultInstance: CommonPageManager by lazy {
            if (defaultAdapter == null) throw NullPointerException("u must call 'initDefault' before use")
            CommonPageManager(defaultAdapter!!)
        }

        fun getDefault(): CommonPageManager = defaultInstance

        fun with(factory: CommonPageFactory): CommonPageManager {
            return CommonPageManager(factory)
        }

        fun initDefault(adapter: CommonPageFactory) {
            defaultAdapter = adapter
        }
    }
}