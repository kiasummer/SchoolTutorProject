package com.nn.kovaleva.irina.schooltutor.UI.resurces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.nn.kovaleva.irina.schooltutor.R;

import java.util.ArrayList;

public class CustomSimpleSpinnerAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> mData;
    private LayoutInflater mInflater;
    public boolean ifUsed;
    ViewHolder holder;


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



    public int getSelectedPosition(Spinner spinner){
        //String str = holder.name.getText().toString();
        int res = spinner.getSelectedItemPosition();
        return res;
    }

    public int getSelectedAgeOfEducation(){
        String str = holder.name.getText().toString();
        for (int i = 1; i < 12; i ++){
            if (str.contains(String.valueOf(i))){
                return i;
            }
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.simple_spinner_adapter, parent, false);
            holder.name = convertView.findViewById(R.id.name);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.name.setText(mData.get(position));

//        if (!ifUsed) {
//            holder.name.setText("Choose...");
//        } else {
//            holder.name.setText(mData.get(position));
//        }



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
