package com.pretty.core.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import com.pretty.core.util.GlideApp


/**
 * @author yu
 * @date 2018/10/26
 */
@BindingAdapter(value = ["bind_url", "bind_place_holder", "bind_error"], requireAll = false)
fun loadImage(imageView: ImageView, url: String?, placeHolder: Int?, error: Int?) {
    GlideApp.with(imageView.context)
        .load(url)
        .apply(RequestOptions().placeholder(placeHolder ?: 0).error(error ?: 0))
        .into(imageView)
}

@BindingAdapter(value = ["bind_url_circle", "bind_place_holder", "bind_error"], requireAll = false)
fun loadImageCircle(imageView: ImageView, url: String?, placeHolder: Int?, error: Int?) {
    GlideApp.with(imageView.context)
        .load(url)
        .apply(RequestOptions().placeholder(placeHolder ?: 0).error(error ?: 0).circleCrop())
        .into(imageView)
}