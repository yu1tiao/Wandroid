package com.pretty.module.wandroid.gank

import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.pretty.core.base.BaseFragment
import com.pretty.core.ext.dp
import com.pretty.core.ext.observe
import com.pretty.core.ext.toResColor
import com.pretty.module.wandroid.R
import com.pretty.module.wandroid.gank.adapter.GankGirlsAdapter
import kotlinx.android.synthetic.main.f_gank_girls.*

class GankGirlsFragment : BaseFragment<GankGirlsViewModel>() {

    private lateinit var adapter: GankGirlsAdapter
    private var isFirstLoad = true
    override var injectStatePage: Boolean = true
    override val mLayoutId: Int = com.pretty.module.wandroid.R.layout.f_gank_girls
    override val mViewModel: GankGirlsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rl_girls.setOnRefreshListener {
            mViewModel.refresh()
        }
        this.adapter = createAdapter()
        rv_girls.layoutManager = LinearLayoutManager(requireContext())
        rv_girls.adapter = this.adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (isFirstLoad) {
            mDisplayDelegate.showLoading()
            isFirstLoad = false
        }
        mViewModel.refresh()
    }

    private fun createAdapter(): GankGirlsAdapter {
        return GankGirlsAdapter(requireContext()).apply {
            enableLoadMore {
                mViewModel.loadMore()
            }
        }
    }

    override fun subscribeLiveData() {
        super.subscribeLiveData()
        observe(mViewModel.ldGirls) {
            mDisplayDelegate.showContent()
            adapter.updateData(it)
            rl_girls.refreshComplete()
            adapter.loadMoreCompleted()
        }
        observe(mViewModel.ldGirlsLoadMore) {
            adapter.addAll(it)
            adapter.loadMoreCompleted()
        }
        observe(mViewModel.ldNoMore) {
            adapter.noMore()
        }
        observe(mViewModel.ldError) {
            adapter.loadMoreCompleted()
            rl_girls.refreshComplete()
        }
    }
}