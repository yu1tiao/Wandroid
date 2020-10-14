package com.pretty.module.wandroid.gank

import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pretty.core.arch.state.StatePage
import com.pretty.core.arch.state.StatePageManager
import com.pretty.core.base.BaseFragment
import com.pretty.core.ext.dp
import com.pretty.core.ext.observe
import com.pretty.core.ext.toResColor
import com.pretty.core.util.runOnMainThread
import com.pretty.module.wandroid.R
import com.pretty.module.wandroid.gank.adapter.GankTodayAdapter
import com.pretty.module.wandroid.gank.widget.GankBannerView
import com.pretty.module.wandroid.gank.widget.GankCategoryView
import kotlinx.android.synthetic.main.f_gank_girls.*
import kotlinx.android.synthetic.main.f_gank_today.*

class GankTodayFragment : BaseFragment<GankTodayViewModel>() {

    private lateinit var adapter: GankTodayAdapter
    private lateinit var banner: GankBannerView
    private lateinit var category: GankCategoryView
    private lateinit var statePage: StatePage

    override val mLayoutId: Int = com.pretty.module.wandroid.R.layout.f_gank_today
    override val mViewModel: GankTodayViewModel by viewModels()

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): View {
        val view = super.inflateView(inflater, container)
        statePage = StatePageManager.getDefault().wrap(view)
        return statePage
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rl_today.setOnRefreshListener {
            mViewModel.getBanner()
            mViewModel.getCategory()
            mViewModel.getHot()
        }
        this.adapter = createAdapter()
        rv_today.layoutManager = LinearLayoutManager(requireContext())
        rv_today.adapter = this.adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        statePage.showLoading()
        mViewModel.getBanner()
        mViewModel.getCategory()
        mViewModel.getHot()
    }

    private fun createAdapter(): GankTodayAdapter {
        banner = GankBannerView(requireContext())
        category = GankCategoryView(requireContext())
        return GankTodayAdapter(requireContext()).apply {
            addHeader(banner)
            addHeader(category)
            addFooter(TextView(context).apply {
                text = "我是有底线的"
                gravity = Gravity.CENTER
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                setTextColor(R.color.gray400.toResColor())
                layoutParams = ViewGroup.LayoutParams(-1, 50.dp().toInt())
            })
        }
    }

    override fun subscribeLiveData() {
        super.subscribeLiveData()
        observe(mViewModel.ldBanner) {
            banner.setBanner(this, it)
            statePage.showContent()
            rl_today.refreshComplete()
        }
        observe(mViewModel.ldCategory) {
            category.bindData(it)
            statePage.showContent()
            rl_today.refreshComplete()
        }
        observe(mViewModel.ldHot) {
            adapter.updateData(it)
            statePage.showContent()
            rl_today.refreshComplete()
        }
        observe(mViewModel.ldError) {
            adapter.loadMoreCompleted()
            rl_today.refreshComplete()
        }
    }

}