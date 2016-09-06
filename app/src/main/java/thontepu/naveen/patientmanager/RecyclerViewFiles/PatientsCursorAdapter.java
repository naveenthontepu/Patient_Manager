package thontepu.naveen.patientmanager.RecyclerViewFiles;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thontepu.naveen.patientmanager.Database.PatientsDB;
import thontepu.naveen.patientmanager.R;
import thontepu.naveen.patientmanager.Utils.Constants;
import thontepu.naveen.patientmanager.Utils.Utilities;

/**
 * Created by mac on 9/6/16.
 */
public class PatientsCursorAdapter extends RecyclerView.Adapter<PatientsViewHolder> {
    Cursor cursor;
    ItemClickInterface itemClickInterface;
    LayoutInflater layoutInflater;
    Context context;
    private boolean mDataValid = false;

    public PatientsCursorAdapter(Cursor cursor, ItemClickInterface itemClickInterface, Context context) {
        this.cursor = cursor;
        this.itemClickInterface = itemClickInterface;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        mDataValid = cursor != null;
        setHasStableIds(true);
    }

    @Override
    public PatientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.patient_item_view, parent, false);
        return new PatientsViewHolder(view, itemClickInterface);
    }

    @Override
    public void onBindViewHolder(PatientsViewHolder holder, int position) {
        Utilities.printLog(Constants.Tags.ACTIVITY_STATE,"the position = "+position);
        Utilities.printLog(Constants.Tags.ACTIVITY_STATE,"the condition = "+(cursor.moveToPosition(position)));
        if (mDataValid && cursor.moveToPosition(position)) {
            holder.patientName.setText(cursor.getString(cursor.getColumnIndex(PatientsDB.COLUMN_NAME)));
            holder.patientProbability.setText(cursor.getString(cursor.getColumnIndex(PatientsDB.COLUMN_PROBABILITY)) + " %");
        }
    }

    public Cursor getCursor() {
        return cursor;
    }

    @Override
    public int getItemCount() {
        if (mDataValid && cursor != null && cursor.getCount() > 0) {
            return cursor.getCount();
        }
        return 0;
    }

    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == cursor) {
            return null;
        }
        Cursor oldCursor = cursor;
        cursor = newCursor;
        if (newCursor != null) {
            mDataValid = true;
            notifyDataSetChanged();
        } else {
            mDataValid = false;
            notifyItemRangeRemoved(0, getItemCount());
        }
        return oldCursor;
    }
}
