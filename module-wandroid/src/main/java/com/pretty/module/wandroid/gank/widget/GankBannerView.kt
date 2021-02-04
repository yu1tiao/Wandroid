package com.pretty.module.wandroid.gank.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.pretty.core.ext.dp
import com.pretty.core.widget.RadiusImageView
import com.pretty.module.wandroid.R
import com.pretty.module.wandroid.entity.BannerBean
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.indicator.CircleIndicator


class GankBannerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var banner: Banner<*, *>

    init {
        LayoutInflater.from(context).inflate(R.layout.v_gank_banner, this)
        val padding = 10.dp().toInt()
        setPadding(padding, padding, padding, padding)

        banner = findViewById(R.id.banner)
    }

    fun setBanner(lifecycle: LifecycleOwner, data: List<BannerBean>) {
        banner.addBannerLifecycleObserver(lifecycle)
            .setAdapter(GankBannerAdapter(data))
            .isAutoLoop(true)
            .setIndicator(CircleIndicator(context))
            .setIndicatorSelectedColorRes(R.color.white)
            .setIndicatorNormalColorRes(R.color.gray400)
            .setIndicatorWidth(6.dp().toInt(), 6.dp().toInt())
            .setIndicatorRadius(3.dp().toInt())
//            .addPageTransformer(ScaleInTransformer())
//            .setBannerGalleryEffect(20.dp().toInt(), 0)
            .setBannerGalleryMZ(20.dp().toInt())
    }
}

private class GankBannerAdapter(data: List<BannerBean>) :
    BannerAdapter<BannerBean, GankBannerAdapter.ViewHolder>(data) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val imageView = RadiusImageView(parent.context)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.cornerRadius = 16.dp().toInt()
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return ViewHolder(imageView)
    }

    override fun onBindView(holder: ViewHolder, data: BannerBean, position: Int, size: Int) {
        holder.imageView.load(data.image)
    }

    inner class ViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)
}