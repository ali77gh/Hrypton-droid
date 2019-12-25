package com.ali77gh.pash.ui.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


import com.ali77gh.pash.R
import com.ali77gh.pash.data.model.History
import com.ali77gh.pash.ui.Tools

class HistoryListViewAdapter(private val _activity: Activity, private val histories: List<History>) : BaseAdapter() {

    override fun getCount(): Int {
        return histories.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {

        val cView = _activity.layoutInflater.inflate(R.layout.item_history, null) as ViewGroup
        val history = histories[i]

        val tv = cView.findViewById<TextView>(R.id.text_history_item)

        tv.text = history.url + " : " + history.username

        if (i == histories.size - 1) {
            tv.setPadding(Tools.DpToPixel(16f), Tools.DpToPixel(16f), Tools.DpToPixel(16f), Tools.DpToPixel(66f))
        }

        return cView
    }
}
