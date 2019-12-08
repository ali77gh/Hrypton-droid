package com.ali77gh.pash.core

object Validation {

    /**
     * @return error (if its ok returns "OK")
     * */

    val OK = "OK"

    private val nums = listOf("0","1","2","3","4","5","6","7","8","9")
    private val specialChars = listOf(")","!","@","#","$","%","^","&","*","(")
    private val lowers = listOf("a","b","c","d","e","f","g","h","i","j","k","m","n","l","o","p","q","r","s","t","u","v","w","x","y","z")
    private val uppers = listOf("A","B","C","D","E","F","G","H","I","J","K","M","N","L","O","P","Q","R","S","T","U","V","W","X","Y","Z")

    fun validateMasterKey(masterkey: String): String{


        if (masterkey.contains(" ")){
            return "should not have space"
        }

        if (masterkey.length < 10){
            return "master key should be more then 10 characters"
        }

        if (!haveNum(masterkey)){
            return "master key should contains numbers"
        }

        if (!haveSpecialChar(masterkey)){
            return "master key should contains special chars"
        }

        if (!haveUpper(masterkey)){
            return "master key should contains upper case letters"
        }

        if (!haveLower(masterkey)){
            return "master key should contains lower case letters"
        }

        return "OK"

    }

    private fun haveNum(masterkey: String): Boolean{

        for (i in nums)
            if (masterkey.contains(i))
                return true

        return false
    }

    private fun haveSpecialChar(masterkey: String): Boolean{

        for (i in specialChars)
            if (masterkey.contains(i))
                return true

        return false
    }

    private fun haveUpper(masterkey: String): Boolean{

        for (i in uppers)
            if (masterkey.contains(i))
                return true

        return false
    }

    private fun haveLower(masterkey: String): Boolean{

        for (i in lowers)
            if (masterkey.contains(i))
                return true

        return false
    }
}