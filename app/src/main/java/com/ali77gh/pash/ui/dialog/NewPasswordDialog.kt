package com.ali77gh.pash.ui.dialog

import android.app.Activity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.ali77gh.pash.R
import com.ali77gh.pash.data.HistoryRepo
import com.ali77gh.pash.data.model.History

class NewPasswordDialog(activity: Activity) : BaseDialog(activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_new_password)

        val url = findViewById<EditText>(R.id.text_new_password_url)
        val username = findViewById<EditText>(R.id.text_new_password_username)
        val close = findViewById<TextView>(R.id.btn_new_password_close)
        val add = findViewById<TextView>(R.id.btn_new_password_add)


        add.setOnClickListener {

            if (url.text.toString().isEmpty()) {
                Toast.makeText(activity, "plz enter website", LENGTH_SHORT).show()
            }

            if (username.text.toString().isEmpty()) {
                Toast.makeText(activity, "plz enter username", LENGTH_SHORT).show()
            }

            val history = History(url.text.toString(), username.text.toString())

            HistoryRepo(activity).Insert(history)
            dismiss()
            ShowPasswordDialog(activity, history).show()

        }

        close.setOnClickListener {
            dismiss()
        }
    }
}