package com.pretty.core.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.l_common_recyclerview_page.*
import me.yuu.liteadapter.core.LiteAdapterEx

/**
 * @author yu
 * @date 2019/2/27
 */
abstract class BaseListFragment<D, VM : BaseListViewModel<D>> : BaseFragment<ViewDataBinding>() {

    protected lateinit var mAdapter: LiteAdapterEx<D>

    override val mLayoutId: Int = com.pretty.core.R.layout.l_common_recyclerview_page

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getListViewModel().run {
            loadCompleted.observe(this@BaseListFragment, Observer {
                refreshLayout.isRefreshing = false
                mAdapter.loadMoreCompleted()
            })
            noMore.observe(this@BaseListFragment, Observer { mAdapter.noMore() })
            loadMoreDataSet.observe(this@BaseListFragment, Observer { mAdapter.addAll(it!!) })
            dataSet.observe(this@BaseListFragment, Observer { mAdapter.updateData(it!!) })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = createAdapter()
        initRecyclerView(recyclerView, mAdapter, getLayoutManager())

        refreshLayout.setOnRefreshListener { getListViewModel().refresh() }

        getListViewModel().refresh()
    }

    protected open fun initRecyclerView(
        recyclerView: RecyclerView,
        adapter: LiteAdapterEx<D>,
        layoutManager: RecyclerView.LayoutManager
    ) {
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun getViewModel(): ViewModel? {
        return getListViewModel()
    }

    protected abstract fun getListViewModel(): VM

    protected open fun getLayoutManager(): RecyclerView.LayoutManager =
        LinearLayoutManager(activity)

    protected abstract fun createAdapter(): LiteAdapterEx<D>

}