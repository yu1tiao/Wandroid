package com.pretty.wandroid

import android.util.Log
import com.pretty.core.base.BaseApplication
import com.pretty.core.config.GlobalConfiguration
import com.pretty.core.config.INetPolicy
import com.pretty.core.http.CommonHeaderInterceptor
import com.pretty.core.util.L
import com.sankuai.waimai.router.Router
import com.sankuai.waimai.router.common.DefaultRootUriHandler
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        Router.init(DefaultRootUriHandler(this))
    }

    override fun initGlobalConfiguration(): GlobalConfiguration {
        return GlobalConfiguration.create {
            netPolicyProvider = this@App
            okHttpConfigCallback = {
                it.connectTimeout(20, TimeUnit.SECONDS)
//                    .addInterceptor(CommonHeaderInterceptor())
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

    override fun getNetPolicy(): INetPolicy {
        return DevNetPolicy()
    }
}
