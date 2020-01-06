package com.ali77gh.pash.ui.activity

import android.os.Build
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.ali77gh.pash.R
import com.ali77gh.pash.data.MasterKeyRepo
import com.ali77gh.pash.ui.layout.HomeLayout
import com.ali77gh.pash.ui.layout.LoginLayout


class MainActivity : AppCompatActivity() {

    companion object {
        var masterKey = ""
    }

    var drawer : DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupStatusBar()
        setupDrawer()
        setupLayouts()
    }

    override fun onBackPressed() {
        if (drawer!!.isDrawerOpen(Gravity.LEFT))
            drawer!!.closeDrawer(Gravity.LEFT)
        else
        super.onBackPressed()
    }

    private fun setupDrawer(){

        val berger = findViewById<ImageView>(R.id.image_home_berger)
        drawer = findViewById(R.id.drawer_home)

        berger.setOnClickListener {
            drawer!!.openDrawer(Gravity.LEFT)
        }

        findViewById<TextView>(R.id.drawer_how_it_works).setOnClickListener {

        }

        findViewById<TextView>(R.id.drawer_website).setOnClickListener {

        }

        findViewById<TextView>(R.id.drawer_github).setOnClickListener {

        }

        findViewById<TextView>(R.id.drawer_who_is_behind).setOnClickListener {

        }

        findViewById<TextView>(R.id.drawer_forgot_master_password).setOnClickListener {

            MasterKeyRepo(this).selfRemove()
            finish()
            //todo its not working
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
        val listLayout = findViewById<HomeLayout>(R.id.home_list_layout)

        val masterKeyRepo = MasterKeyRepo(this)
        if (masterKeyRepo.selfIsExist()){

            listLayout.render(this)


            loginLayout.visibility = GONE
            listLayout.visibility = VISIBLE
            listLayout.alpha = 1.0f

            masterKey = masterKeyRepo.selfLoad()

        }else{
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
    }


}
