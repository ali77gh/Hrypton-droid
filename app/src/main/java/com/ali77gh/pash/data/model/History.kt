package com.ali77gh.pash.data.model

import com.example.easyrepolib.sqlite.Model

class History(var url:String,var username:String) : Model{

    var key :String? = ""

    override fun setId(id: String?) {
        key = id
    }

    override fun getId(): String? {
        return key
    }
}