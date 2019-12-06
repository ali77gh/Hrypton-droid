package com.ali77gh.pash.ui.layout

import android.app.Activity
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.FrameLayout
import com.ali77gh.pash.R

class ListLayout(context: Context , attrs: AttributeSet) : FrameLayout(context,attrs) {

    fun render(activity: Activity) {
        val root = activity.layoutInflater.inflate(R.layout.layout_list, null) as ConstraintLayout

        this.addView(root)
    }
}