package com.pretty.wandroid

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import com.pretty.core.base.BaseFragment

class Tf : BaseFragment() {

    override val mLayoutId: Int = R.layout.f_test

    override fun getViewModel(): ViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundColor(Color.parseColor(arguments!!.getString("color")))
    }
}