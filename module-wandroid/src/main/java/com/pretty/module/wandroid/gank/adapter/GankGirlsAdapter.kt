package com.pretty.module.wandroid.gank.adapter

import android.content.Context
import android.widget.ImageView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.pretty.core.ext.load
import com.pretty.module.wandroid.R
import com.pretty.module.wandroid.entity.GankBean
import me.yuu.liteadapter.core.LiteAdapterEx
import me.yuu.liteadapter.core.ViewHolder

class GankGirlsAdapter(context: Context) : LiteAdapterEx<GankBean>(context) {

    init {
        register(R.layout.c_gank_gril_item) { holder, item, position ->
            holder.setText(R.id.tv_author, item.author)
                .findById<ImageView>(R.id.iv)?.load(item.url)
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        val lp = holder.itemView.layoutParams
        if (lp is StaggeredGridLayoutManager.LayoutParams) {
            val index = holder.layoutPosition
            if (index == 0) {
                lp.isFullSpan = true
            }
        }
    }

}