package thontepu.naveen.patientmanager.RecyclerViewFiles;

import android.view.View;

/**
 * Created by naveen thontepu on 28-04-2016.
 */
public interface ItemClickInterface {
    /**
     * on item click interface for sending the on click of the view in a recycler view
     * @param view
     * @param position
     */
    void onItemClickListener(View view, int position);
}
