package com.pretty.core.arch.state

import android.view.View
import android.view.ViewGroup

/**
 * Copyright (c) 2021 北京嗨学网教育科技股份有限公司 All rights reserved.
 *
 * @author yuli
 * @date 2021/4/10
 * @description StatePageManager
 *
 *
    val statePage = StatePageManager.getDefault().wrap(this)
    2, 或者自定义配置
    StatePageManager.with {
    // 会copy全局设置，修改需要自定义的选项就可以
    }.wrap(this)
 */
class StatePageManager(private val config: StatePageConfig) {

    /**
     * 包装普通的view，切换状态时自动添加adapter中的loading、error等布局
     */
    fun wrap(view: View): StatePage {
        val wrapper = StatePage(view.context)
        wrapper.config = this.config

        // 从parent中移除view
        if (view.parent != null) {
            val parent = view.parent as ViewGroup
            val index = parent.indexOfChild(view)
            parent.removeView(view)
            parent.addView(wrapper, index, view.layoutParams)
        }

        wrapper.addView(view, 0)

        return wrapper
    }

    companion object {

        private var defaultConfig: StatePageConfig? = null

        private val defaultInstance: StatePageManager by lazy {
            requireNotNull(defaultConfig) {
                "u must call 'initDefault' before use, 请在GlobalConfig中配置StatePageConfig"
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