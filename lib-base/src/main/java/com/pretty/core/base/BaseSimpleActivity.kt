package com.pretty.core.base

import androidx.activity.viewModels

/**
 * 简易activity，没有ViewModel的情况继承
 * @author yu
 * @date 2018/10/29
 */
abstract class BaseSimpleActivity : BaseActivity<BaseViewModel>() {
    override val mViewModel by viewModels<BaseViewModel>()

    override fun subscribeLiveData() {}
}