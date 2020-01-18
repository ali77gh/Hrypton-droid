package com.ali77gh.pash.ui.dialog

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.ali77gh.pash.R
import com.ali77gh.pash.core.Pasher
import com.ali77gh.pash.core.PasherListener
import com.ali77gh.pash.ui.activity.MainActivity
import com.ali77gh.pash.ui.view.FuckingCoolProgressbar


class ShowPasswordDialog(activity: Activity) : BaseDialog(activity) {

    private var pass : String = ""

    private var username = ""
    private var url = ""
    private var bankNumber = ""
    private var bankMode = false

    private var title : TextView? = null
    private var password : TextView? = null
    private var progress : FuckingCoolProgressbar? = null
    private var guest : CheckBox? = null
    private var copy : TextView? = null
    private var qr : ImageView?= null

    constructor(activity: Activity,username: String, url:String):this(activity){
        this.username = username
        this.url = url
    }

    constructor(activity: Activity, bankNumber:String):this(activity){
        this.bankNumber = bankNumber
        bankMode = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_show_password)

        title = findViewById(R.id.text_show_password_title)
        password = findViewById(R.id.text_show_password_password)
        progress = findViewById(R.id.progress_show_password)
        guest = findViewById(R.id.check_show_password_guest);
        val close = findViewById<TextView>(R.id.btn_show_password_close)
        copy = findViewById(R.id.btn_show_password_copy)
        qr = findViewById(R.id.image_show_password_qr_code)


        progress!!.render(activity)
        startPashing(false)


        guest!!.setOnCheckedChangeListener { buttonView, isChecked ->
            startPashing(isChecked)
        }

        close.setOnClickListener {
            dismiss()
        }

        copy!!.setOnClickListener {
            putInClipBoard(pass)
            Toast.makeText(activity, "copied to clipboard", LENGTH_LONG).show()
            dismiss()
        }

        qr!!.setOnClickListener {
            QRDialog(activity,pass).show()
        }
    }

    private fun startPashing(isGuest :Boolean){

        fun startAnim(){
            title!!.text = "generating password..."
            copy!!.visibility = GONE
            qr!!.visibility = GONE
            progress!!.start()
            password!!.visibility = GONE
            this.pass = ""
            guest!!.isEnabled = false
        }

        fun stopAnim(pass:String){
            progress!!.stop(cb = {
                title!!.text = "your password"
                copy!!.visibility = VISIBLE
                qr!!.visibility = VISIBLE
                password!!.visibility = VISIBLE
                password!!.text = pass
                this@ShowPasswordDialog.pass = pass
                guest!!.isEnabled = true
            })
        }


        startAnim()
        if (bankMode)
            Pasher.pashBankMode(MainActivity.masterKey, bankNumber,isGuest, object : PasherListener {
                override fun onReady(pass: String) {
                    activity.runOnUiThread{
                        stopAnim(pass)
                    }
                }

            })
        else
            Pasher.pash(MainActivity.masterKey, url, username,isGuest, object : PasherListener {
                override fun onReady(pass: String) {
                    activity.runOnUiThread{
                        stopAnim(pass)
                    }
                }

            })

    }

    private fun putInClipBoard(value :String){
        val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setText(value)
    }
}
