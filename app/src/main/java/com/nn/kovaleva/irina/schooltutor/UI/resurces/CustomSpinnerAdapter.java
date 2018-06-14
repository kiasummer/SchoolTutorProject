package com.nn.kovaleva.irina.schooltutor.UI.resurces;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nn.kovaleva.irina.schooltutor.R;

import java.util.ArrayList;


public class CustomSpinnerAdapter extends BaseAdapter {
    public static final String TAG = "CustomSpinnerAdapter";

    private Context mContext;
    private ArrayList<String> mData;
    private LayoutInflater mInflater;
    public boolean ifUsed;
    public ViewHolder holder;


    public CustomSpinnerAdapter(Context context, ArrayList<String> data) {
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

    public String getSelectedString(Spinner spinner){
        //String str = holder.name.getText().toString();
        String str = spinner.getSelectedItem().toString();
        if (str.equals("Choose a subject...")) {
            return "";
        } else{
            return str;
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView: ");

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_adapter, parent, false);
            holder.name = convertView.findViewById(R.id.name);
            holder.logo = convertView.findViewById(R.id.logo);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(mData.get(position));
        holder.logo.setImageResource(setImgResours(position));

//        if (!ifUsed) {
//            holder.name.setText("Choose a subject...");
//            holder.logo.setImageResource(android.R.drawable.ic_menu_edit);
//        } else {
//
//        }



        convertView.setTag(holder);


        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getDropDownView: ");
        ViewHolder holder;
        ifUsed = true;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.spinner_list_drop_adapter, parent, false);
            holder.name = convertView.findViewById(R.id.name);
            holder.logo = convertView.findViewById(R.id.logo);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(mData.get(position));
        holder.logo.setImageResource(setImgResours(position));


        convertView.setTag(holder);


        return convertView;
    }

    public class ViewHolder {
        TextView name;
        ImageView logo;
    }

    private int setImgResours(int position){
        int imgResourceId;
        switch (position) {
            case 0:
                imgResourceId = android.R.drawable.ic_menu_edit;
                break;
            case 1:
                imgResourceId = android.R.drawable.ic_menu_share;
                break;
            case 2:
                imgResourceId = android.R.drawable.ic_menu_call;
                break;
            case 3:
                imgResourceId = android.R.drawable.ic_menu_camera;
                break;
            case 4:
                imgResourceId = android.R.drawable.ic_menu_search;
                break;
            default:
                imgResourceId = android.R.drawable.ic_menu_zoom;
                break;
        }


        return imgResourceId;
    }
}

