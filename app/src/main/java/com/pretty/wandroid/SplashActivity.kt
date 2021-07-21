package com.pretty.wandroid

import android.animation.ObjectAnimator
import androidx.core.animation.addListener
import com.pretty.core.base.BaseSimpleActivity
import com.pretty.core.router.RC
import com.pretty.wandroid.databinding.ActivitySplashBinding
import com.sankuai.waimai.router.Router

class SplashActivity : BaseSimpleActivity<ActivitySplashBinding>() {

    private lateinit var anim: ObjectAnimator

    override fun initBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun initView() {
        anim = ObjectAnimator.ofFloat(mBinding.ivSplash, "alpha", 0.5f, 1f)
            .apply {
                duration = 1500
                addListener(onEnd = {
                    Router.startUri(this@SplashActivity, RC.WANDROID_HOME_ACTIVITY)
                    finish()
                })
            }
        anim.start()
    }

    override fun onDestroy() {
        if (this::anim.isInitialized) {
            anim.cancel()
        }
        super.onDestroy()
    }
}
