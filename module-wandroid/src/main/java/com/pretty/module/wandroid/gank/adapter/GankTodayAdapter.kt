package com.pretty.module.wandroid.gank.adapter

import android.content.Context
import android.widget.ImageView
import coil.load
import com.pretty.core.ext.safeCast
import com.pretty.module.wandroid.R
import com.pretty.module.wandroid.entity.GankBean
import me.yuu.liteadapter.core.LiteAdapterEx

class GankTodayAdapter(context: Context) : LiteAdapterEx<Any>(context) {

    init {
        register(R.layout.c_gank_hot_item) { holder, item, position ->
            when (item) {
                is GankBean -> {
                    val imageUrl = if (item.images.isNullOrEmpty()) "" else item.images[0]
                    holder.setText(R.id.tv, item.title)
                        .setText(R.id.tv_who, item.author)
                        .setText(R.id.tv_date, item.publishedAt)
                        .findById<ImageView>(R.id.iv)?.load(imageUrl)
                }
            }
        }
        register(R.layout.c_gank_header_item) { holder, item, position ->
            item.safeCast<String> {
                holder.setText(R.id.tv, it)
            }
        }

        injectorFinder { item, position, itemCount ->
            return@injectorFinder when (item) {
                is String -> 1
                else -> 0
            }
        }
    }

}