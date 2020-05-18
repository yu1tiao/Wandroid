package com.pretty.wandroid.user.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pretty.core.base.BaseDataBindFragment
import com.pretty.core.ext.disableIfNoInput
import com.pretty.core.ext.observe
import com.pretty.core.ext.transactParent
import com.pretty.core.util.ToolbarBuilder
import com.pretty.wandroid.user.R
import com.pretty.wandroid.user.databinding.FLoginBinding
import com.pretty.wandroid.user.register.RegisterFragment

class LoginFragment : BaseDataBindFragment<FLoginBinding>() {

    override val mLayoutId: Int = R.layout.f_login

    private val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }

    override fun getViewModel(): ViewModel? = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = viewModel
        mBinding.btnLogin.disableIfNoInput(mBinding.etUserName, mBinding.etPassWord)

        mBinding.btnJump2Register.setOnClickListener {
            transactParent {
                val registerFragment = RegisterFragment()
                add(R.id.flContainer, registerFragment)
                    .addToBackStack(registerFragment.javaClass.canonicalName)
            }
        }

        ToolbarBuilder.build(mBinding.toolbar as Toolbar) { toolbar ->
//            setCenterTitle(R.string.login_page_sign_in)
            setTitle(R.string.login_page_sign_in, R.color.white)
            setDefaultNavigationBtn(activity!!)
        }
    }

    override fun subscribeLiveData() {
        super.subscribeLiveData()
        observe(viewModel.loginSuccess) {
            activity?.finish()
        }
    }

}
