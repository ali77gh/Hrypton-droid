package com.ali77gh.pash.ui

import android.content.res.Resources
import android.util.DisplayMetrics

/**
 * Created by ali77gh on 12/14/18.
 */

object Tools {

    @JvmStatic
    fun PixelsToDp(px: Float): Int {
        val metrics = Resources.getSystem().displayMetrics
        val dp = px / (metrics.densityDpi / 160f)
        return Math.round(dp)
    }

    @JvmStatic
    fun DpToPixel(dp: Float): Int {
        val metrics = Resources.getSystem().displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return Math.round(px)
    }

}
