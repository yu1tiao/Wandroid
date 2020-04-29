package com.pretty.wandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pretty.eventbus.anno.Subscribe
import com.pretty.eventbus.core.BusAutoRegister

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BusAutoRegister.initWith(this)
    }


    @Subscribe(tag = "installApk")
    fun test(message: String) {
    }
}
