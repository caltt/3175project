package com.example.a3175.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RequiresApi(api = Build.VERSION_CODES.O)
public class LocalDateConverter {
    @TypeConverter
    public static LocalDate toLocalDate(String value) {
        try {
            return value == null ? null : LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    @TypeConverter
    public static String fromLocalDate(LocalDate date) {
        return date == null ? null : date.toString();
    }
}
