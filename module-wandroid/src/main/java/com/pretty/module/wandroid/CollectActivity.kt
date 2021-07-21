package com.pretty.module.wandroid

import com.pretty.core.base.BaseSimpleActivity
import com.pretty.core.router.RC
import com.pretty.core.router.interceptor.LoginInterceptor
import com.pretty.module.wandroid.databinding.AWandroidCollectBinding
import com.sankuai.waimai.router.annotation.RouterUri


@RouterUri(
    path = [RC.WANDROID_COLLECT_ACTIVITY],
    interceptors = [LoginInterceptor::class]
)
class CollectActivity : BaseSimpleActivity<AWandroidCollectBinding>() {

    override fun initBinding(): AWandroidCollectBinding {
        return AWandroidCollectBinding.inflate(layoutInflater)
    }
}