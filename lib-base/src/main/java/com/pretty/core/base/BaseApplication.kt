package com.pretty.core.base

import android.app.Application
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.pretty.core.Foundation
import com.pretty.core.config.GlobalConfiguration

/**
 * @author yu
 * @date 2018/11/9
 */
abstract class BaseApplication : Application(), ViewModelStoreOwner,
    HasDefaultViewModelProviderFactory {

    private lateinit var mAppViewModelStore: ViewModelStore

    abstract var mConfiguration: GlobalConfiguration

    override fun onCreate() {
        super.onCreate()
        Foundation.init(this)
        mAppViewModelStore = ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    /**
     * 获取一个应用级的ViewModelProvider，可以用来全局的数据
     */
    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this)
    }

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(this)
    }
}

/**
 * 获取应用级别作用域的ViewModel
 */
inline fun <reified VM : BaseViewModel> getAppViewModel(): VM {
    return Foundation.getAppContext().getAppViewModelProvider().get(VM::class.java)
}

