package com.ali77gh.pash.core

object Validation {

    private const val minSize = 10

    /**
     * @return error (if its ok returns "OK")
     * */

    const val OK = "OK"

    private const val allowedSpecialChars = "//~!@#\$%^&*_-+=`|\\(){}[]:;\"'<>,.?/"

    fun password(password: String): String {

        if(password == "") return "is empty"


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

        if (!haveDigit) return "should have numbers"
        if (!haveUpper) return "should have upper case letters"
        if (!haveLower) return "should have lower case letters"
        if (!haveSpecialChar) return "should have special chars"

        if (password.length < minSize) {
            return "should be more then 10 chars"
        }

        return OK

    }

    fun website(website:String):String{

        if(website == "") return "is empty"

        if (!website.contains('.'))
            return "should have .com , .org ,..."

        if (website.contains(' '))
            return "space is not allowed"
        //todo

        return OK
    }

    fun username(username:String):String{

        if(username == "") return "is empty"


        if (username.contains(' '))
            return "space is not allowed"
        //todo

        return OK
    }

    private fun Char.isSpecialChar() = allowedSpecialChars.contains(this)
}