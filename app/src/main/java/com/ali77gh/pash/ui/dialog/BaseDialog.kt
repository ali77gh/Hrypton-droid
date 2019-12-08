package com.ali77gh.pash.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.Window

import com.ali77gh.pash.R

/**
 * Created by ali77gh on 1/20/19.
 */

open class BaseDialog(protected val activity: Activity) : Dialog(activity) {

    init {
        try {
            window!!.attributes.windowAnimations = R.style.DialogAnim
        } catch (ignored: NullPointerException) {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)//remove title in some android versions
    }
}
