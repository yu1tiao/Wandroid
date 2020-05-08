package com.pretty.core.base

import com.pretty.core.http.FlatResp
import com.pretty.core.http.check
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * @author yu
 * @date 2018/10/29
 */
abstract class BaseModel : IModel {

    override fun onCreate() {

    }

    override fun destroy() {

    }

    /**
     * 使用协程请求Http数据，在Retrofit定义的接口中声明挂起函数就可以直接使用
     * 例如：
     * @GET("article/list/0/json")
     * suspend fun getData(): Resp<Any>
     */
    protected suspend fun <T : FlatResp> requestHttp(block: suspend () -> T): T =
        withContext(Dispatchers.IO) {
            return@withContext block().check()
        }
}