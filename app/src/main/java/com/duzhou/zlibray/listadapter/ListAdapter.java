package com.duzhou.zlibray.listadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.duzhou.zlibray.R;

import java.util.List;

/**
 * Created by zhou on 16-4-23.
 */
public class ListAdapter extends BaseAdapter {

    private List<String> list;
    private LayoutInflater mInflater;

    public ListAdapter(Context context, List<String> list){
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
    }
    /**
     * 将数据循环展示三遍
     */
    @Override
    public int getCount() {
        if(list != null){
            return list.size() * 3;
        }
        return 0;
    }

    @Override
    public Object getItem(int arg0) {

        return list.get(arg0 % list.size());
    }

    @Override
    public long getItemId(int arg0) {
        return arg0 % list.size();
    }
    @Override
    public View getView(int postition, View convertView, ViewGroup arg2) {
        ViewHoler viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHoler();
            convertView = mInflater.inflate(android.R.layout.simple_list_item_1, null);
            viewHolder.tvText = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHoler) convertView.getTag();
        }
        viewHolder.tvText.setText(list.get(postition % list.size()));//取余展示数据
        return convertView;
    }

    static class ViewHoler{
        TextView tvText;
    }

}