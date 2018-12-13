package com.example.loglibrary;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * @author jieyu.chen
 * @date 2018/7/18
 */
public class LogListAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;

    public LogListAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void setLogItem(String item) {
        list.add(item);
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = new TextView(context);
            holder.textView = (TextView) view;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textView.setText(list.get(i));
        return view;
    }

    public class ViewHolder {

        public void setTextView(TextView textView) {
            this.textView = textView;
        }

        private TextView textView;

        public TextView getTextView() {
            return textView;
        }
    }
}
