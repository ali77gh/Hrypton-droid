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


        test()

        readLine()
    }

    var i = 0
    fun test() {
        Pasher.pash("m", "u", i.toString(), false, object : PasherListener {
            override fun onReady(pass: String) {
                println(pass)
                if (i < 100) {
                    i++
                    test()
                }
            }

        })
    }

}
