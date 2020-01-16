package com.ali77gh.pash.core


import java.security.MessageDigest

object Pasher {

    //config
    private val HASH_LAYERS_COUNT = 50_000 // increase this make algorithm slower (and safer)
    private val PASSWORD_MIN_SIZE = 12 //if you want to increase PASSWORD_MIN_SIZE use sha-512
    //max size is min+3

    // caching
    private val cache = mutableMapOf<String, String>()

    /**
     * @return 64 chars (0 1 2 3 4 5 6 7 8 9 a b c d e f) with same possibility
     * */
    fun sha256(input: String): String {
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
        for (i in 0..HASH_LAYERS_COUNT) {
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

        private const val hexChars = "0123456789abcdef"
        private const val allowedChars = "0123456789abcdefghijkmnlopqrstuvwxyzABCDEFGHIJKMNLPOQRSTUVWXYZ/~!@#\$%^&*_-+=`|\\(){}[]:;\"'<>,.?/"

        /**
         * char replacement map
         * @param input lower case and number
         * @return      upper case and allowed special chars
         * */
        private fun switch(input1: Char, input2: Char): Char {

            var number = charsToNum(input1, input2).toDouble()
            // num is a number 0 <= x <= 127
            // so make it a number 0 <= x <= allowedChars.size()
            number = ((number / 127) * (allowedChars.length - 1))
            return allowedChars[number.toInt()]

        }

        /**
         * @param char lower case and numbers
         * @return a number 0 <= x <= 35
         * */
        private fun charToNum(char: Char) = hexChars.indexOf(char)

        private fun charsToNum(input1: Char, input2: Char): Int {

            return when (input1) {
                '0', '1' -> hexChars.indexOf(input2)
                '2', '3' -> hexChars.indexOf(input2) + 16
                '4', '5' -> hexChars.indexOf(input2) + 32
                '6', '7' -> hexChars.indexOf(input2) + 48
                '8', '9' -> hexChars.indexOf(input2) + 64
                'a', 'b' -> hexChars.indexOf(input2) + 80
                'c', 'd' -> hexChars.indexOf(input2) + 96
                'e', 'f' -> hexChars.indexOf(input2) + 112

                else -> throw java.lang.RuntimeException("character is not lower or number")
            }
        }


        fun main(input: String): String {

            var result = ""
            for (i in 0..63 step 2) {

                result += switch(input[i], input[i + 1])
            }

            //if its not standard try one more hash
            //(to make sure output is standard password and website validator gonna accept it)
            if (Validation.password(result.substring(0, PASSWORD_MIN_SIZE)) != Validation.OK) {
                result = sha256(result)
                result = main(result)
            }

            return result
        }

        /**
         * @param hashResult 64 lower case and numbers
         * @return 4 digits (in string)
         * */
        fun bankMode(hashResult: String): String {

            var result = ""
            for (i in 0..3) {
                var num = charToNum(hashResult[i]).toDouble()
                // num is a number 0 <= x <= 15
                // so make it a number 0 <= x <= 9
                num = ((num / 15) * 9)
                result += num.toInt().toString()
            }
            return result
        }
    }


    /**
     * @return chars with dynamic size (PASSWORD_MIN_SIZE..PASSWORD_MIN_SIZE+3) with same possibility
     * */
    private fun limitIt(input: String): String {

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
            bit1 and bit2 -> input.substring(0, PASSWORD_MIN_SIZE)
            !bit1 and bit2 -> input.substring(0, PASSWORD_MIN_SIZE + 1)
            bit1 and !bit2 -> input.substring(0, PASSWORD_MIN_SIZE + 2)
            !bit1 and !bit2 -> input.substring(0, PASSWORD_MIN_SIZE + 3)
            else -> throw java.lang.RuntimeException("this should never happen")
        }
    }


    // public methods

    /**
     * hash to generate password
     * @param listener this callback called when password is ready (not in ui thread)
     * result is standard password with lower case and upper case numbers and special chars
     * with dynamic size
     * */
    fun pash(masterPass: String, url: String, username: String, isGuest: Boolean, listener: PasherListener) {

        Thread {

            var value = "$masterPass$url$username"

            if (isGuest) value += "whatever"

            if (cache.containsKey(value)) {
                listener.onReady(cache[value]!!)
                return@Thread
            }

            var pash: String = slowIt(value)
            pash = Standard.main(pash)
            pash = limitIt(pash)

            listener.onReady(pash) // go ;)

            cache[value] = pash // push to cache

        }.start()
    }

    /**
     * hash to generate password in bank mode
     * @return 4 digits
     * */
    fun pashBankMode(masterPass: String, lastFourDigit: String, isGuest: Boolean, listener: PasherListener) {

        Thread {
            var pash = "$masterPass $lastFourDigit"
            if (isGuest) pash += "GuestMode"

            pash = slowIt(pash)
            pash = Standard.bankMode(pash)

            listener.onReady(pash)

        }.start()
    }

    /**
     * master password hash (to find out this master password is the previous one or not)
     * @return two chars
     * */
    fun mash(masterPass: String, listener: PasherListener) {
        Thread {
            listener.onReady(slowIt(masterPass).substring(0, 2))
        }.start()
    }
}