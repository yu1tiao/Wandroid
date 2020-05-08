package com.pretty.wandroid

import androidx.lifecycle.ViewModel
import com.pretty.core.arch.launchFragmentInContainer
import com.pretty.core.base.BaseActivity
import com.pretty.wandroid.user.login.LoginFragment

class MainActivity : BaseActivity() {

    override val mLayoutId: Int = R.layout.activity_main

    override fun getViewModel(): ViewModel? {
        return null
    }

    override fun initPage() {
        launchFragmentInContainer(this, LoginFragment::class.java)
    }

}
