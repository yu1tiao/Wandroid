package com.pretty.wandroid.user.login

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.BarUtils
import com.pretty.core.base.BaseDataBindActivity
import com.pretty.core.ext.observe
import com.pretty.core.ext.throttleClick
import com.pretty.core.router.RC
import com.pretty.wandroid.user.databinding.ALoginBinding
import com.sankuai.waimai.router.annotation.RouterUri
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RouterUri(path = [RC.WANDROID_LOGIN_ACTIVITY])
class LoginActivity : BaseDataBindActivity<ALoginBinding, LoginViewModel>() {

    private lateinit var animatorSet: AnimatorSet
    private lateinit var loginDialog: LoginDialog
    override val mLayoutId: Int = com.pretty.wandroid.user.R.layout.a_login
    override val mViewModel: LoginViewModel by viewModels()

    override fun initPage(contentView: View) {
        BarUtils.transparentStatusBar(this)
        startAnimation()

        mBinding.btnLogin.throttleClick {
            showLoginDialog()
        }
        mBinding.btnRegister.throttleClick {
            lifecycleScope.launch {
                showLoading()
                delay(2000)
                hideLoading()
            }
        }
    }

    private fun showLoginDialog() {
        if (!this::loginDialog.isInitialized) {
            loginDialog = LoginDialog(this)
            loginDialog.loginBtnClick = { userName, password ->
                mViewModel.btnLogin(userName, password)
            }
        }
        loginDialog.show()
    }

    private fun startAnimation() {
        val animator1 = ObjectAnimator.ofFloat(mBinding.loginBgImage1, "alpha", 1.0f, 0f)
        val animator2 = ObjectAnimator.ofFloat(mBinding.loginBgImage2, "alpha", 0f, 1.0f)
        val animatorScale1 = ObjectAnimator.ofFloat(mBinding.loginBgImage1, "scaleX", 1.0f, 1.3f)
        val animatorScale2 = ObjectAnimator.ofFloat(mBinding.loginBgImage1, "scaleY", 1.0f, 1.3f)
        val animatorSet1 = AnimatorSet()
        animatorSet1.duration = 5000
        animatorSet1.play(animator1).with(animator2).with(animatorScale1).with(animatorScale2)
        animatorSet1.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                // 放大的View复位
                mBinding.loginBgImage1.scaleX = 1.0f
                mBinding.loginBgImage1.scaleY = 1.0f
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })

        val animator3 = ObjectAnimator.ofFloat(mBinding.loginBgImage2, "alpha", 1.0f, 0f)
        val animator4 = ObjectAnimator.ofFloat(mBinding.loginBgImage1, "alpha", 0f, 1.0f)
        val animatorScale3 = ObjectAnimator.ofFloat(mBinding.loginBgImage2, "scaleX", 1.0f, 1.3f)
        val animatorScale4 = ObjectAnimator.ofFloat(mBinding.loginBgImage2, "scaleY", 1.0f, 1.3f)
        val animatorSet2 = AnimatorSet()
        animatorSet2.duration = 5000
        animatorSet2.play(animator3).with(animator4).with(animatorScale3).with(animatorScale4)
        animatorSet2.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                // 放大的View复位
                mBinding.loginBgImage2.scaleX = 1.0f
                mBinding.loginBgImage2.scaleY = 1.0f
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })

        animatorSet = AnimatorSet()
        animatorSet.playSequentially(animatorSet1, animatorSet2)
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                // 循环播放
                animation.start()
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animatorSet.start()
    }

    override fun onDestroy() {
        animatorSet.cancel()
        super.onDestroy()
    }

    override fun subscribeLiveData() {
        super.subscribeLiveData()
        observe(mViewModel.loginSuccess) {
            loginDialog.dismiss()
            finish()
        }
    }
}