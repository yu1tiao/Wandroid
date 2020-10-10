package com.pretty.module.wandroid

import com.pretty.core.arch.container.launchFragmentInContainer
import com.pretty.core.base.BaseSimpleActivity
import com.pretty.core.router.RC
import com.pretty.core.util.showToast
import com.pretty.module.wandroid.gank.GankHomeFragment
import com.pretty.module.wandroid.gank.GankTodayFragment
import com.sankuai.waimai.router.Router
import com.sankuai.waimai.router.annotation.RouterUri
import kotlinx.android.synthetic.main.a_wandroid_home.*


@RouterUri(path = [RC.WANDROID_HOME_ACTIVITY], exported = true)
class WandroidHomeActivity : BaseSimpleActivity() {

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
        btn_gank.setOnClickListener {
            launchFragmentInContainer(this, GankTodayFragment::class.java)
        }
    }

    override fun retry() {
        showToast("重新加载")
    }
}