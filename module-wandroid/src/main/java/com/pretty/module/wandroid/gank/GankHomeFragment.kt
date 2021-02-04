package com.pretty.module.wandroid.gank

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import com.pretty.core.base.BaseDataBindFragment
import com.pretty.core.base.BaseViewModel
import com.pretty.module.wandroid.R
import com.pretty.module.wandroid.databinding.FGankHomeBinding

class GankHomeFragment : BaseDataBindFragment<FGankHomeBinding, BaseViewModel>() {

    override val mLayoutId: Int = R.layout.f_gank_home
    override val mViewModel: BaseViewModel by viewModels()
    private val titles = arrayOf("广场", "妹子")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(mBinding.tabLayout) {
            addTab(newTab().setText(titles[0]))
            addTab(newTab().setText(titles[1]))
        }

        mBinding.viewPager.adapter = object : FragmentPagerAdapter(
            childFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            private val fragments = listOf<Fragment>(GankTodayFragment(), GankGirlsFragment())

            override fun getCount(): Int {
                return fragments.size
            }

            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return titles[position]
            }
        }
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
    }
}