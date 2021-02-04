package com.pretty.module.wandroid

import androidx.activity.viewModels
import com.pretty.core.arch.container.launchFragmentInContainer
import com.pretty.core.base.BaseDataBindActivity
import com.pretty.core.base.BaseViewModel
import com.pretty.core.ext.throttleClick
import com.pretty.core.router.RC
import com.pretty.module.wandroid.databinding.AWandroidHomeBinding
import com.pretty.module.wandroid.gank.GankHomeFragment
import com.sankuai.waimai.router.Router
import com.sankuai.waimai.router.annotation.RouterUri


@RouterUri(path = [RC.WANDROID_HOME_ACTIVITY], exported = true)
class WandroidHomeActivity : BaseDataBindActivity<AWandroidHomeBinding, BaseViewModel>() {

    override val mLayoutId: Int = R.layout.a_wandroid_home

    override val mViewModel: BaseViewModel by viewModels()

    override fun initPage() {
        // 跳转登录页
        mBinding.btnLogin.throttleClick {
            Router.startUri(this, RC.WANDROID_LOGIN_ACTIVITY)
        }
        // 通过登录拦截器跳转收藏页面
        mBinding.btnCollect.throttleClick {
            Router.startUri(this, RC.WANDROID_COLLECT_ACTIVITY)
        }
        mBinding.btnGank.throttleClick {
            launchFragmentInContainer(this, GankHomeFragment::class.java)
        }
    }

}