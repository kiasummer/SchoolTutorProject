package com.nn.kovaleva.irina.schooltutor.UI.resurces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nn.kovaleva.irina.schooltutor.R;

import java.util.ArrayList;

public class CustomSimpleSpinnerAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> mData;
    private LayoutInflater mInflater;
    public boolean ifUsed;


    public CustomSimpleSpinnerAdapter(Context context, ArrayList<String> data) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mData = data;
        ifUsed = false;
    }
    @Override
    public int getCount() {
        return mData.size();
    }


    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.simple_spinner_adapter, parent, false);
            holder.name = convertView.findViewById(R.id.name);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        if (!ifUsed) {
            holder.name.setText("Choose...");
        } else {
            holder.name.setText(mData.get(position));
        }



        convertView.setTag(holder);


        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.simple_spinner_drop_adapler, parent, false);
            holder.name = convertView.findViewById(R.id.name);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(mData.get(position));

        convertView.setTag(holder);

        return convertView;
    }

    public class ViewHolder {
        TextView name;
    }
}
