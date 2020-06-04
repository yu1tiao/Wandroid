package com.pretty.wandroid.user.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.pretty.core.base.BaseDataBindFragment
import com.pretty.core.ext.disableIfNoInput
import com.pretty.core.ext.observe
import com.pretty.core.util.ToolbarBuilder
import com.pretty.wandroid.user.R
import com.pretty.wandroid.user.databinding.FRegisterBinding

class RegisterFragment : BaseDataBindFragment<FRegisterBinding, RegisterViewModel>() {

    override val mLayoutId: Int = R.layout.f_register
    override val mViewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = mViewModel
        mBinding.btnLogin.disableIfNoInput(
            mBinding.etUserName,
            mBinding.etPassWord,
            mBinding.etRePassWord
        )

        ToolbarBuilder.build(mBinding.toolbar) {
//            setCenterTitle(R.string.login_page_sign_in)
            setTitle(R.string.login_page_sign_up, R.color.white)
            setDefaultNavigationBtn(requireActivity())
        }
    }

    override fun subscribeLiveData() {
        super.subscribeLiveData()
        observe(mViewModel.registerSuccess) {
            requireActivity().onBackPressed()
        }
    }
}
