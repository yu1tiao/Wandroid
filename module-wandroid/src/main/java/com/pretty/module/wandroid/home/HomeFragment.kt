package com.pretty.module.wandroid.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.pretty.core.base.BaseDataBindFragment
import com.pretty.core.base.BaseViewModel
import com.pretty.module.wandroid.databinding.FHomeBinding

/**
 * Copyright (c) 2021 北京嗨学网教育科技股份有限公司 All rights reserved.
 *
 * @author yuli
 * @date 2021/5/20
 * @description HomeFragment
 */
class HomeFragment : BaseDataBindFragment<FHomeBinding, BaseViewModel>() {

    override val mViewModel: BaseViewModel by viewModels()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FHomeBinding {
        return FHomeBinding.inflate(inflater, container, false)
    }
}