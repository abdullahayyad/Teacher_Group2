package ps.wwbtraining.teacher_group2.Utils;

import android.content.Context;
import android.text.format.DateFormat;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;



public class DateAndTimeUtil {


    public static String toStringReadableDate(Calendar calendar) {
        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL, Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }


}