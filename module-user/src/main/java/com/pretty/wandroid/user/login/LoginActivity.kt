package com.pretty.wandroid.user.login

import androidx.fragment.app.commit
import com.pretty.core.R
import com.pretty.core.base.BaseSimpleActivity
import com.pretty.core.router.RC
import com.sankuai.waimai.router.annotation.RouterUri

@RouterUri(path = [RC.WANDROID_LOGIN_ACTIVITY])
class LoginActivity : BaseSimpleActivity() {

    override val mLayoutId: Int = R.layout.a_fragment_container

    override fun initPage() {
        supportFragmentManager.commit {
            add(R.id.flContainer, LoginFragment::class.java, null)
        }
    }

}