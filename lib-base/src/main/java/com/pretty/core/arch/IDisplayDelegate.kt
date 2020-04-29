package com.pretty.core.arch

import android.content.Context
import com.pretty.core.arch.commonpage.ICommonPage

interface IDisplayDelegate : Destroyable {

    fun init(
        context: Context,
        commonPage: ICommonPage? = null
    )

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
    fun dismissLoading()

    /**
     * 更新Loading对话框文字（如果正在展示的话）
     * @param msg 新的 msg
     */
    fun updateLoadingMsg(msg: String)

    fun showTips(text: String?)

    fun showSuccess()

    fun showError(iconRes: Int? = -1, errorText: String? = null)

    fun showEmpty(iconRes: Int? = -1, emptyText: String? = null)

    fun getContext(): Context
}