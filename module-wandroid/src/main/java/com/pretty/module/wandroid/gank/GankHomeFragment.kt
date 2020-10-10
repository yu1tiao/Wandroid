package com.pretty.module.wandroid.gank

import androidx.fragment.app.viewModels
import com.pretty.core.base.BaseFragment
import com.pretty.core.base.BaseViewModel

class GankHomeFragment : BaseFragment<BaseViewModel>() {

    override val mLayoutId: Int = com.pretty.module.wandroid.R.layout.f_gank_home
    override val mViewModel: BaseViewModel by viewModels()
}