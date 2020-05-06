package com.pretty.wandroid

import androidx.lifecycle.ViewModel
import com.pretty.core.base.BaseFragment
import com.pretty.wandroid.databinding.FTestPermissionBinding

class TestPermissionFragment : BaseFragment<FTestPermissionBinding>() {

    override val mLayoutId: Int = R.layout.f_test_permission

    override fun getViewModel(): ViewModel? {
        return null
    }

    override fun subscribeUi(binding: FTestPermissionBinding) {
        binding.btnPermission.setOnClickListener {

        }
    }

}
