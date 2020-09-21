package com.pretty.core.arch.state

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

/**
 *
 * 1, 获取全局配置的
    val statePage = StatePageManager.getDefault().wrap(this)
    StatePageManager.with {
        // 会copy全局设置，修改需要自定义的选项就可以
    }.wrap(this)
 *
 *
 */
class StatePageManager(private val config: StatePageConfig) {

    fun wrap(activity: Activity): StatePage {
        val contentView = activity.findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
        return wrap(contentView)
    }

    fun wrap(fragment: Fragment): StatePage {
        val contentView = fragment.view
        requireNotNull(contentView) {
            "请在Fragmnet设置view之后再调用StatePageManager.wrap(fragment)"
        }
        return wrap(contentView)
    }

    /**
     * 包装普通的view，切换状态时自动添加adapter中的loading、error等布局
     */
    fun wrap(view: View): StatePage {
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

        return StatePage(wrapper, config)
    }

    companion object {

        private var defaultConfig: StatePageConfig? = null

        private val defaultInstance: StatePageManager by lazy {
            requireNotNull(defaultConfig) {
                "u must call 'initDefault' before use"
            }
            StatePageManager(defaultConfig!!)
        }

        /**
         * 获取全局配置的StatePageManager
         */
        fun getDefault(): StatePageManager = defaultInstance

        /**
         * 单独设置调用，会拷贝全局设置，只需要修改需要修改的选项就行
         */
        fun with(action: StatePageConfig.() -> Unit): StatePageManager {
            requireNotNull(defaultConfig) {
                "u must call 'initDefault' before use"
            }
            val newConfig = defaultConfig!!.newConfig {
                action(this)
                return@newConfig this
            }
            return StatePageManager(newConfig)
        }

        /**
         * 初始化全局的设置
         */
        fun initDefault(config: StatePageConfig) {
            defaultConfig = config
        }
    }
}