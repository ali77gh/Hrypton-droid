package com.ali77gh.pash.ui.layout


import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.*
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.widget.AppCompatCheckBox
import com.ali77gh.pash.R
import com.ali77gh.pash.core.Pasher
import com.ali77gh.pash.core.PasherListener
import com.ali77gh.pash.core.Validation
import com.ali77gh.pash.data.MasterPasswordRepo
import com.ali77gh.pash.ui.dialog.ItsNotLastPasswordDialog
import com.ali77gh.pash.ui.view.FuckingCoolProgressbar
import com.google.android.material.textfield.TextInputEditText


class LoginLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    lateinit var listener: LoginLayoutListener

    var warning = true

    fun render(activity: Activity) {
        val root = activity.layoutInflater.inflate(R.layout.layout_login, null) as ViewGroup

        val input = root.findViewById<TextInputEditText>(R.id.text_login_password)
        val passwordVisibility = root.findViewById<ImageView>(R.id.img_login_password_visible)
        val rememberMe = root.findViewById<AppCompatCheckBox>(R.id.check_remember_me)
        val enter = root.findViewById<Button>(R.id.btn_login)
        val progressbar = root.findViewById<FuckingCoolProgressbar>(R.id.progressbar_login)

        progressbar.render(activity)

        val masterKeyRepo = MasterPasswordRepo(activity)

        warning = !MasterPasswordRepo(activity).hashIsExist()

        var isPasswordMode = true
        passwordVisibility.setOnClickListener {

            warning = false
            if (isPasswordMode){
                input.transformationMethod = HideReturnsTransformationMethod.getInstance()
                passwordVisibility.setImageResource(R.drawable.login_hide_password)
            } else{
                input.transformationMethod = PasswordTransformationMethod.getInstance()
                passwordVisibility.setImageResource(R.drawable.login_show_password)
            }
            isPasswordMode = !isPasswordMode
        }

        enter.setOnClickListener {

            val masterPassword = input.text.toString()

            //   validation
            val res = Validation.password(masterPassword)
            if (res != Validation.OK) {

                showError(input,res)
                return@setOnClickListener
            }

            if (warning) {
                redToast(activity, "warning: app can't check password is correct or not.", LENGTH_LONG)
                redToast(activity, "make sure its correct!!!", LENGTH_SHORT)
                warning = false
                return@setOnClickListener
            }

            fun disableInputs(){
                input.isEnabled = false
                rememberMe.isEnabled = false
                enter.isEnabled = false
            }
            fun enable(){
                input.isEnabled = true
                rememberMe.isEnabled = true
                enter.isEnabled = true
            }


            if (masterKeyRepo.hashIsExist()) {
                progressbar.start()
                disableInputs()
                Pasher.mash(masterPassword, object : PasherListener {

                    override fun onReady(pass: String) {
                        activity.runOnUiThread {
                            progressbar.stop {
                                enable()

                                if (pass == masterKeyRepo.hashLoad()) {
                                    if (rememberMe.isChecked) {

                                        masterKeyRepo.selfSave(masterPassword)
                                        masterKeyRepo.hashRemove()
                                        listener.onReady(masterPassword)
                                    }else{
                                        listener.onReady(masterPassword)
                                    }

                                } else {
                                    ItsNotLastPasswordDialog(activity, cb = {
                                        if (rememberMe.isChecked) {

                                            masterKeyRepo.selfSave(masterPassword)
                                            masterKeyRepo.hashRemove()
                                            listener.onReady(masterPassword)
                                        }else{
                                            progressbar.start()
                                            disableInputs()
                                            masterKeyRepo.hashSave(masterPassword, object : PasherListener {

                                                override fun onReady(pass: String) {
                                                    progressbar.stop {
                                                        enable()
                                                        listener.onReady(masterPassword)
                                                    }
                                                }

                                            })
                                        }


                                    }).show()
                                }
                            }
                        }

                    }
                })

            }else {
                if (rememberMe.isChecked) {

                    masterKeyRepo.selfSave(masterPassword)
                    masterKeyRepo.hashRemove()
                    listener.onReady(masterPassword)
                }else{
                    progressbar.start()
                    disableInputs()
                    masterKeyRepo.hashSave(masterPassword, object : PasherListener {
                        override fun onReady(pass: String) {
                            progressbar.stop {
                                enable()
                                listener.onReady(masterPassword)
                            }
                        }

                    })
                }

            }

        }

        this.addView(root)
    }

    private fun redToast(activity: Activity, txt: String, a: Int) {
        val toastMessage = Toast.makeText(activity, txt, a)
        val toastView = toastMessage.view
        toastView.setBackgroundResource(R.drawable.red_toast)
        toastMessage.show()
    }

    private fun showError(input:TextInputEditText,msg:String){
        val errorBox = findViewById<TextView>(R.id.text_master_password_input_error)

        errorBox.text = msg

        input.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                errorBox.text = ""
            }

        })
    }


    interface LoginLayoutListener {
        fun onReady(masterKey: String)
    }
}
