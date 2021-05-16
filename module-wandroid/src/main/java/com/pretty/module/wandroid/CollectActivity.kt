package com.pretty.module.wandroid

import android.view.View
import com.pretty.core.base.BaseSimpleActivity
import com.pretty.core.router.RC
import com.pretty.core.router.interceptor.LoginInterceptor
import com.sankuai.waimai.router.annotation.RouterUri


@RouterUri(
    path = [RC.WANDROID_COLLECT_ACTIVITY],
    interceptors = [LoginInterceptor::class]
)
class CollectActivity : BaseSimpleActivity() {

    override val mLayoutId: Int = R.layout.a_wandroid_collect

    override fun initPage(contentView: View) {
    }

}