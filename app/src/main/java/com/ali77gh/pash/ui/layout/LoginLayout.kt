package com.ali77gh.pash.ui.layout

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout

import com.ali77gh.pash.R

class LoginLayout(context: Context , attrs: AttributeSet) : FrameLayout(context,attrs) {

    lateinit var listener : LoginLayoutListener


    fun render(activity: Activity) {
        val root = activity.layoutInflater.inflate(R.layout.layout_login, null) as LinearLayout

        val input = root.findViewById<EditText>(R.id.text_home_password)
        val rememberMe = root.findViewById<EditText>(R.id.check_remember_me)
        val enter = root.findViewById<Button>(R.id.btn_login)


        enter.setOnClickListener {

            // todo validation and show error
            // todo check if input is last master key that entered
            // todo watch remember me
            listener.onReady(input.text.toString())
        }

        this.addView(root)
    }

    interface LoginLayoutListener{
        fun onReady(masterKey:String)
    }
}
