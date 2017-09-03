package com.example.lablnet.simplenote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

/**
 * Created by lablnet on 8/25/2017.
 */

public class Custum_Adapter extends ArrayAdapter<Data_Model> {
    public List<Data_Model> mList;
    public Context context;

    public Custum_Adapter(@NonNull Context context, @NonNull List<Data_Model> mList) {
        super(context, 0, mList);
        this.mList = mList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layout = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layout.inflate(R.layout.list_item, null);
        }
        TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        TextView txtDescription = (TextView) convertView.findViewById(R.id.txtDescription);
        TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);
        ImageView img=(ImageView)convertView.findViewById(R.id.imageView);
        txtDescription.setMaxLines(2);
        txtTitle.setMaxLines(1);
        Data_Model mData = mList.get(position);
        txtTitle.setText(mData.getTitle());
        txtDescription.setText(mData.getDescription());
        String[] date=mData.getDate().split(" ");
        int mDate= Integer.parseInt(date[1]);
        Date datew=new Date();
        if(mDate==datew.getDate()){
            txtDate.setText("Today");
        }
        else if(mDate==datew.getDate()-1){
            txtDate.setText("Yesterday");
        }
        else {
            txtDate.setText(String.valueOf(mData.getDate()));
        }
        if(mData.getShowAlarmIcon()==0){
            img.setVisibility(View.GONE);
        }
        return convertView;
    }
}
