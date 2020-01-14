package com.ali77gh.pash.ui.layout

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.*
import com.ali77gh.pash.R
import com.ali77gh.pash.core.Validation
import com.ali77gh.pash.ui.dialog.ShowPasswordDialog
import com.google.android.material.textfield.TextInputEditText

class HomeLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {


    fun render(activity: Activity) {
        val root = activity.layoutInflater.inflate(R.layout.layout_home, null) as ViewGroup

        val url = root.findViewById<TextInputEditText>(R.id.text_new_password_url)
        val urlErr = root.findViewById<TextView>(R.id.text_website_input_error)
        val username = root.findViewById<TextInputEditText>(R.id.text_new_password_username)
        val usernameErr = root.findViewById<TextView>(R.id.text_username_input_error)
        val add = root.findViewById<TextView>(R.id.btn_new_password_add)


        add.setOnClickListener {

            val urlRes = Validation.website(url.text.toString())
            val usernameRes = Validation.username(username.text.toString())

            if (urlRes != Validation.OK)
                showError(url, urlErr, urlRes)


            if (usernameRes != Validation.OK)
                showError(username, usernameErr, usernameRes)

            if (urlRes == Validation.OK && usernameRes == Validation.OK)
                ShowPasswordDialog(
                        activity,
                        username.text.toString(),
                        url.text.toString()
                ).show()

        }

        this.addView(root)
    }

    private fun showError(input: TextInputEditText, errorBox: TextView, msg: String) {

        errorBox.text = msg

        input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                errorBox.text = ""
            }

        })
    }
}