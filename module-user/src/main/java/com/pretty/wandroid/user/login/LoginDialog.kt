package com.pretty.wandroid.user.login

import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.blankj.utilcode.util.ScreenUtils
import com.pretty.core.base.BaseDialog
import com.pretty.core.ext.disableIfEmpty
import com.pretty.core.ext.dp
import com.pretty.core.ext.throttleClick
import com.pretty.wandroid.user.R

class LoginDialog(context: Context) : BaseDialog(context) {

    var loginBtnClick: ((String, String) -> Unit)? = null

    init {
        setContentView(R.layout.d_login)
        window?.run {
            decorView.setPadding(0, 0, 0, 0)

            val lp = attributes
            lp.width = ScreenUtils.getScreenWidth() - 80.dp()
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            lp.dimAmount = 0.2f
            lp.gravity = Gravity.CENTER

            this.attributes = lp
            setWindowAnimations(com.pretty.core.R.style.bottom_in_bottom_out)
        }

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etUserName = findViewById<EditText>(R.id.etUserName)
        val etPassWord = findViewById<EditText>(R.id.etPassWord)

        btnLogin.disableIfEmpty(etUserName, etPassWord)
        btnLogin.throttleClick {
            loginBtnClick?.invoke(
                etUserName.text.toString().trim(),
                etPassWord.text.toString().trim()
            )
        }
    }

}