package com.ali77gh.pash.data

import android.content.Context

import com.ali77gh.pash.data.model.History
import com.example.easyrepolib.sqlite.GenericDAO

class HistoryRepo(context: Context) : GenericDAO<History>(context, History::class.java, "history", true)
