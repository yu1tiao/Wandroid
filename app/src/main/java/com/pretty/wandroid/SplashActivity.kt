package com.pretty.wandroid

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.pretty.core.base.BaseSimpleActivity
import com.pretty.core.ext.throttleClick
import com.pretty.core.router.RC
import com.pretty.core.router.compact.UriProxyActivity
import com.pretty.core.util.NotificationUtil
import com.sankuai.waimai.router.Router
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseSimpleActivity() {

    override val mLayoutId: Int = R.layout.activity_splash

    override fun initPage() {
        btn_wandroid.throttleClick {
            Router.startUri(this, RC.WANDROID_HOME_ACTIVITY)
        }
        btn_notify.throttleClick {
            // 注意需要外部跳转的页面需要在RouterUri中设置exported = true
            NotificationUtil.showNotify(
                this,
                "我是标题",
                "我是内容详情",
                R.mipmap.ic_launcher,
                createPendingIntent(
                    this,
                    RC.WANDROID_HOME_ACTIVITY
                )
            )
        }
    }

    private fun createPendingIntent(context: Context?, deepLink: String): PendingIntent {
        val intent = Intent(context, UriProxyActivity::class.java).apply {
            data = deepLink.toUri()
        }
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}
