package com.ali77gh.pash.core

import android.app.Activity
import java.security.MessageDigest

class Pasher(private val activity: Activity) {

    //config
    private val HASH_LIEARS_COUNT = 100000
    private val CHAR_LIMIT = 12

    private fun sha256(base: String): String {
        try {
            val digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(base.toByteArray(charset("UTF-8")))
            val hexString = StringBuffer()

            for (i in hash.indices) {
                val hex = Integer.toHexString(0xff and hash[i].toInt())
                if (hex.length == 1) hexString.append('0')
                hexString.append(hex)
            }

            return hexString.toString()
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }

    }

    private fun slowIt(input: String): String {
        var value = input
        for (i in 0..HASH_LIEARS_COUNT) {
            value = sha256(value)
        }
        return value
    }

    private fun standardIt(input: String) : String{

        var chars = ""
        var desider = ""
        var isDesider = true
        for (i in input){
            if (isDesider){
                desider+=i
                isDesider = false
            }else{
                chars+=i
                isDesider = true
            }
        }

        var result = ""
        for( i in 0 until desider.length){

            if (shouldSwitch(desider[i])){
                result+= switch(chars[i])
            }else{
                result+= chars[i]
            }
        }

        return result
    }

    private fun shouldSwitch(input: Char) : Boolean{

        return when(input){
            'a'->true
            'b'->true
            'c'->true
            'd'->true
            'e'->true
            'f'->true
            'g'->true
            'h'->true
            'i'->true
            'j'->true
            'k'->true
            'm'->true
            'n'->true
            'l'->true
            'o'->true
            'p'->true
            'q'->true
            'r'->true
            's'->false
            't'->false
            'u'->false
            'v'->false
            'w'->false
            'x'->false
            'y'->false
            'z'->false
            '0'->false
            '1'->false
            '2'->false
            '3'->false
            '4'->false
            '5'->false
            '6'->false
            '7'->false
            '8'->false
            '9'->false

            else -> throw java.lang.RuntimeException("charecter is not lower or number")
        }
    }

    private fun switch(input: Char):Char{

        return when(input) {
            'a' -> 'A'
            'b' -> 'B'
            'c' -> 'C'
            'd' -> 'D'
            'e' -> 'E'
            'f' -> 'F'
            'g' -> 'G'
            'h' -> 'H'
            'i' -> 'I'
            'j' -> 'J'
            'k' -> 'K'
            'm' -> 'M'
            'n' -> 'N'
            'l' -> 'L'
            'o' -> 'O'
            'p' -> 'P'
            'q' -> 'Q'
            'r' -> 'R'
            's' -> 'S'
            't' -> 'T'
            'u' -> 'U'
            'v' -> 'V'
            'w' -> 'W'
            'x' -> 'X'
            'y' -> 'Y'
            'z' -> 'Z'
            '0' -> ')'
            '1' -> '!'
            '2' -> '@'
            '3' -> '#'
            '4' -> '$'
            '5' -> '%'
            '6' -> '^'
            '7' -> '&'
            '8' -> '*'
            '9' -> '('

            else -> throw java.lang.RuntimeException("charecter is not lower or number")
        }
    }

    private fun limitIt(input: String) = input.substring(0,CHAR_LIMIT)

    private fun alisHashAlgorithm(value: String, listener: PasherListener) {
        Thread {

            // ok! lets pash
            var pash :String

            pash = slowIt(value) // 1
            pash = standardIt(pash) // 2
            pash = limitIt(pash) // 3

            activity.runOnUiThread {
                listener.onReady(pash) // go ;)
            }

        }.start()
    }

    // public things

    /**
     * password hash
     * */
    fun pash(masterPass: String, url: String, username: String, listener: PasherListener) {
        alisHashAlgorithm("$masterPass$url$username", listener)
    }

    /**
     * master key hash
     * */
    fun mash(masterPass: String, listener: PasherListener) {
        Thread {

            val mash :String = slowIt(masterPass)

            activity.runOnUiThread {
                listener.onReady(mash)
            }

        }.start()
    }
}