package com.ali77gh.pash.core

import android.app.Activity
import java.security.MessageDigest

class Pasher(private val activity: Activity) {

    //config
    private val HASH_LIEARS_COUNT = 50_000
    private val PASSWORD_MIN_SIZE = 12 //max size is min+3


    companion object{
        val cache = mutableMapOf<String,String>()
    }

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

    private fun standardIt(input: String): String {

        var chars = ""
        var decider = ""
        var isDecider = true
        for (i in input) {
            if (isDecider) {
                decider += i
                isDecider = false
            } else {
                chars += i
                isDecider = true
            }
        }

        var result = ""
        for (i in decider.indices) {

            result += if (shouldSwitch(decider[i])) {
                cipher(chars[i])
            } else {
                chars[i]
            }
        }

        if (Validation.password(result.substring(0,PASSWORD_MIN_SIZE)) != ""){
            result = sha256(result)
            result = standardIt(result)
        }

        return result
    }

    private fun shouldSwitch(input: Char): Boolean {

        return when (input) {
            'a' -> true
            'b' -> true
            'c' -> true
            'd' -> true
            'e' -> true
            'f' -> true
            'g' -> true
            'h' -> true
            'i' -> true
            'j' -> true
            'k' -> true
            'm' -> true
            'n' -> true
            'l' -> true
            'o' -> true
            'p' -> true
            'q' -> true
            'r' -> true
            's' -> false
            't' -> false
            'u' -> false
            'v' -> false
            'w' -> false
            'x' -> false
            'y' -> false
            'z' -> false
            '0' -> false
            '1' -> false
            '2' -> false
            '3' -> false
            '4' -> false
            '5' -> false
            '6' -> false
            '7' -> false
            '8' -> false
            '9' -> false

            else -> throw java.lang.RuntimeException("character is not lower case or number")
        }
    }

    private fun cipher(input: Char): Char {

        return when (input) {
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

            // we miss some special chars ->   ~`-_=+\|}]{[:;"'/?.><,

            else -> throw java.lang.RuntimeException("character is not lower or number")
        }
    }

    /**
    *   substring with dynamic size (PASSWORD_MIN_SIZE..PASSWORD_MIN_SIZE+3) with same possibility
    * */
    private fun limitIt(input: String) : String {

        val bit1 =
                input[input.lastIndex].isLowerCase()
                ||
                input[input.lastIndex].isDigit()

        val bit2 =
                input[input.lastIndex - 1].isLowerCase()
                ||
                input[input.lastIndex - 1].isDigit()
        // this values are 50% true

        return when {
             bit1 and  bit2 -> input.substring(0, PASSWORD_MIN_SIZE)
            !bit1 and  bit2 -> input.substring(0, PASSWORD_MIN_SIZE + 1)
             bit1 and !bit2 -> input.substring(0, PASSWORD_MIN_SIZE + 2)
            !bit1 and !bit2 -> input.substring(0, PASSWORD_MIN_SIZE + 3)
            else -> throw java.lang.RuntimeException("this should never happen")
        }
    }

    private fun alisHashAlgorithm(value: String, listener: PasherListener) {

        if (cache.containsKey(value)){
            listener.onReady(cache[value]!!)
            return
        }

        Thread {


            var pash: String

            pash = slowIt(value)
            pash = standardIt(pash)
            pash = limitIt(pash)

            cache[value] = pash
            activity.runOnUiThread {

                listener.onReady(pash) // go ;)
            }

        }.start()
    }

    // public things

    /**
     * hash to generate password
     * */
    fun pash(masterPass: String, url: String, username: String,isGuest: Boolean, listener: PasherListener ) {
        if (isGuest)
            alisHashAlgorithm("$masterPass$url$username GuestMode", listener)
        else
            alisHashAlgorithm("$masterPass$url$username", listener)

    }

    /**
     * master password hash
     * @return two chars
     * */
    fun mash(masterPass: String, listener: PasherListener) {
        Thread {
            val mash = slowIt(masterPass).substring(0,2)
            activity.runOnUiThread {
                listener.onReady(mash)
            }

        }.start()
    }
}