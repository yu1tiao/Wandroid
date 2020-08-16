package com.pretty.core.arch

import android.content.Context

interface IDisplayDelegate : Destroyable {

    fun init(context: Context)

    /**
     * 显示Loading对话框
     * @param msg 文案。为 `null` 时显示为默认加载文字。
     * @param cancelCallback 取消展示Loading回调
     */
    fun showLoading(
        msg: String? = null,
        needDimBehind: Boolean = false,
        cancelCallback: (() -> Unit)? = null
    )

    /**
     * 关闭Loading对话框
     */
    fun hideLoading()

    /**
     * 更新Loading对话框文字（如果正在展示的话）
     * @param msg 新的 msg
     */
    fun updateLoadingMsg(msg: String)

    fun showTips(text: String?)

    fun getContext(): Context
}