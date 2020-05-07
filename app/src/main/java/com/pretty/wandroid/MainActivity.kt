package com.pretty.wandroid

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.pretty.core.base.BaseActivity
import com.pretty.core.util.L
import com.pretty.eventbus.anno.Subscribe
import com.pretty.eventbus.core.BusAutoRegister
import com.pretty.wandroid.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val mLayoutId: Int = R.layout.activity_main

    override fun getViewModel(): ViewModel? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BusAutoRegister.initWith(this)
    }

    override fun subscribeUi(binding: ActivityMainBinding) {
    }

    @Subscribe(tag = "installApk")
    fun test(message: String) {
        L.e("message = $message")
    }
}
