package com.ali77gh.pash.ui.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import com.ali77gh.pash.R
import com.ali77gh.pash.ui.Tools
import com.ali77gh.pash.ui.animation.ExpandAndCollapse
import com.ali77gh.pash.ui.animation.FadeLoop
import kotlin.random.Random

class FuckingCoolProgressbar(context: Context, attrs: AttributeSet) : FrameLayout(context,attrs) {

    private val duration: Int = 500

    private var parent : FrameLayout? = null
    private val tvs = ArrayList<TextView>()
    private var fadeLoop: FadeLoop? = null

    fun render(activity: Activity) {
        val root = activity.layoutInflater.inflate(R.layout.layout_fucking_cool_progressbar, null) as FrameLayout

        parent = root.getChildAt(0) as FrameLayout

        for (i in 0 until parent!!.childCount) // ignore lock icon
            tvs.add(parent!!.getChildAt(i) as TextView)


        parent!!.animate()
                .rotation(30000 * 360f)
                .setInterpolator(LinearInterpolator())
                .setDuration(20000 * 6000)
                .start()

        //switch chars
        for (tv in tvs){
            tv.animate()
                    .rotation(-30000 * 360f)
                    .setInterpolator(LinearInterpolator())
                    .setDuration(20000 * 6000)
                    .start()
            putRandomChar(tv)
        }

        fadeLoop = FadeLoop(root.getChildAt(1),Integer.MAX_VALUE,duration)
        fadeLoop!!.setMinFade(0.6f)

        this.addView(root)
    }

    fun start(){
        this.visibility = View.VISIBLE
        ExpandAndCollapse.expandTo(parent!!,Tools.DpToPixel(120f))
        fadeLoop!!.animate()
    }

    fun stop(cb : ()->Unit){
        ExpandAndCollapse.collapse(parent!!)
        fadeLoop!!.stopAnimate()
        parent!!.postDelayed({
            cb()
            this.visibility = View.GONE
        },duration.toLong())
    }

    private fun putRandomChar(label :TextView){

        when(Random.nextInt(0,10)){
            0 -> label.text= "0"
            1 -> label.text= "+"
            2 -> label.text= "0"
            3 -> label.text= "/"
            4 -> label.text= "1"
            5 -> label.text= "8"
            6 -> label.text= "0"
            7 -> label.text= "="
            8 -> label.text= "1"
            9 -> label.text= "%"
            10 -> label.text="1"
        }


            label.postDelayed({
                putRandomChar(label)
            },Random.nextLong(150,2000))

    }
}