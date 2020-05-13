package com.pretty.core.ext

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.pretty.core.Foundation

/**
 *
 * @author yu
 * @date 2018/10/25
 */

fun Int.toResDimen(): Float = Foundation.getAppContext().resources.getDimension(this)
fun Int.toResColor(): Int = ContextCompat.getColor(Foundation.getAppContext(), this)
fun Int.toResString(): String = Foundation.getAppContext().getString(this)
fun Int.toResDrawable(): Drawable? = ContextCompat.getDrawable(Foundation.getAppContext(), this)
