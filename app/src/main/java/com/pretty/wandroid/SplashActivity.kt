package com.pretty.wandroid

import android.animation.ObjectAnimator
import androidx.core.animation.addListener
import com.pretty.core.base.BaseSimpleActivity
import com.pretty.core.router.RC
import com.sankuai.waimai.router.Router
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseSimpleActivity() {

    override val mLayoutId: Int = R.layout.activity_splash

    override fun initPage() {
        ObjectAnimator.ofFloat(iv_splash, "alpha", 0.5f, 1f)
            .apply {
                duration = 1500
                addListener(onEnd = {
                    Router.startUri(this@SplashActivity, RC.WANDROID_HOME_ACTIVITY)
                    finish()
                })
            }
            .start()
    }
}
