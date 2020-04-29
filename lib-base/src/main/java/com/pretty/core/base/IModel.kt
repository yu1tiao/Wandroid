package com.pretty.core.base

import com.pretty.core.arch.Destroyable

/**
 * @author yu
 * @date 2018/10/26
 */
interface IModel : Destroyable {
    fun onCreate()
}