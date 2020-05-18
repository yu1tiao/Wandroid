package com.pretty.wandroid.user.register

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pretty.core.base.BaseDataBindFragment
import com.pretty.core.ext.disableIfNoInput
import com.pretty.core.ext.observe
import com.pretty.core.util.ToolbarBuilder
import com.pretty.wandroid.user.R
import com.pretty.wandroid.user.databinding.FRegisterBinding

class RegisterFragment : BaseDataBindFragment<FRegisterBinding>() {

    override val mLayoutId: Int = R.layout.f_register

    private val viewModel by lazy { ViewModelProvider(this).get(RegisterViewModel::class.java) }

    override fun getViewModel(): ViewModel? = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = viewModel
        mBinding.btnLogin.disableIfNoInput(mBinding.etUserName, mBinding.etPassWord, mBinding.etRePassWord)

        ToolbarBuilder.build(mBinding.toolbar as Toolbar) { toolbar ->
//            setCenterTitle(R.string.login_page_sign_in)
            setTitle(R.string.login_page_sign_up, R.color.white)
            setDefaultNavigationBtn(activity!!)
        }
    }

    override fun subscribeLiveData() {
        super.subscribeLiveData()
        observe(viewModel.registerSuccess) {
            activity?.onBackPressed()
        }
    }
}
