package com.ali77gh.pash.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.ali77gh.pash.R
import com.ali77gh.pash.core.Pasher
import com.ali77gh.pash.core.PasherListener

class MainActivity : AppCompatActivity() {

    val hasher = Pasher(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupDrawer()


        hasher.pash("1234","instagram.com","ali77gh", object : PasherListener {
            override fun onReady(pass: String) {

                Toast.makeText(this@MainActivity,pass,LENGTH_LONG)
            }
        })
    }

    private fun setupDrawer(){
        val berger = findViewById<ImageView>(R.id.image_home_berger)
        var drawer = findViewById<DrawerLayout>(R.id.drawer_home)

        berger.setOnClickListener {
            drawer.openDrawer(Gravity.LEFT)
        }
    }
}
