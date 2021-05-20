package com.pretty.module.wandroid.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.pretty.core.base.BaseDataBindFragment
import com.pretty.core.base.BaseViewModel
import com.pretty.core.ext.setFragments
import com.pretty.module.wandroid.R
import com.pretty.module.wandroid.databinding.FHomeBinding

/**
 * Copyright (c) 2021 北京嗨学网教育科技股份有限公司 All rights reserved.
 *
 * @author yuli
 * @date 2021/5/20
 * @description HomeFragment
 */
class HomeFragment : BaseDataBindFragment<FHomeBinding, BaseViewModel>() {

    override val mLayoutId: Int = R.layout.f_home
    override val mViewModel: BaseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        mBinding.viewPager.setFragments(childFragmentManager, listOf())
    }

}