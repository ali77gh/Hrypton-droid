package com.ali77gh.pash.ui.dialog

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.ali77gh.pash.R
import com.ali77gh.pash.core.QRCodeTools

class QRDialog(activity: Activity) : BaseDialog(activity) {


    private lateinit var password : String

    constructor(activity: Activity,password:String) : this(activity) {
        this.password = password
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_qr)

        val qrcode = findViewById<ImageView>(R.id.image_qr_code)
        val close = findViewById<TextView>(R.id.image_qr_code_close)

        QRCodeTools.generate(password ){
            bitmap ->
            activity.runOnUiThread {
                qrcode.setImageBitmap(bitmap)
            }
        }

        close.setOnClickListener { dismiss() }
    }
}