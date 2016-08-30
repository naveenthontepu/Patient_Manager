package thontepu.naveen.patientmanager.RecyclerViewFiles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import thontepu.naveen.patientmanager.Database.PatientPojo;
import thontepu.naveen.patientmanager.R;

/**
 * Created by mac on 8/30/16.
 */
public class PatientsRecyclerViewAdapter extends RecyclerView.Adapter<PatientsViewHolder> {
    List<PatientPojo> data = Collections.emptyList();
    ItemClickInterface itemClickInterface;
    LayoutInflater layoutInflater;
    Context context;

    public PatientsRecyclerViewAdapter(Context context, ItemClickInterface itemClickInterface, List<PatientPojo> data) {
        this.context = context;
        this.itemClickInterface = itemClickInterface;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public PatientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.patient_item_view,parent,false);
        PatientsViewHolder patientsViewHolder = new PatientsViewHolder(view,itemClickInterface);
        return patientsViewHolder;
    }

    @Override
    public void onBindViewHolder(PatientsViewHolder holder, int position) {
        if (position < data.size()) {
            PatientPojo patientPojo = data.get(position);
            holder.patientName.setText(patientPojo.getName());
            holder.patientProbability.setText(patientPojo.getProbability()+"%");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
