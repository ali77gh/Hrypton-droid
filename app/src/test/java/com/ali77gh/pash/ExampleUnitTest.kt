package com.ali77gh.pash

import com.ali77gh.pash.core.Pasher
import com.ali77gh.pash.core.PasherListener
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {

        for (i in 0..10000)
        Pasher.pash("masterPass","url.com","$i",false,object : PasherListener {
            override fun onReady(pass: String) {
                println(pass)
            }

        })

        readLine()
        //println("0123456789abcdefghijkmnlopqrstuvwxyzABCDEFGHIJKMNLOPQRSTUVWXYZ/~!@#\$%^&*_-+=`|\\(){}[]:;\"'<>,.?//".length)
    }

}
