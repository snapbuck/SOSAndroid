package vn.snapbuck.sos.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import vn.snapbuck.sos.R;
import vn.snapbuck.sos.app.SBApp;

/**
 * Created by sb1 on 5/7/15.
 */
public class SBDateTimeUtils {

    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;

    public static long getDateInMillis(String srcDate) throws ParseException {
        //SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        SimpleDateFormat dateFormat;
        if (srcDate.contains("Z")) {
            if (!srcDate.contains(".")) {
                srcDate = srcDate.replace("Z", ".000Z");
            }
            dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        } else {
            dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
        }
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = dateFormat.parse(srcDate);

        return date.getTime();
    }

//    public static String getDuration(String srcDate) {
//        String result = "";
//        if (!srcDate.contains("Z")) {
//            String date = srcDate.split(" ")[0];
//            String time = srcDate.split(" ")[1];
//            srcDate = date + " " + time;
//            try {
//                result = getRelativeTimeSpanStringSB(srcDate);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            try {
//                result = getRelativeTimeSpanStringSB(srcDate);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        if(SBApp.getLanguage().equals("vi")){
//            result = VietnameseConvert.convertTime(result);
//        }
//
//        return result;
//
//    }

//    public static String getDayDuration(String srcDate) {
//        if(srcDate==null)
//            return "";
//        String result = "";
//        if (!srcDate.contains("Z")) {
//            String date = srcDate.split(" ")[0];
//            String time = srcDate.split(" ")[1];
//            srcDate = date + " " + time;
//            try {
//                result = getRelativeTimeSpanByDay(srcDate);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            try {
//                result = getRelativeTimeSpanByDay(srcDate);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        if(SBApp.getLanguage().equals("vi")){
//            result = VietnameseConvert.convertTime(result);
//        }
//
//        return result;
//
//    }


    public static String getDateString(String srcDate) throws ParseException {
        //SimpleDa
        String dateTime = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = simpleDateFormat.parse(srcDate);

            dateTime = new SimpleDateFormat("yyyy-M-d HH:mm:ss.SSS").format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateTime;
    }

