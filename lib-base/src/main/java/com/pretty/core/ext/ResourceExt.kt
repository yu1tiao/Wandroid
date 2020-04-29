package com.pretty.core.ext

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @author yu
 * @date 2018/10/25
 */
fun Context.dimenPx(resId: Int): Int = resources.getDimensionPixelSize(resId)

fun Context.color(resId: Int): Int = resources.getColor(resId)

fun Context.drawable(resId: Int): Drawable = resources.getDrawable(resId)

fun Context.string(resId: Int): String = resources.getString(resId)

fun Context.stringArray(resId: Int): Array<String> = resources.getStringArray(resId)


fun Fragment.dimenPx(resId: Int): Int = resources.getDimensionPixelSize(resId)

fun Fragment.color(resId: Int): Int = resources.getColor(resId)

fun Fragment.drawable(resId: Int): Drawable = resources.getDrawable(resId)

fun Fragment.string(resId: Int): String = resources.getString(resId)

fun Fragment.stringArray(resId: Int): Array<String> = resources.getStringArray(resId)


fun View.dimenPx(resId: Int): Int = resources.getDimensionPixelSize(resId)

fun View.color(resId: Int): Int = resources.getColor(resId)

fun View.drawable(resId: Int): Drawable = resources.getDrawable(resId)

fun View.string(resId: Int): String = resources.getString(resId)

fun View.stringArray(resId: Int): Array<String> = resources.getStringArray(resId)


fun RecyclerView.ViewHolder.dimenPx(resId: Int): Int = itemView.dimenPx(resId)

fun RecyclerView.ViewHolder.color(resId: Int): Int = itemView.color(resId)

fun RecyclerView.ViewHolder.drawable(resId: Int): Drawable = itemView.drawable(resId)

fun RecyclerView.ViewHolder.string(resId: Int): String = itemView.string(resId)

fun RecyclerView.ViewHolder.stringArray(resId: Int): Array<String> = itemView.stringArray(resId)
