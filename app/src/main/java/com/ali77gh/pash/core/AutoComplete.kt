package com.ali77gh.pash.core

import android.R
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView


object AutoComplete {

    //todo provide list of websites
    private val WEBSITES = listOf(
            "instagram.com",
            "facebook.com",
            "test.com",
            "unitools.ir",
            "alisblog.ir")

    fun websites(context: Context, editText: AutoCompleteTextView) {
        val adapter: ArrayAdapter<String> = ArrayAdapter(context, R.layout.simple_list_item_1, WEBSITES)
        editText.setAdapter(adapter)
    }
}