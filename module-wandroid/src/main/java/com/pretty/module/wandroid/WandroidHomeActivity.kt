package com.pretty.module.wandroid

import android.view.Menu
import android.view.MenuItem
import com.pretty.core.base.BaseSimpleActivity
import com.pretty.core.router.RC
import com.pretty.core.router.service.AccountService
import com.pretty.core.util.runOnMainThread
import com.pretty.core.util.showToast
import com.sankuai.waimai.router.Router
import com.sankuai.waimai.router.annotation.RouterUri
import kotlinx.android.synthetic.main.a_wandroid_home.*


@RouterUri(path = [RC.WANDROID_HOME_ACTIVITY], exported = true)
class WandroidHomeActivity : BaseSimpleActivity() {

    override var injectStatePage: Boolean = true

    // 使用ServiceLoader获取用户管理服务
    private val accountService by lazy {
        Router.getService(AccountService::class.java, RC.ACCOUNT_SERVICE)
    }

    override val mLayoutId: Int = R.layout.a_wandroid_home

    override fun initPage() {
        // 跳转登录页
        btn_login.setOnClickListener {
            Router.startUri(this, RC.WANDROID_LOGIN_ACTIVITY)
        }
        // 通过登录拦截器跳转收藏页面
        btn_collect.setOnClickListener {
            Router.startUri(this, RC.WANDROID_COLLECT_ACTIVITY)
        }

        mDisplayDelegate.showLoading()
        runOnMainThread(1000) {
            mDisplayDelegate.showContent()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_loading -> mDisplayDelegate.showLoading()
            R.id.menu_content -> mDisplayDelegate.showContent()
            R.id.menu_error -> mDisplayDelegate.showError()
            R.id.menu_empty -> mDisplayDelegate.showEmpty()
        }
        return true
    }

    override fun retry() {
        showToast("重新加载")
    }
}