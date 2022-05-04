package com.example.cp.ambulancetracking_app.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cp.ambulancetracking_app.R;

import java.util.ArrayList;

public class UserReqAdapter extends ArrayAdapter<String> {
    private Activity context;
    private ArrayList<String> bookid;
    private ArrayList<String> userid;
    private ArrayList<String> contactno;
    private ArrayList<String> datetime;

    public UserReqAdapter(Activity context, ArrayList<String> bid, ArrayList<String> uid, ArrayList<String> cn, ArrayList<String> dt) {
        super(context, R.layout.adapter_user_req, bid);
        this.context = context;
        this.bookid = bid;
        this.userid = uid;
        this.contactno = cn;
        this.datetime = dt;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.adapter_user_req, null, true);

        TextView txtC = (TextView) rowView.findViewById(R.id.txtContactNo);
        TextView txtD = (TextView) rowView.findViewById(R.id.txtDate);

        txtC.setText(contactno.get(position));
        txtD.setText(datetime.get(position));

        return rowView;
    }

}