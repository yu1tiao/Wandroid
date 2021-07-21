package com.pretty.module.wandroid

import com.pretty.core.base.BaseSimpleActivity
import com.pretty.core.ext.throttleClick
import com.pretty.core.router.RC
import com.pretty.module.wandroid.databinding.AWandroidHomeBinding
import com.sankuai.waimai.router.Router
import com.sankuai.waimai.router.annotation.RouterUri


@RouterUri(path = [RC.WANDROID_HOME_ACTIVITY], exported = true)
class WandroidHomeActivity : BaseSimpleActivity<AWandroidHomeBinding>() {

    override fun initBinding(): AWandroidHomeBinding {
        return AWandroidHomeBinding.inflate(layoutInflater)
    }

    override fun initView() {
        // 跳转登录页
        mBinding.btnLogin.throttleClick {
            Router.startUri(this, RC.WANDROID_LOGIN_ACTIVITY)
        }
        // 通过登录拦截器跳转收藏页面
        mBinding.btnCollect.throttleClick {
            Router.startUri(this, RC.WANDROID_COLLECT_ACTIVITY)
        }
    }

}