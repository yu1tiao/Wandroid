package com.pretty.wandroid.user.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.pretty.core.base.BaseDataBindFragment
import com.pretty.core.ext.disableIfNoInput
import com.pretty.core.ext.observe
import com.pretty.core.util.ToolbarBuilder
import com.pretty.wandroid.user.R
import com.pretty.wandroid.user.databinding.FLoginBinding
import com.pretty.wandroid.user.register.RegisterFragment

class LoginFragment : BaseDataBindFragment<FLoginBinding>() {

    override val mLayoutId: Int = R.layout.f_login

    private val viewModel: LoginViewModel by viewModels()

    override fun getViewModel(): ViewModel? = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = viewModel
        mBinding.btnLogin.disableIfNoInput(mBinding.etUserName, mBinding.etPassWord)

        mBinding.btnJump2Register.setOnClickListener {
            parentFragmentManager.commit {
                val registerFragment = RegisterFragment()
                add(R.id.flContainer, registerFragment)
                    .addToBackStack(registerFragment.javaClass.canonicalName)
            }
        }

        ToolbarBuilder.build(mBinding.toolbar) {
            setTitle(R.string.login_page_sign_in, R.color.white)
            setDefaultNavigationBtn(requireActivity())
        }
    }

    override fun subscribeLiveData() {
        super.subscribeLiveData()
        observe(viewModel.loginSuccess) {
            requireActivity().finish()
        }
    }

}
