package com.kaolee.hotel.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 日期時間格式校驗
 */
@Slf4j
public class DateTimeFormatCheckUtil {

    /**
     * 日期時間格式校驗
     * @param date
     * @param format
     * @return
     */
    public static boolean isValidDate(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            log.error("日期格式解析錯誤：{}",e);
            return false;
        }
    }
}
