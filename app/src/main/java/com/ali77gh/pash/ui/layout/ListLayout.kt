package com.ali77gh.pash.ui.layout

import android.app.Activity
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ListView
import com.ali77gh.pash.R
import com.ali77gh.pash.data.HistoryRepo
import com.ali77gh.pash.ui.adapter.HistoryListViewAdapterJ
import com.ali77gh.pash.ui.dialog.ShowPasswordDialog

class ListLayout(context: Context , attrs: AttributeSet) : FrameLayout(context,attrs) {

    private var repo : HistoryRepo? = null

    fun render(activity: Activity) {
        val root = activity.layoutInflater.inflate(R.layout.layout_list, null) as ConstraintLayout

        repo = HistoryRepo(activity)

        val list = root.findViewById<ListView>(R.id.list_history)
        val fab = root.findViewById<FloatingActionButton>(R.id.fab_history)

        val histories = repo!!.all
        list.adapter = HistoryListViewAdapterJ(activity, histories)


        list.setOnItemClickListener { parent, view, position, id ->
            ShowPasswordDialog(activity, histories[position]).show()
        }

        fab.setOnClickListener {

        }

        this.addView(root)
    }
}