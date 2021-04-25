package com.pretty.core.http

import com.pretty.core.Foundation
import com.pretty.core.config.Condition
import java.io.Serializable

/**
 * 网络请求返回的数据接口
 * @author mathew
 * @date 2020/3/12
 */
open class Resp<T>(
    var data: T?,
    var errorCode: String?,
    var errorMsg: String?
) : Serializable {
    open fun isSuccessful(): Boolean {
        return errorCode == "0"
    }
}

fun <T : Resp<*>> T.check(condition: Condition? = null): T {
    val c = condition ?: Foundation.getGlobalConfig().httpCondition
    return if (c()) {
        this
    } else {
        throw ApiException(errorCode.orEmpty(), errorMsg)
    }
}

