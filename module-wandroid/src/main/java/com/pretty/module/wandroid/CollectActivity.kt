package com.pretty.module.wandroid

import androidx.lifecycle.ViewModel
import com.pretty.core.base.BaseActivity
import com.pretty.core.router.RouterConstant
import com.pretty.core.router.interceptor.LoginInterceptor
import com.sankuai.waimai.router.annotation.RouterUri


@RouterUri(
    path = [RouterConstant.WANDROID_COLLECT_ACTIVITY],
    interceptors = [LoginInterceptor::class]
)
class CollectActivity : BaseActivity() {

    override val mLayoutId: Int = R.layout.a_wandroid_collect

    override fun getViewModel(): ViewModel? {
        return null
    }

    override fun initPage() {
    }

}