    public static Date getDateFromString(String srcDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(srcDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String getDayMonth(String srcDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        if (!srcDate.contains(".")) {
            srcDate = srcDate.replace("Z", ".000Z");
        }
        try {
            value = formatter.parse(srcDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd", Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dt = dateFormatter.format(value);

        return dt;
    }

    public static String getMonthDayYear(String srcDate) {

        //DateFormat simpleDateFormat = DateFormat.getDateInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date value = null;
        if (!srcDate.contains(".")) {
            srcDate = srcDate.replace("Z", ".000Z");
        }
        try {
            value = simpleDateFormat.parse(srcDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dt = dateFormatter.format(value);

        return dt;
    }

    public static String getMonthDay(String srcDate) {

        //DateFormat simpleDateFormat = DateFormat.getDateInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date value = null;
        if (!srcDate.contains(".")) {
            srcDate = srcDate.replace("Z", ".000Z");
        }
        try {
            value = simpleDateFormat.parse(srcDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd", Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dt = dateFormatter.format(value);

        return dt;
    }

    public static String getMonthYear(String srcDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        if (!srcDate.contains(".")) {
            srcDate = srcDate.replace("Z", ".000Z");
        }
        try {
            value = formatter.parse(srcDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM yyyy", Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormatter.format(value);
    }

    public static String getCurrentUTCDateString() {
        String result = "";
        try {
            SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
            dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

            result = dateFormatGmt.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getCurrentDateString() {
        String result = "";
        try {
            SimpleDateFormat dateFormatGmt = new SimpleDateFormat("MMM dd,yyyy hh:mm a");
            dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

            result = dateFormatGmt.format(new Date());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

//    public static String getRelativeTimeSpanStringSB(String srcDate) {
//        boolean isAddMinute = true;
//        long currentTime = System.currentTimeMillis();
//        long srcTime = 0;
//        try {
//            srcTime = getDateInMillis(srcDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        long ms = currentTime - srcTime;
//        if (ms >= 0) {
//            StringBuffer text = new StringBuffer("");
//            if (ms > DAY) {
//                long value = ms / DAY;
//                if (value != 0) {
//                    if (value <= 1) {
//                        text.append(value).append(SBApp.getSBAppContext().getResources().getString(R.string.day_unit_single));
//                    } else {
//                        text.append(value).append(SBApp.getSBAppContext().getResources().getString(R.string.day_unit_plural));
//                    }
//                    isAddMinute = false;
//                }
//                ms %= DAY;
//            }
//            if (ms > HOUR) {
//                long value = ms / HOUR;
//                if (value <= 1) {
//                    text.append(value).append(SBApp.getSBAppContext().getResources().getString(R.string.hour_unit_single));
//                } else {
//                    text.append(value).append(SBApp.getSBAppContext().getResources().getString(R.string.hour_unit_plural));
//                }
//                ms %= HOUR;
//            }
//
//            if (ms > MINUTE) {
//                long value = ms / MINUTE;
//                if (isAddMinute) {
//                    if (value <= 1) {
//                        text.append(value).append(SBApp.getSBAppContext().getResources().getString(R.string.minute_unit_single));
//                    } else {
//                        text.append(value).append(SBApp.getSBAppContext().getResources().getString(R.string.minute_unit_plural));
//                    }
//                }
//                ms %= MINUTE;
//            }
//
//            return text.toString();
//        } else {
//            return "";
//        }
//
//    }

//    public static String getRelativeTimeSpanByDay(String srcDate) {
//        boolean isAddMinute = true;
//        long currentTime = System.currentTimeMillis();
//        long srcTime = 0;
//        try {
//            srcTime = getDateInMillis(srcDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        long ms = currentTime - srcTime;
//        if (ms >= 0) {
//            StringBuffer text = new StringBuffer("");
//            if (ms > DAY) {
//                long value = ms / DAY;
//                if (value != 0) {
//                    if (value <= 1) {
//                        text.append(value).append(SBApp.getSBAppContext().getResources().getString(R.string.day_unit_single));
//                    } else {
//                        text.append(value).append(SBApp.getSBAppContext().getResources().getString(R.string.day_unit_plural));
//                    }
//                    isAddMinute = false;
//                }
//                ms %= DAY;
//            }
//            else if (ms > HOUR) {
//                long value = ms / HOUR;
//                if (value <= 1) {
//                    text.append(value).append(SBApp.getSBAppContext().getResources().getString(R.string.hour_unit_single));
//                } else {
//                    text.append(value).append(SBApp.getSBAppContext().getResources().getString(R.string.hour_unit_plural));
//                }
//                ms %= HOUR;
//            }
//            else if (ms > MINUTE) {
//                long value = ms / MINUTE;
//                if (value <= 1) {
//                    text.append(value).append(SBApp.getSBAppContext().getResources().getString(R.string.minute_unit_single));
//                } else {
//                    text.append(value).append(SBApp.getSBAppContext().getResources().getString(R.string.minute_unit_plural));
//                }
//                ms %= MINUTE;
//            }
//
//            return text.toString();
//        } else {
//            return "";
//        }
//
//    }

    public static String getDateStringFromUTC(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
        if (dateStr.contains("Z"))
            df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        if (!dateStr.contains(".")) {
            dateStr = dateStr.replace("Z", ".000Z");
        }
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dateStr.toLowerCase().endsWith("pm"))
            date.setTime(date.getTime() + 12 * 60 * 60 * 1000);


        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
        dateFormat.setTimeZone(TimeZone.getDefault());
        return dateFormat.format(date);
    }

    public static String getDateStringReduce(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
        if (dateStr.contains("Z"))
            df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        if (!dateStr.contains(".")) {
            dateStr = dateStr.replace("Z", ".000Z");
        }
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dateStr.toLowerCase().endsWith("pm"))
            date.setTime(date.getTime() + 12 * 60 * 60 * 1000);


        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        dateFormat.setTimeZone(TimeZone.getDefault());
        return dateFormat.format(date);
    }

    public static String getTimeOnlyUTC(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
        if (dateStr.contains("Z"))
            df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        if (!dateStr.contains(".")) {
            dateStr = dateStr.replace("Z", ".000Z");
        }
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dateStr.toLowerCase().endsWith("pm"))
            date.setTime(date.getTime() + 12 * 60 * 60 * 1000);


        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.US);
        dateFormat.setTimeZone(TimeZone.getDefault());
        return dateFormat.format(date);
    }

    public static String getBirthday(String srcDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        if (!srcDate.contains(".")) {
            srcDate = srcDate.replace("Z", ".000Z");
        }
        try {
            value = formatter.parse(srcDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dt = dateFormatter.format(value);

        return dt;
    }

}
