package com.pretty.wandroid

import androidx.lifecycle.ViewModel
import com.pretty.core.base.BaseActivity

class MainActivity : BaseActivity() {

    override val mLayoutId: Int = R.layout.activity_main

    override fun getViewModel(): ViewModel? {
        return null
    }

    override fun initPage() {

    }

}
