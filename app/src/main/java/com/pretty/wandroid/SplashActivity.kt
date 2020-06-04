package com.pretty.wandroid

import com.pretty.core.base.BaseSimpleActivity
import com.pretty.core.router.RouterConstant
import com.sankuai.waimai.router.Router
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseSimpleActivity() {

    override val mLayoutId: Int = R.layout.activity_splash

    override fun initPage() {
        btn_wandroid.setOnClickListener {
            Router.startUri(this, RouterConstant.WANDROID_HOME_ACTIVITY)
        }
    }

}
