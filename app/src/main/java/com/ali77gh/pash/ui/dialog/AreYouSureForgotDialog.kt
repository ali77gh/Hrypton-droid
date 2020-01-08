package com.ali77gh.pash.ui.dialog

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.ali77gh.pash.R

class AreYouSureForgotDialog(activity: Activity) : BaseDialog(activity) {

    private lateinit var callback : () -> Unit

    constructor(activity: Activity,cb :() -> Unit) : this(activity) {
        callback = cb
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_are_you_sure_forgot)

        val no = findViewById<TextView>(R.id.btn_forgot_no)
        val yes = findViewById<TextView>(R.id.btn_forgot_yes)

        no.setOnClickListener {
            dismiss()
        }

        yes.setOnClickListener {
            callback()
            dismiss()
        }

    }
}