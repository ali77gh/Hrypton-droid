package com.ali77gh.pash.ui.dialog

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import com.ali77gh.pash.R
import com.ali77gh.pash.core.Pasher
import com.ali77gh.pash.core.PasherListener
import com.ali77gh.pash.data.model.History
import com.ali77gh.pash.ui.activity.MainActivity


class ShowPasswordDialog(activity: Activity, private val history: History) : BaseDialog(activity) {

    var pass : String = ""

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_show_password)

        val password = findViewById<TextView>(R.id.text_show_password_password)
        val progress = findViewById<ProgressBar>(R.id.progress_show_password)
        val close = findViewById<TextView>(R.id.btn_show_password_close)
        val copy = findViewById<TextView>(R.id.btn_show_password_copy)

        Pasher(activity).pash(MainActivity.masterKey, history.url, history.username, object : PasherListener {
            override fun onReady(pass: String) {
                copy.visibility = VISIBLE
                progress.visibility = GONE
                password.text = pass
                this@ShowPasswordDialog.pass = pass
            }

        })

        close.setOnClickListener {
            dismiss()
        }

        copy.setOnClickListener {
            putInClipBoard(pass)
            dismiss()
        }
    }

    private fun putInClipBoard(value :String){
        val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setText(value)
    }
}
