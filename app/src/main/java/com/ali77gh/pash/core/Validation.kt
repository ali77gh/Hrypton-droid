package com.ali77gh.pash.core

object Validation {

    private val minSize = 10

    /**
     * @return error (if its ok returns "OK")
     * */

    val OK = "OK"

    private val allowedSpecialChars = "//~!@#\$%^&*_-+=`|\\(){}[]:;\"'<>,.?/"

    fun password(password: String): String {


        var haveDigit = false
        var haveUpper = false
        var haveLower = false
        var haveSpecialChar = false

        for (i in password) {

            when {
                i.isDigit() -> haveDigit = true
                i.isUpperCase() -> haveUpper = true
                i.isLowerCase() -> haveLower = true
                i.isSpecialChar() -> haveSpecialChar = true
                i == ' ' -> return "space is not allowed"
                else -> return "char \"$i\" not allowed" //not allowing non english chars (like asian chars)
            }

        }

        if (!haveDigit) return "password should have numbers"
        if (!haveUpper) return "password should have upper case letters"
        if (!haveLower) return "password should have lower case letters"
        if (!haveSpecialChar) return "password should have special char"

        if (password.length < minSize) {
            return "master key should be more then 10 characters"
        }

        return OK

    }

    private fun Char.isSpecialChar() = allowedSpecialChars.contains(this)
}