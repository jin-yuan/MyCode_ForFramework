package com.sofb.crwaler.framework.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author liuxuejun
 * @date 2020-01-10 10:39
 **/
public class SofbDateUtils {


    public static String getNowFormatDate() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static void main(String[] args) {
        //
        SofbDateUtils.getNowFormatDate();
    }
}
