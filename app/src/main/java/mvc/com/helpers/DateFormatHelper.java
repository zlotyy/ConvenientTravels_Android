package mvc.com.helpers;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zloty on 2018-01-09.
 */

public class DateFormatHelper {
    String date;
    String time;
    String pattern;
    Calendar calendar;
    DatePicker datepicker;
    TimePicker timepicker;

    public DateFormatHelper(DatePicker datepicker, String pattern){
        this.datepicker = datepicker;
        this.pattern = pattern;
    }

    public DateFormatHelper(TimePicker timepicker, String pattern){
        this.timepicker = timepicker;
        this.pattern = pattern;
    }


    /**
     * metoda zamienia DatePicker na Stringa w podanym formacie
     */
    public String datepickerToString_DateFormat(){
        SimpleDateFormat dateformat = new SimpleDateFormat(pattern);
        date = dateformat.format(new Date(datepicker.getYear()-1900, datepicker.getMonth(), datepicker.getDayOfMonth()));

        return date;
    }


    /**
     * metoda zamienia TimePicker na Stringa
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public String timepickerToString_DateFormat(){
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timepicker.getHour());
        calendar.set(Calendar.MINUTE, timepicker.getMinute());
        calendar.clear(Calendar.SECOND);    // wyzeruj sekundy

        SimpleDateFormat timeFormat = new SimpleDateFormat(pattern);
        time = timeFormat.format(calendar.getTime());

        return time;
    }


    /**
     * metoda zamienia Stringa na Calendar w podanym formacie
     */
    public Calendar stringToCalendar_DateTimeFormat(){
        SimpleDateFormat format = new SimpleDateFormat(pattern);

        try {
            calendar.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;
    }
}
