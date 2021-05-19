package com.pretty.module.wandroid.gank.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pretty.core.ext.dp
import com.pretty.core.ext.load
import com.pretty.module.wandroid.R
import com.pretty.module.wandroid.entity.GankCategoryBean
import me.yuu.liteadapter.core.LiteAdapter

class GankCategoryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val adapter: CategoryAdapter


    init {
        LayoutInflater.from(context).inflate(R.layout.v_gank_category, this)
        orientation = VERTICAL
        layoutParams = LayoutParams(-1, -2)
        val padding = 10.dp()
        setPadding(padding, padding, padding, padding)

        adapter = CategoryAdapter(context)

        with(findViewById<RecyclerView>(R.id.rv_category)) {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = this@GankCategoryView.adapter
        }
    }

    fun bindData(data: List<GankCategoryBean>) {
        adapter.updateData(data)
    }
}

private class CategoryAdapter(context: Context) : LiteAdapter<GankCategoryBean>(context) {

    init {
        register(R.layout.c_gank_category_item) { holder, item, position ->
            holder.setText(R.id.tv, item.title)
                .findById<ImageView>(R.id.iv)?.load(item.coverImageUrl)
        }
    }
}