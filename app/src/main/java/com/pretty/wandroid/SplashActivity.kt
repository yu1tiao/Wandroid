package com.pretty.wandroid

import android.animation.ObjectAnimator
import android.view.View
import androidx.activity.viewModels
import androidx.core.animation.addListener
import com.pretty.core.base.BaseDataBindActivity
import com.pretty.core.base.BaseViewModel
import com.pretty.core.router.RC
import com.pretty.wandroid.databinding.ActivitySplashBinding
import com.sankuai.waimai.router.Router

class SplashActivity : BaseDataBindActivity<ActivitySplashBinding, BaseViewModel>() {

    private lateinit var anim: ObjectAnimator
    override val mLayoutId: Int = R.layout.activity_splash

    override val mViewModel: BaseViewModel by viewModels()

    override fun initPage(contentView: View) {
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
