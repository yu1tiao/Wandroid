package com.pretty.core.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import com.pretty.core.util.GlideApp


/**
 * @author yu
 * @date 2018/10/26
 */
@BindingAdapter("bind_imageUrl", "bind_place_holder", requireAll = false)
fun loadImage(imageView: ImageView, url: String?, placeHolder: Int?) {
    GlideApp.with(imageView.context)
        .load(url)
        .apply(RequestOptions().placeholder(placeHolder ?: 0))
        .into(imageView)
}

@BindingAdapter("bind_imageUrl_circle")
fun loadImageCircle(imageView: ImageView, url: String?) {
    GlideApp.with(imageView.context)
        .load(url)
        .apply(RequestOptions().circleCrop())
        .into(imageView)
}