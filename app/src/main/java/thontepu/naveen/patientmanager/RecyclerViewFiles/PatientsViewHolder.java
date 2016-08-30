package thontepu.naveen.patientmanager.RecyclerViewFiles;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import thontepu.naveen.patientmanager.R;

/**
 * Created by mac on 8/30/16.
 */
public class PatientsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ItemClickInterface itemClickInterface;
    ImageView patientPic;
    TextView patientName,patientProbability;
    LinearLayout patientItem;

    public PatientsViewHolder(View itemView, ItemClickInterface itemClickInterface) {
        super(itemView);
        this.itemClickInterface = itemClickInterface;
        patientItem = (LinearLayout)itemView.findViewById(R.id.patientItem);
        patientPic = (ImageView)itemView.findViewById(R.id.patientPic);
        patientName = (TextView)itemView.findViewById(R.id.patientName);
        patientProbability = (TextView)itemView.findViewById(R.id.patientProbability);
        patientItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (itemClickInterface!=null){
            itemClickInterface.onItemClickListener(view,getAdapterPosition());
        }
    }
}
