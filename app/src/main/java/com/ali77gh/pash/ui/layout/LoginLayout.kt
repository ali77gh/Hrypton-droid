package com.ali77gh.pash.ui.layout

import android.app.Activity
import android.content.Context
import android.support.v7.widget.AppCompatCheckBox
import android.util.AttributeSet
import android.widget.*

import com.ali77gh.pash.R
import com.ali77gh.pash.core.Pasher
import com.ali77gh.pash.core.PasherListener
import com.ali77gh.pash.core.Validation
import com.ali77gh.pash.data.MasterKeyRepo
import com.ali77gh.pash.ui.dialog.ItsNotLastPassword
import com.ali77gh.pash.ui.view.FuckingCoolProgressbar

class LoginLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    lateinit var listener: LoginLayoutListener


    fun render(activity: Activity) {
        val root = activity.layoutInflater.inflate(R.layout.layout_login, null) as LinearLayout

        val input = root.findViewById<EditText>(R.id.text_home_password)
        val rememberMe = root.findViewById<AppCompatCheckBox>(R.id.check_remember_me)
        val enter = root.findViewById<Button>(R.id.btn_login)
        val progressbar = root.findViewById<FuckingCoolProgressbar>(R.id.progressbar_login)

        progressbar.render(activity)

        val masterKeyRepo = MasterKeyRepo(activity)

        enter.setOnClickListener {

            val masterPassword = input.text.toString()

            //   validation
            val res = Validation.password(masterPassword)
            if (res != Validation.OK) {

                Toast.makeText(activity, res, Toast.LENGTH_SHORT).show()
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
                Pasher(activity).mash(masterPassword, object : PasherListener {

                    override fun onReady(pass: String) {
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
                                ItsNotLastPassword(activity, cb = {
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

    interface LoginLayoutListener {
        fun onReady(masterKey: String)
    }
}
