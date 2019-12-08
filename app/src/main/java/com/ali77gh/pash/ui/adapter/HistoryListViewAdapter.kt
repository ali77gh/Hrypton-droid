package com.ali77gh.pash.ui.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.ali77gh.pash.R
import com.ali77gh.pash.data.model.History

class HistoryListViewAdapter(private val _activity: Activity, private val histories: Array<History>) : BaseAdapter() {

    override fun getCount(): Int {
        return histories.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {

        val cview = _activity.layoutInflater.inflate(R.layout.item_history, null) as ViewGroup
        val history = histories[i]

        cview.findViewById<TextView>(R.id.text_history_item).text = "${history.url} : ${history.username}"

        return cview
    }
}