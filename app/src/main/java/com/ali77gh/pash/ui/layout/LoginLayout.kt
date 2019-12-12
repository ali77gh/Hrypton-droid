package com.ali77gh.pash.ui.layout

import android.app.Activity
import android.content.Context
import android.support.v7.widget.AppCompatCheckBox
import android.util.AttributeSet
import android.widget.*

import com.ali77gh.pash.R
import com.ali77gh.pash.core.Validation
import com.ali77gh.pash.data.MasterKeyRepo

class LoginLayout(context: Context , attrs: AttributeSet) : FrameLayout(context,attrs) {

    lateinit var listener : LoginLayoutListener


    fun render(activity: Activity) {
        val root = activity.layoutInflater.inflate(R.layout.layout_login, null) as LinearLayout

        val input = root.findViewById<EditText>(R.id.text_home_password)
        val rememberMe = root.findViewById<AppCompatCheckBox>(R.id.check_remember_me)
        val enter = root.findViewById<Button>(R.id.btn_login)
        val progressbar = root.findViewById<FuckingCoolProgressbar>(R.id.progressbar_login)

        progressbar.render(activity)

        progressbar.postDelayed( {
            progressbar.start()

            progressbar.postDelayed( {
                progressbar.stop()
                progressbar.postDelayed( {
                    progressbar.start()

                    progressbar.postDelayed( {
                        progressbar.stop()
                    },5000)
                },2000)
            },5000)
        },500)

        enter.setOnClickListener {

            val res = Validation.validateMasterKey(input.text.toString())
            if (res != Validation.OK){

                Toast.makeText(activity,res,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // todo check if input is last master key that entered

            if (rememberMe.isChecked){
                MasterKeyRepo(activity).selfSave(input.text.toString())
            }

            listener.onReady(input.text.toString())
        }

        this.addView(root)
    }

    interface LoginLayoutListener{
        fun onReady(masterKey:String)
    }
}
