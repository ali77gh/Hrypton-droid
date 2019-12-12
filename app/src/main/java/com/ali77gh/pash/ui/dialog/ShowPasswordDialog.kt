package com.ali77gh.pash.ui.dialog

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.CheckBox
import android.widget.TextView
import com.ali77gh.pash.R
import com.ali77gh.pash.core.Pasher
import com.ali77gh.pash.core.PasherListener
import com.ali77gh.pash.data.model.History
import com.ali77gh.pash.ui.activity.MainActivity
import com.ali77gh.pash.ui.layout.FuckingCoolProgressbar


class ShowPasswordDialog(activity: Activity, private val history: History) : BaseDialog(activity) {

    var pass : String = ""

    var copy : TextView? = null
    var progress : FuckingCoolProgressbar? = null
    var password : TextView? = null
    var guest : CheckBox? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_show_password)

        password = findViewById(R.id.text_show_password_password)
        progress = findViewById(R.id.progress_show_password)
        guest = findViewById(R.id.check_show_password_guest);
        val close = findViewById<TextView>(R.id.btn_show_password_close)
        copy = findViewById(R.id.btn_show_password_copy)


        startPashing(false)
        progress?.render(activity)

        guest?.setOnCheckedChangeListener { buttonView, isChecked ->
            startPashing(isChecked)
        }

        close.setOnClickListener {
            dismiss()
        }

        copy?.setOnClickListener {
            putInClipBoard(pass)
            dismiss()
        }
    }

    private fun startPashing(isGuest :Boolean){

        copy?.visibility = GONE
        progress?.visibility = VISIBLE
        password?.visibility = GONE
        this.pass = ""
        guest?.isEnabled = false

        Pasher(activity).pash(MainActivity.masterKey, history.url, history.username,isGuest, object : PasherListener {
            override fun onReady(pass: String) {
                copy?.visibility = VISIBLE
                progress?.visibility = GONE
                password?.visibility = VISIBLE
                password?.text = pass
                this@ShowPasswordDialog.pass = pass
                guest?.isEnabled = true
            }

        })

    }

    private fun putInClipBoard(value :String){
        val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setText(value)
    }
}
