package com.ali77gh.pash.ui.animation

import android.view.View

class FadeLoop {

    private var duration = 300
    private var minFade = 0f
    private var maxFade = 1f

    private var counter = 1
    private var forceStoped = false
    private var loop: Int = 0
    private var view: View? = null

    private val animationEnd = object : OnAnimationEnd {
        override fun onAnimationEnd() {
            if (loop == counter) return
            if (forceStoped) return
            counter++
            OneTimeAnim(view!!, this)
        }
    }

    constructor(view: View, loop: Int, duration: Int) {

        if (loop < 1) throw RuntimeException("loop should be more then 1 current:$loop")
        this.view = view
        this.loop = loop
        this.duration = duration
    }

    constructor(view: View, loop: Int) {

        if (loop < 1) throw RuntimeException("loop should be more then 1 current:$loop")
        this.view = view
        this.loop = loop
    }

    fun setMinFade(minFade: Float) {
        this.minFade = minFade
    }

    fun setMaxFade(maxFade: Float) {
        this.maxFade = maxFade
    }

    fun animate() {
        OneTimeAnim(view!!, animationEnd)
        forceStoped = false
    }

    fun stopAnimate() {
        forceStoped = true
    }

    private fun OneTimeAnim(view: View, onAnimationEnd: OnAnimationEnd) {

        view.animate().alpha(minFade).setDuration(duration.toLong()).start()

        view.postDelayed({ view.animate().alpha(maxFade).setDuration(duration.toLong()).start() }, (duration + 50).toLong())

        view.postDelayed({ onAnimationEnd.onAnimationEnd() }, (duration * 2 + 100).toLong())
    }

    private interface OnAnimationEnd {
        fun onAnimationEnd()
    }
}
