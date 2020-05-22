package com.pretty.wandroid.user.login

import androidx.fragment.app.commit
import androidx.lifecycle.ViewModel
import com.pretty.core.R
import com.pretty.core.base.BaseActivity
import com.pretty.core.router.RouterConstant
import com.sankuai.waimai.router.annotation.RouterUri

@RouterUri(path = [RouterConstant.WANDROID_LOGIN_ACTIVITY])
class LoginActivity : BaseActivity() {

    override val mLayoutId: Int = R.layout.a_fragment_container

    override fun initPage() {
        supportFragmentManager.commit {
            add(R.id.flContainer, LoginFragment::class.java, null)
        }
    }

    override fun getViewModel(): ViewModel? {
        return null
    }
}