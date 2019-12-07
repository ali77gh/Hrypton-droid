package com.ali77gh.pash.data

import android.app.Activity
import com.ali77gh.pash.core.Pasher
import com.ali77gh.pash.core.PasherListener
import com.example.easyrepolib.abstracts.GRepo
import com.example.easyrepolib.repos.StringDAO

class MasterKeyRepo(var activity: Activity) {

    var repo: StringDAO? = null

    init {
        repo = StringDAO(activity, GRepo.Mode.LOCAL, "key")
    }


    // master key (remember me checkbox)

    private val selfFileName = "masterKey"

    fun selfIsExist(): Boolean {
        return repo!!.CheckExist(selfFileName)
    }

    fun selfSave(masterKey: String) {
        repo!!.Save(selfFileName, masterKey)
    }

    fun selfLoad(): String {
        return repo!!.Load(selfFileName)
    }



    // hash of master key (for checking master key is last master key that entered)

    private val hashFileName = "masterKeyHash"

    fun hashIsExist(): Boolean {
        return repo!!.CheckExist(hashFileName)
    }

    fun hashSave(masterKey: String,listener:PasherListener) {

        Pasher(activity).mash(masterKey,object :PasherListener{
            override fun onReady(hashedMasterKey: String) {
                repo!!.Save(hashFileName, hashedMasterKey)
                listener.onReady("")
            }
        })
    }

    fun hashLoad(): String {
        return repo!!.Load(hashFileName)
    }

    fun hashCheckSame(masterKey: String,listener: MasterKeyHashCheckListener){

        Pasher(activity).mash(masterKey,object :PasherListener{
            override fun onReady(hashedMasterKey: String) {

                listener.onReady(hashedMasterKey==hashLoad())
            }
        })
    }

    interface MasterKeyHashCheckListener{
        fun onReady(isMatch:Boolean)
    }

}