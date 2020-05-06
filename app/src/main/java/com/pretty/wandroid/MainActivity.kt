package com.pretty.wandroid

import androidx.lifecycle.ViewModel
import com.pretty.core.base.BaseActivity
import com.pretty.core.base.BaseViewModel
import com.pretty.core.ext.add
import com.pretty.core.util.L
import com.pretty.eventbus.anno.Subscribe
import com.pretty.wandroid.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    //    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        BusAutoRegister.initWith(this)
//
//        add(R.id.fl, TestPermissionFragment())
//    }
    override val mLayoutId: Int=R.layout.activity_main

    override fun getViewModel(): ViewModel? {
        return null
    }

    override fun subscribeUi(binding: ActivityMainBinding) {
        add(R.id.fl, TestPermissionFragment())
    }

    @Subscribe(tag = "installApk")
    fun test(message: String) {
        L.e("message = $message")
    }
}
