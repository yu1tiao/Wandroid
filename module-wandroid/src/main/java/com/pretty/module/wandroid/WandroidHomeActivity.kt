package com.pretty.module.wandroid

import com.pretty.core.base.BaseSimpleActivity
import com.pretty.core.router.RouterConstant
import com.pretty.core.router.service.AccountService
import com.sankuai.waimai.router.Router
import com.sankuai.waimai.router.annotation.RouterUri
import kotlinx.android.synthetic.main.a_wandroid_home.*


@RouterUri(path = [RouterConstant.WANDROID_HOME_ACTIVITY])
class WandroidHomeActivity : BaseSimpleActivity() {

    // 使用ServiceLoader获取用户管理服务
    private val accountService by lazy {
        Router.getService(AccountService::class.java, RouterConstant.ACCOUNT_SERVICE)
    }

    override val mLayoutId: Int = R.layout.a_wandroid_home

    override fun initPage() {
        // 跳转登录页
        btn_login.setOnClickListener {
            Router.startUri(this, RouterConstant.WANDROID_LOGIN_ACTIVITY)
        }
        // 通过登录拦截器跳转收藏页面
        btn_collect.setOnClickListener {
            Router.startUri(this, RouterConstant.WANDROID_COLLECT_ACTIVITY)
        }
    }
}