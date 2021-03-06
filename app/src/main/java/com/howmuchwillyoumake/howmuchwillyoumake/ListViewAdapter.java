package com.howmuchwillyoumake.howmuchwillyoumake;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.howmuchwillyoumake.howmuchwillyoumake.Constants.FIRST_COLUMN;
import static com.howmuchwillyoumake.howmuchwillyoumake.Constants.SECOND_COLUMN;
import static com.howmuchwillyoumake.howmuchwillyoumake.Constants.THIRD_COLUMN;

public class ListViewAdapter extends BaseAdapter{

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;
    TextView txtThird;

    public ListViewAdapter(Activity activity,ArrayList<HashMap<String, String>> list){
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = activity.getLayoutInflater();

        if(convertView == null){

            convertView = inflater.inflate(R.layout.entry_list, null);

            txtFirst = (TextView) convertView.findViewById(R.id.title_list);
            txtSecond = (TextView) convertView.findViewById(R.id.operation_amount_num_units_unit_list);
            txtThird = (TextView) convertView.findViewById(R.id.year_duration_list);

        }

        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get(FIRST_COLUMN));
        txtSecond.setText(map.get(SECOND_COLUMN));
        txtThird.setText(map.get(THIRD_COLUMN));

        return convertView;
    }
}