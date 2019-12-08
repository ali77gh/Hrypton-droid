package com.ali77gh.pash.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.ali77gh.pash.R;
import com.ali77gh.pash.data.model.History;
import com.ali77gh.pash.ui.Tools;

import java.util.List;

public class HistoryListViewAdapterJ extends BaseAdapter {

    private Activity _activity;
    private List<History> histories;

    public HistoryListViewAdapterJ(Activity activity, List<History> histories) {

        this._activity = activity;
        this.histories = histories;
    }

    @Override
    public int getCount() {
        return histories.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewGroup cview = (ViewGroup) _activity.getLayoutInflater().inflate(R.layout.item_history, null);
        History history = histories.get(i);

        TextView tv = cview.findViewById(R.id.text_history_item);

        tv.setText(history.getUrl()+" : "+history.getUsername());

        if (i == histories.size()-1){
            tv.setPadding(Tools.DpToPixel(16),Tools.DpToPixel(16),Tools.DpToPixel(16),Tools.DpToPixel(66));
        }

        return cview;
    }
}
