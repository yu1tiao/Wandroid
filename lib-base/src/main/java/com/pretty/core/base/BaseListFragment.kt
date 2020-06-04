package com.pretty.core.base

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pretty.core.ext.observe
import kotlinx.android.synthetic.main.l_common_recyclerview_page.*
import me.yuu.liteadapter.core.LiteAdapterEx

/**
 * @author yu
 * @date 2019/2/27
 */
abstract class BaseListFragment<D, VM : BaseListViewModel<D>> : BaseFragment<VM>() {

    protected lateinit var mAdapter: LiteAdapterEx<D>

    override val mLayoutId: Int = com.pretty.core.R.layout.l_common_recyclerview_page

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.run {
            observe(loadCompleted) {
                refreshLayout.isRefreshing = false
                mAdapter.loadMoreCompleted()
            }
            observe(noMore) { mAdapter.noMore() }
            observe(loadMoreDataSet) { mAdapter.addAll(it) }
            observe(dataSet) { mAdapter.updateData(it) }
            observe(loadMoreError) { mAdapter.loadMoreError() }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = createAdapter()
        initRecyclerView(recyclerView, mAdapter, getLayoutManager())
        refreshLayout.setOnRefreshListener { mViewModel.refresh() }
        mViewModel.refresh()
    }

    protected open fun initRecyclerView(
        recyclerView: RecyclerView,
        adapter: LiteAdapterEx<D>,
        layoutManager: RecyclerView.LayoutManager
    ) {
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    protected open fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(activity)
    }

    protected abstract fun createAdapter(): LiteAdapterEx<D>

}