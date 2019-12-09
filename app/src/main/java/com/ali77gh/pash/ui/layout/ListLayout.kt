package com.ali77gh.pash.ui.layout

import android.app.Activity
import android.content.Context
import android.service.media.MediaBrowserService
import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ListView
import android.widget.TextView
import com.ali77gh.pash.R
import com.ali77gh.pash.data.HistoryRepo
import com.ali77gh.pash.data.model.History
import com.ali77gh.pash.ui.adapter.HistoryListViewAdapterJ
import com.ali77gh.pash.ui.dialog.NewPasswordDialog
import com.ali77gh.pash.ui.dialog.ShowPasswordDialog

class ListLayout(context: Context , attrs: AttributeSet) : FrameLayout(context,attrs) {

    private var repo : HistoryRepo? = null

    fun render(activity: Activity) {
        val root = activity.layoutInflater.inflate(R.layout.layout_list, null) as ViewGroup

        repo = HistoryRepo(activity)


        val fab = root.findViewById<FloatingActionButton>(R.id.fab_history)

        refreshList(activity,root)

        fab.setOnClickListener {
            val dialog = NewPasswordDialog(activity)
            dialog.show()
            dialog.setOnDismissListener {
                refreshList(activity,root)
            }
        }

        this.addView(root)
    }

    private fun refreshList(activity: Activity , root: ViewGroup){

        val list = root.findViewById<ListView>(R.id.list_history)
        val nothingToShow = root.findViewById<TextView>(R.id.text_history_nothing_to_show)

        val histories = repo!!.all.reversed()

        list.adapter = HistoryListViewAdapterJ(activity, histories)

        if (histories.isEmpty())
            nothingToShow.visibility = View.VISIBLE
        else
            nothingToShow.visibility = View.GONE


        list.setOnItemClickListener { parent, view, position, id ->
            ShowPasswordDialog(activity, histories[position]).show()
        }
    }
}