package com.pretty.wandroid.user.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModel
import com.pretty.core.base.BaseDataBindFragment
import com.pretty.core.ext.observe
import com.pretty.wandroid.user.R
import com.pretty.wandroid.user.databinding.FRegisterBinding

class RegisterFragment : BaseDataBindFragment<FRegisterBinding>() {

    override val mLayoutId: Int = R.layout.f_register

    private val viewModel by lazy { RegisterViewModel() }

    override fun getViewModel(): ViewModel? = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = viewModel

        mBinding.etUserName.addTextChangedListener(textWatcher)
        mBinding.etPassWord.addTextChangedListener(textWatcher)
        mBinding.etRePassWord.addTextChangedListener(textWatcher)

        mBinding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_default_navigation_btn)
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    override fun subscribeLiveData() {
        super.subscribeLiveData()
        observe(viewModel.registerSuccess) {
            activity?.onBackPressed()
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
