package mvc.com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mvc.com.R;
import mvc.com.helpers.DateFormatHelper;
import mvc.com.model.DriveModel;

/**
 * Created by zloty on 2018-01-11.
 */

public class CustomDriveAdapter extends ArrayAdapter<DriveModel> implements View.OnClickListener {

    private ArrayList<DriveModel> drives;
    Context mContext;


    private static class ViewHolder {
        TextView txtCityStart;
        TextView txtCityArrival;
        TextView txtStartDate;
    }

    public CustomDriveAdapter(ArrayList<DriveModel> drives, Context context){
        super(context, R.layout.row_drive_item, drives);
        this.drives = drives;
        this.mContext = context;
    }


    @Override
    public void onClick(View view) {

    }


    public View getView(int position, View convertView, ViewGroup parent){
        DriveModel drive = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;

        final View result;

        if(convertView == null){

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_drive_item, parent, false);

            viewHolder.txtCityStart = convertView.findViewById(R.id.drive_row_city_start);
            viewHolder.txtCityArrival = convertView.findViewById(R.id.drive_row_city_arrival);
            viewHolder.txtStartDate = convertView.findViewById(R.id.drive_row_start_date);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        // parsowanie daty na stringa
        DateFormatHelper dateFormatHelper = new DateFormatHelper(drive.getStartDate(), "yyyy-MM-dd HH:mm");
        String startDate = dateFormatHelper.calendarToString_DateTimeFormat();

        String startPlace = "z:  " + drive.getCityStart() + " " + drive.getStreetStart() + " " + drive.getExactPlaceStart();
        String arrivalPlace = "do: " + drive.getCityArrival() + " " + drive.getStreetArrival() + " " + drive.getExactPlaceArrival();

        viewHolder.txtCityStart.setText(startPlace);
        viewHolder.txtCityArrival.setText(arrivalPlace);
        viewHolder.txtStartDate.setText(startDate);

        return convertView;
    }

}
