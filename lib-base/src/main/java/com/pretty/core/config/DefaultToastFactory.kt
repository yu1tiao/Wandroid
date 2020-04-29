package com.pretty.core.config

import android.view.Gravity
import android.widget.Toast
import com.pretty.core.Foundation

class DefaultToastFactory : ToastFactory {
    override fun invoke(message: CharSequence, duration: Int): Toast {
        return Toast.makeText(Foundation.getAppContext(), message, duration)
            .apply { setGravity(Gravity.CENTER, 0, 0) }
    }
}