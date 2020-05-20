package com.pretty.wandroid

import androidx.lifecycle.ViewModel
import com.pretty.core.base.BaseActivity
import com.pretty.core.router.RouterConstant
import com.sankuai.waimai.router.Router
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    override val mLayoutId: Int = R.layout.activity_splash

    override fun getViewModel(): ViewModel? {
        return null
    }

    override fun initPage() {
        btn_wandroid.setOnClickListener {
            Router.startUri(this, RouterConstant.WANDROID_HOME_ACTIVITY)
        }
    }

}
