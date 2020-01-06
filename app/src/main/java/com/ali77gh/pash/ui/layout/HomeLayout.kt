package com.ali77gh.pash.ui.layout

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.*
import com.ali77gh.pash.R
import com.ali77gh.pash.ui.dialog.ShowPasswordDialog

class HomeLayout(context: Context, attrs: AttributeSet) : FrameLayout(context,attrs) {


    fun render(activity: Activity) {
        val root = activity.layoutInflater.inflate(R.layout.layout_home, null) as ViewGroup

        val url =root.findViewById<EditText>(R.id.text_new_password_url)
        val username = root.findViewById<EditText>(R.id.text_new_password_username)
        val add = root.findViewById<TextView>(R.id.btn_new_password_add)


        add.setOnClickListener {

            if (url.text.toString().isEmpty()) {
                Toast.makeText(activity, "plz enter website", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (username.text.toString().isEmpty()) {
                Toast.makeText(activity, "plz enter username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ShowPasswordDialog(
                    activity,
                    username.text.toString(),
                    url.text.toString()
            ).show()

        }

        this.addView(root)
    }
}