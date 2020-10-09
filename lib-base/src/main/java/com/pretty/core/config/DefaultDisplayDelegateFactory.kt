package com.pretty.core.config

import com.pretty.core.arch.DisplayDelegate
import com.pretty.core.arch.IDisplayDelegate


class DefaultDisplayDelegateFactory : DisplayDelegateFactory {

    override fun invoke(): IDisplayDelegate {
        return DisplayDelegate()
    }
}