package com.ali77gh.pash.core


import java.security.MessageDigest

object Pasher {

    //config
    private val HASH_LIEARS_COUNT = 50_000 // increase this make algorithm slower
    private val PASSWORD_MIN_SIZE = 12 //if you want to increase PASSWORD_MIN_SIZE use sha-512
    //max size is min+3

    // caching
    private val cache = mutableMapOf<String,String>()

    /**
     * @return 64 lower case and numbers
     * */
    private fun sha256(input: String): String {
        try {
            val digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(input.toByteArray(charset("UTF-8")))
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

    /**
     *  calling hash many times to make reverse (crack) harder
     *  @return 64 lower case and numbers
     * */
    private fun slowIt(input: String): String {
        var value = input
        for (i in 0..HASH_LIEARS_COUNT) {
            value = sha256(value)
        }
        return value
    }

    /**
     * sha-256 output is just lower case and numbers
     * this method will add upper case and special chars for make it standard
     * @return 32 lower case and upper case and numbers and allowed special chars
     * */
    private object Standard {

        /**
         * @param input decider (lowercase or number)
         * */
        fun shouldSwitch(input: Char): Boolean {

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

        /**
         * char replacement map
         * @param input lower case and number
         * @return      upper case and allowed special chars
         * */
        fun switch(input: Char): Char {

            return when (input) {
                // used in both calls
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

        fun main(input: String) :String{
            val chars = input.substring(0,32)
            val decider = input.substring(32,64)

            var result = ""
            for (i in decider.indices) {

                result += if (shouldSwitch(decider[i])) {
                    switch(chars[i])
                } else {
                    chars[i]
                }
            }

            //if its not standard try one more hash
            //(to make sure output is standard password and website validator gonna accept it)
            if (Validation.password(result.substring(0,PASSWORD_MIN_SIZE)) != Validation.OK){
                result = sha256(result)
                result = main(result)
            }

            return result
        }

    }


    /**
    * @return chars with dynamic size (PASSWORD_MIN_SIZE..PASSWORD_MIN_SIZE+3) with same possibility
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

    /**
     * @param listener this callback called when password is ready (not in ui thread)
     * */
    private fun mainAlgorithm(value: String, listener: PasherListener) {

        Thread {

            if (cache.containsKey(value)){
                listener.onReady(cache[value]!!)
                return@Thread
            }

            var pash: String

            pash = slowIt(value)
            pash = Standard.main(pash)
            pash = limitIt(pash)

            listener.onReady(pash) // go ;)

            cache[value] = pash // push to cache

        }.start()
    }



    // public methods

    /**
     * hash to generate password
     * */
    fun pash(masterPass: String, url: String, username: String,isGuest: Boolean, listener: PasherListener ) {
        if (isGuest)
            mainAlgorithm("$masterPass$url$username GuestMode", listener)
        else
            mainAlgorithm("$masterPass$url$username", listener)

    }

    /**
     * master password hash (to find out this master password is the previous one or not)
     * @return two chars
     * */
    fun mash(masterPass: String, listener: PasherListener) {
        Thread {

            listener.onReady(slowIt(masterPass).substring(0,2))

        }.start()
    }
}