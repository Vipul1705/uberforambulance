package com.example.cp.ambulancetracking_app.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cp.ambulancetracking_app.R;

import java.util.ArrayList;

public class ChargeAdapter  extends ArrayAdapter<String> {
    private Activity context;
    private ArrayList<String> chrgid;
    private ArrayList<String> ambname;
    private ArrayList<String> charges;
    private ArrayList<String> date1;

    public ChargeAdapter(Activity context, ArrayList<String> chrgid, ArrayList<String> name, ArrayList<String> charges, ArrayList<String> date1) {
        super(context, R.layout.adapter_charge, chrgid);
        this.context = context;
        this.chrgid = chrgid;
        this.ambname = name;
        this.charges = charges;
        this.date1 = date1;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.adapter_charge, null, true);

        TextView txtId = (TextView) rowView.findViewById(R.id.tvAdpChrgId);
        TextView txtName = (TextView) rowView.findViewById(R.id.tvAdpAmbName);
        TextView txtCharges = (TextView) rowView.findViewById(R.id.tvAdpChrgValue);
        TextView txtDate1 = (TextView) rowView.findViewById(R.id.tvAdpChrgDate);

        txtId.setText(chrgid.get(position));
        txtName.setText(ambname.get(position));
        txtCharges.setText(charges.get(position));
        txtDate1.setText(date1.get(position));
        return rowView;
    }

}