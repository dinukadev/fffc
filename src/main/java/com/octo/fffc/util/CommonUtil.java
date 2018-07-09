package com.octo.fffc.util;

import com.octo.fffc.exception.FixedFileFormatConverterException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * This class contains common utility functionality used across the application.
 *
 * @author dinuka
 */
public final class CommonUtil {
    /**
     * This method will construct the date according to the format required for the csv and if a parser exception occurrs
     * it will throw out an instance of {@link FixedFileFormatConverterException}
     *
     * @param data
     * @return
     */
    public static String constructDate(String data) {
        SimpleDateFormat formatDateTo = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate;
        try {
            currentDate = currentDateFormat.parse(data);
        } catch (ParseException e) {
            String exceptionMessage = String.format("Date : %s could not be parsed", data);
            throw new FixedFileFormatConverterException(exceptionMessage);
        }
        return formatDateTo.format(currentDate);
    }

    /**
     * This method will write the csv data to the file. Will create a file if one does not exist and will append
     * to the file if it does exist.
     *
     * @param csvPath the absolute file path to write the csv data
     * @param data    the data to write to the file
     */
    public static void writeToFile(String csvPath, String data) {
        try {
            Files.write(Paths.get(csvPath), Arrays.asList(data), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new FixedFileFormatConverterException(e);
        }
    }

    /**
     * This method will validate if the passed in string is a valid number. If it is not, then a runtime exception
     * of type {@link FixedFileFormatConverterException} is thrown out
     *
     * @param number the number to validate
     */
    public static void isValidNumber(String number) {
        if (!NumberUtils.isNumber(number)) {
            String exceptionMessage = String.format("Invalid number : %s provided", number);
            throw new FixedFileFormatConverterException(exceptionMessage);
        }
    }
}
