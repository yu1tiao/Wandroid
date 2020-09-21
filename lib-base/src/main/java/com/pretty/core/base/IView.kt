package com.pretty.core.base


import com.pretty.core.arch.IDisplayDelegate
import com.pretty.core.arch.IDisposableManager

/**
 * @author yu
 * @date 2018/10/26
 */
interface IView {

    /**
     * 提供基本 UI 交互方法，包括进度条和 toast 等。
     */
    val mDisplayDelegate: IDisplayDelegate

    /**
     * 提供 Disposable 管理的功能，会自动管理生命周期。
     */
    val mDisposableManager: IDisposableManager

}