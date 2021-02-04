package com.pretty.module.wandroid.gank

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pretty.core.base.BaseDataBindFragment
import com.pretty.core.ext.observe
import com.pretty.module.wandroid.R
import com.pretty.module.wandroid.databinding.FGankGirlsBinding
import com.pretty.module.wandroid.gank.adapter.GankGirlsAdapter

class GankGirlsFragment : BaseDataBindFragment<FGankGirlsBinding, GankGirlsViewModel>() {

    private lateinit var adapter: GankGirlsAdapter
    override val mLayoutId: Int = R.layout.f_gank_girls
    override val mViewModel: GankGirlsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.rlGirls.setOnRefreshListener {
            mViewModel.refresh()
        }
        this.adapter = createAdapter()
        mBinding.rvGirls.layoutManager = LinearLayoutManager(requireContext())
        mBinding.rvGirls.adapter = this.adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
            adapter.updateData(it)
            mBinding.rlGirls.refreshComplete()
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
            mBinding.rlGirls.refreshComplete()
        }
    }
}