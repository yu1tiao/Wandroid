package com.pretty.wandroid.user.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModel
import com.pretty.core.base.BaseDataBindFragment
import com.pretty.core.ext.observe
import com.pretty.core.ext.transactParent
import com.pretty.wandroid.user.R
import com.pretty.wandroid.user.databinding.FLoginBinding
import com.pretty.wandroid.user.register.RegisterFragment

class LoginFragment : BaseDataBindFragment<FLoginBinding>() {

    override val mLayoutId: Int = R.layout.f_login

    private val viewModel by lazy { LoginViewModel() }

    override fun getViewModel(): ViewModel? = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = viewModel

        mBinding.etUserName.addTextChangedListener(textWatcher)
        mBinding.etPassWord.addTextChangedListener(textWatcher)

        mBinding.btnJump2Register.setOnClickListener {
            transactParent {
                val registerFragment = RegisterFragment()
                add(R.id.flContainer, registerFragment)
                    .addToBackStack(registerFragment.javaClass.canonicalName)
            }
        }
        mBinding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_default_navigation_btn)
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    override fun subscribeLiveData() {
        super.subscribeLiveData()
        observe(viewModel.loginSuccess) {
            activity?.finish()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            mBinding.btnLogin.isEnabled = !(mBinding.etUserName.text.isNullOrEmpty()
                    || mBinding.etPassWord.text.isNullOrEmpty())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }
}
