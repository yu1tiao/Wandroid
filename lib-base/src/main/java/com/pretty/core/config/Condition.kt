package com.pretty.core.config

import com.pretty.core.http.Resp

/**
 * Copyright (c) 2021 北京嗨学网教育科技股份有限公司 All rights reserved.
 *
 * @author yuli
 * @date 4/25/21
 * @description Condition
 */


typealias Condition = Resp<*>.() -> Boolean

val defaultCondition: Condition = {
    this.isSuccessful()
}