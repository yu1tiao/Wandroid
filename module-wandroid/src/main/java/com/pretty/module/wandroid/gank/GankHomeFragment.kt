package com.pretty.module.wandroid.gank

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pretty.core.base.BaseDataBindFragment
import com.pretty.core.base.BaseViewModel
import com.pretty.core.ext.setFragments
import com.pretty.module.wandroid.R
import com.pretty.module.wandroid.databinding.FGankHomeBinding

class GankHomeFragment : BaseDataBindFragment<FGankHomeBinding, BaseViewModel>() {

    override val mLayoutId: Int = R.layout.f_gank_home
    override val mViewModel: BaseViewModel by viewModels()
    private val titles = arrayOf("广场", "妹子")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(mBinding) {
            tabLayout.addTab(tabLayout.newTab().setText(titles[0]))
            tabLayout.addTab(tabLayout.newTab().setText(titles[1]))

            viewPager.setFragments(
                childFragmentManager,
                listOf<Fragment>(GankTodayFragment(), GankGirlsFragment()),
                titles
            )
            tabLayout.setupWithViewPager(mBinding.viewPager)
        }
    }
}