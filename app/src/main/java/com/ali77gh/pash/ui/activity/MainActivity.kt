package com.ali77gh.pash.ui.activity

import android.os.Build
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.ali77gh.pash.R
import com.ali77gh.pash.data.MasterKeyRepo
import com.ali77gh.pash.ui.layout.ListLayout
import com.ali77gh.pash.ui.layout.LoginLayout


class MainActivity : AppCompatActivity() {

    companion object {
        var masterKey = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupStatusBar()
        setupDrawer()
        setupLayouts()
        test()

    }

    private fun setupDrawer(){

        val berger = findViewById<ImageView>(R.id.image_home_berger)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_home)

        berger.setOnClickListener {
            drawer.openDrawer(Gravity.LEFT)
        }

        findViewById<TextView>(R.id.drawer_how_it_works).setOnClickListener {

        }

        findViewById<TextView>(R.id.drawer_website).setOnClickListener {

        }

        findViewById<TextView>(R.id.drawer_github).setOnClickListener {

        }

        findViewById<TextView>(R.id.drawer_who_is_behind).setOnClickListener {

        }
    }

    private fun setupStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    private fun setupLayouts(){

        val loginLayout = findViewById<LoginLayout>(R.id.home_login_layout)
        val listLayout = findViewById<ListLayout>(R.id.home_list_layout)

        loginLayout.render(this)
        listLayout.render(this)

        loginLayout.listener = object : LoginLayout.LoginLayoutListener {
            override fun onReady(masterKey:String){

                MainActivity.masterKey = masterKey
                loginLayout.animate().alpha(0.0F).setDuration(200).start()
                loginLayout.postDelayed({
                    listLayout.alpha = 0.0F
                    listLayout.visibility = VISIBLE
                    listLayout.animate().alpha(1.0F).setDuration(200).start()
                },250)
            }
        }
    }

    private fun test(){

        val masterKeyRepo = MasterKeyRepo(this)

        //master key self

//        Log.d(".selfIsExist()", masterKeyRepo.selfIsExist().toString())
//
//        masterKeyRepo.selfSave("masterKeyTest")
//        Log.d("master key saved.","")
//
//        Log.d(".selfIsExist()", masterKeyRepo.selfIsExist().toString())
//
//        Log.d(".selfLoad()", masterKeyRepo.selfLoad())


        //master key hash

        Log.d(".hashIsExist()", masterKeyRepo.hashIsExist().toString())
//
//        masterKeyRepo.hashSave("master key",object :PasherListener{
//            override fun onReady(pass: String) {
//
//                Log.d(".hashIsExist",masterKeyRepo.hashIsExist().toString())
//
//                Log.d(".hashLoad",masterKeyRepo.hashLoad())
//
//                masterKeyRepo.hashCheckSame("masterkey",object :MasterKeyRepo.MasterKeyHashCheckListener{
//                    override fun onReady(isMatch: Boolean) {
//                        Log.d(".isMatch",isMatch.toString())
//                    }
//
//                })
//            }
//        })

        //history

//        val historyRepo = HistoryRepo(this)
//
//        Log.d("size:",historyRepo.all.size.toString())
//
//        historyRepo.Insert(History("instagram.com","ali77gh"))
//
//        Log.d("size:",historyRepo.all.size.toString())
//
//        historyRepo.Remove(historyRepo.all[0].key)
//
//        Log.d("size:",historyRepo.all.size.toString())



    }
}
