package com.pretty.wandroid

import android.util.Log
import com.pretty.core.base.BaseApplication
import com.pretty.core.config.GlobalConfiguration
import com.pretty.core.config.INetPolicy
import com.pretty.core.http.CommonHeaderInterceptor
import com.pretty.core.router.RC
import com.sankuai.waimai.router.Router
import com.sankuai.waimai.router.common.DefaultRootUriHandler
import com.sankuai.waimai.router.components.DefaultLogger
import com.sankuai.waimai.router.core.Debugger
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        initRouter()
    }

    private fun initRouter() {
        Debugger.setLogger(DefaultLogger())
        Debugger.setEnableLog(true)
        Debugger.setEnableDebug(true)

        // 配置了默认scheme和host的，跳转需要写完全路径
//        Router.init(DefaultRootUriHandler(this, RC.SCHEME, RC.HOST))
        Router.init(DefaultRootUriHandler(this))
    }

    override fun initGlobalConfiguration(): GlobalConfiguration {
        return GlobalConfiguration.create {
            okHttpConfigCallback = {
                it.connectTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(CommonHeaderInterceptor())
                    .addInterceptor(
                        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                            override fun log(message: String) {
                                Log.d("okHttp_", message)
                            }
                        }).apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
            }
            retrofitConfigCallback = {
                it.addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            }
        }
    }

    override fun getNetPolicy(buildType: String): INetPolicy {
        return when (buildType) {
            "sit" -> SitNetPolicy()
            "release" -> ReleaseNetPolicy()
            else -> DevNetPolicy()
        }
    }
}
