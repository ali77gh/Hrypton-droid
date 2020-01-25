package com.ali77gh.pash.core

import android.R
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView


object AutoComplete {

    //--------------------------------------------------------------
    //                                                              |
    //add websites here by a pull request to improve auto complete  |
    //                                                              |
    //--------------------------------------------------------------
    private val WEBSITES = listOf(

            //social networks and messengers
            "instagram.com",
            "facebook.com",
            "pinterest.com",
            "tumblr.com",
            "snapchat.com",
            "youtube.com",
            "linkedin.com",
            "twitter.com",
            "tagged.com",
            "virgool.io",
            "vk.com",
            "flickr.com",
            "ask.fm",
            "reddit.com",
            "whatsapp.com",
            "medium.com",
            "telegram.org",
            "line.me",
            "viber.com",
            "weibo.com",
            "tiktok.com",
            "wechat.com",

            //git repos
            "github.com",
            "gitlab.com",

            //mails
            "mail.google.com",
            "outlook.live.com",
            "yahoo.com",
            "icloud.com",

            //others
            "wikipedia.org",
            "amazon.com",
            "netflix.com",
            "twitch.tv",
            "msn.com",
            "imdb.com")

    fun websites(context: Context, editText: AutoCompleteTextView) {
        val adapter: ArrayAdapter<String> = ArrayAdapter(context, R.layout.simple_list_item_1, WEBSITES)
        editText.setAdapter(adapter)
    }
}