package com.pretty.core.arch.state

/**
 * Copyright (c) 2021 北京嗨学网教育科技股份有限公司 All rights reserved.
 *
 * @author yuli
 * @date 2021/4/10
 * @description IStatePage
 */
interface IStatePage {
    fun showLoading()
    fun showError()
    fun showEmpty()
    fun showContent()
}