package com.changgou.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 获取特定日期工具类
 */
public class DateUtil {

    /**
     * @description: //TODO 从yyyy-MM-dd HH:mm格式转成yyyyMMddHH格式
     * @param: [dateStr]
     * @return: java.lang.String
     */
    public static String formatStr(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = simpleDateFormat.parse(dateStr);
            simpleDateFormat = new SimpleDateFormat("yyyyMMddHH");
            return simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @description: //TODO 获取指定日期的凌晨0点
     * @param: [date]
     * @return: java.util.Date
     */
    public static Date toDayStartHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date start = calendar.getTime();
        return start;
    }


    /**
     * @description: //TODO 时间递增 N分钟
     * @param: [date, minutes]
     * @return: java.util.Date
     */
    public static Date addDateMinutes(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);// 24小时制
        date = calendar.getTime();
        return date;
    }


    /**
     * @description: //TODO 时间递增 N小时
     * @param: [date, hour]
     * @return: java.util.Date
     */
    public static Date addDateHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);// 24小时制
        date = calendar.getTime();
        return date;
    }


    /**
     * @description: //TODO 获取时间菜单
     * @param: []
     * @return: java.util.List<java.util.Date>
     */
    public static List<Date> getDateMenus() {

        // 定义一个List<Date>集合，存储所有时间段
        List<Date> dates = new ArrayList<Date>();

        // 循环12次
        Date date = toDayStartHour(new Date()); // 凌晨
        for (int i = 0; i < 12; i++) {
            // 每次递增2小时,将每次递增的时间存入到List<Date>集合中
            dates.add(addDateHour(date, i * 2));
        }

        // 判断当前时间属于哪个时间范围
        Date now = new Date();
        for (Date cdate : dates) {
            // 开始时间 <= 当前时间 < 开始时间 + 2小时
            if (cdate.getTime() <= now.getTime() && now.getTime() < addDateHour(cdate, 2).getTime()) {
                now = cdate;
                break;
            }
        }

        // 当前需要显示的时间菜单
        List<Date> dateMenus = new ArrayList<Date>();

        for (int i = 0; i < 5; i++) {
            dateMenus.add(addDateHour(now, i * 2));
        }
        return dateMenus;
    }


    /**
     * @description: //TODO 时间转成yyyyMMddHH
     * @param: [date]
     * @return: java.lang.String
     * @author: KyleSun
     * @date: 19:27 2020/6/17
     */
    public static String date2Str(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHH");
        return simpleDateFormat.format(date);
    }


    /**
     * @description: //TODO 测试
     * @param: [args]
     * @return: void
     */
    public static void main(String[] args) {

        // 储存数据
        List<Date> dateList = new ArrayList<>();

        // 获取当日0点
        Date startHour = toDayStartHour(new Date());

        // 时间递增 2小时
        for (int i = 0; i < 12; i++) {
            dateList.add(addDateHour(startHour, i * 2));
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Date date : dateList) {
            String format = simpleDateFormat.format(date);
            System.out.println(format);
        }


    }
}
