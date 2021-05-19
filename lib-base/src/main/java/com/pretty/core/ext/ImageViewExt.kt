package com.pretty.core.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


fun ImageView.load(url: String, placeHolder: Int = 0, error: Int = 0) {
    Glide.with(context)
        .load(url)
        .apply(RequestOptions().placeholder(placeHolder).error(error))
        .into(this)
}

fun ImageView.loadCircle(url: String, placeHolder: Int = 0, error: Int = 0) {
    Glide.with(context)
        .load(url)
        .apply(RequestOptions().placeholder(placeHolder).error(error).circleCrop())
        .into(this)
}