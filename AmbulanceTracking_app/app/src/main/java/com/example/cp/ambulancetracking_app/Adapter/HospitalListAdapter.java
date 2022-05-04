package com.example.cp.ambulancetracking_app.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cp.ambulancetracking_app.R;

import java.util.ArrayList;

public class HospitalListAdapter extends ArrayAdapter<String> {
    private Activity context;
    private ArrayList<String> hospid;
    private ArrayList<String> hospname;
    private ArrayList<String> contactno;
    private ArrayList<String> email;
    private ArrayList<String> address;

    public HospitalListAdapter(Activity context, ArrayList<String> hid, ArrayList<String> name, ArrayList<String> cn, ArrayList<String> mail, ArrayList<String> add) {
        super(context, R.layout.adapter_hospital_view, hid);
        this.context = context;
        this.hospid = hid;
        this.hospname = name;
        this.contactno = cn;
        this.email = mail;
        this.address = add;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.adapter_hospital_view, null, true);

        TextView txtName = (TextView) rowView.findViewById(R.id.txtHospName);
        TextView txtMail = (TextView) rowView.findViewById(R.id.txtEmailId);
        TextView txtContNo = (TextView) rowView.findViewById(R.id.txtContactNo);
        TextView txtAdd = (TextView) rowView.findViewById(R.id.txtAddress);

        txtName.setText(hospname.get(position));
        txtMail.setText(email.get(position));
        txtContNo.setText("+91 "+contactno.get(position));
        txtAdd.setText(address.get(position));

        return rowView;
    }

}