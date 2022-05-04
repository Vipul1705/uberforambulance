package com.example.cp.ambulancetracking_app.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cp.ambulancetracking_app.R;

import java.util.ArrayList;

public class AmbuListadapter  extends ArrayAdapter<String> {
    private Activity context;
    private ArrayList<String> ambid;
    private ArrayList<String> ambname;
    private ArrayList<String> contactno;
    private ArrayList<String> vehicleNo;

    public AmbuListadapter(Activity context, ArrayList<String> aid, ArrayList<String> name, ArrayList<String> cn,
                           ArrayList<String> vehicleNo) {
        super(context, R.layout.adapter_ambulance_view, aid);
        this.context = context;
        this.ambid = aid;
        this.ambname = name;
        this.contactno = cn;
        this.vehicleNo = vehicleNo;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.adapter_ambulance_view, null, true);

        TextView txtName = (TextView) rowView.findViewById(R.id.txtAmbulanceName);
        TextView txtContNo = (TextView) rowView.findViewById(R.id.txtContact);
        TextView txtVehicleNo = rowView.findViewById(R.id.txtVehicleNo);

        txtName.setText(ambname.get(position));
        txtContNo.setText(contactno.get(position));
        txtVehicleNo.setText(vehicleNo.get(position));

        return rowView;
    }

}